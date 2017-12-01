package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.RolePO;
import com.livingguide.vo.query.RoleQueryModel;

@Repository
public interface RoleDAO extends BaseDAO<RolePO, RoleQueryModel> {

}
