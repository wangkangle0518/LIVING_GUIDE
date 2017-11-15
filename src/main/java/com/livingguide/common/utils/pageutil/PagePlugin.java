package com.livingguide.common.utils.pageutil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.livingguide.common.BComponent;

// mybatis3.4版本以上，配置拦截器规则args参数应增加Intger.class
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class, Integer.class})})
public class PagePlugin extends BComponent implements Interceptor {

	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml分页查询语句ID（以Page结尾）
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			// 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的  
		     //  MappedStatement的sql
			if (mappedStatement.getId().matches(pageSqlId)) {
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject is null !");
				}else{
					Connection conn = (Connection) invocation.getArgs()[0];
					String sql = boundSql.getSql();
					//获取from的索引
					String tmp = sql.toLowerCase();
					String countSql = "select count(0) " + sql.substring(tmp.indexOf("from"));
					PreparedStatement countStatement = conn.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
					setParameters(countStatement, mappedStatement, countBS, parameterObject);
					ResultSet rs = countStatement.executeQuery();
					int totalCount = 0;
					if (rs.next()) {
						totalCount = rs.getInt(1);
					}
					rs.close();
					countStatement.close();
					
					Page page = null;
					if (parameterObject instanceof Page) {
						//分页对象作为参数对象
						page = (Page) parameterObject;
						page.setTotalCount(totalCount);
					}else{
						//分页参数作为参数对象parameterObject的一个属性
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"page");
						if(pageField!=null){
							page = (Page) ReflectHelper.getValueByFieldName(parameterObject,"page");
							if(page==null) page = new Page();
							page.setTotalCount(totalCount);
							ReflectHelper.setValueByFieldName(parameterObject,"page", page); //给参数对象parameterObject设置page属性
						}else{
							throw new NoSuchFieldException(parameterObject.getClass().getName()+"查询参数对象没有 page 属性");
						}
					}
					//原始SQL
					sql = sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
					String pageSql = buildPageSql(sql, page);
					this.getLogger().info("==>分页SQL：{}", pageSql);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将重写后的分页sql替换原有的sql语句
				}
			}
		}
		// 将执行权交给下一个拦截器
		return invocation.proceed();
	}

	/**
	 * sql重写，在原始的sql语句上加入分页的参数，目前支持mysql和oracle两种数据库的分页
	 * @param sql
	 * @param page
	 * @return
	 */
	private String buildPageSql(String sql, Page page) {
		if(page!=null && dialect!=null && dialect.trim().length()>0){
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equals(dialect)){
				pageSql.append(sql);
				pageSql.append(" limit "+page.getStart()+","+page.getPageSize());
			}else if("oracle".equals(dialect)){
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") as tmp_tb where ROWNUM<=");
				pageSql.append(page.getStart()+page.getPageSize());
				pageSql.append(") where row_id>");
				pageSql.append(page.getStart());
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}

	/**
	 * 对SQL参数(?)设值org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		dialect = properties.getProperty("dialect");
		pageSqlId = properties.getProperty("pageSqlId");
	}

}
