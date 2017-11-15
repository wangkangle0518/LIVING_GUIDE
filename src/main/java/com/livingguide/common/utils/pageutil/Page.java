package com.livingguide.common.utils.pageutil;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Page implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 默认分页大小
	 */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 初始化分页大小
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 数据总条数
	 */
	private int totalCount;

	/**
	 * 当前页码
	 */
	private int pageNum;

	/**
	 * 当前页第一条数据的序号
	 */
	private int start;

	/**
	 * 返回的数据集
	 */
	private List<?> result = Collections.emptyList();

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return (int) Math.ceil(totalCount * 1.0 / pageSize);
	}

	/**
	 * 获取分页大小
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置分页大小
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize < 0 ? 0 : pageSize;
	}

	/**
	 * 获取数据总条数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置数据总条数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取当前页码
	 * 
	 * @return
	 */
	public int getPageNum() {
		return pageNum < 1 ? 1 : pageNum > getTotalPage() ? getTotalPage() : pageNum;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * 获取当前页第一条数据的序号
	 * 
	 * @return
	 */
	public int getStart() {
		this.start = (getPageNum() - 1) * getPageSize();
		return start < 0 ? 0 : start;
	}

	/**
	 * 设置当前页第一条数据的序号
	 * 
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 获取返回集
	 * 
	 * @return
	 */
	public List<?> getResult() {
		return result;
	}

	/**
	 * 设置返回集
	 * 
	 * @param result
	 */
	public void setResult(List<?> result) {
		this.result = result;
	}

}
