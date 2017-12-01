package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.RolePermissionPO;
import com.livingguide.vo.query.RolePermissionQueryModel;

@Repository
public interface RolePermissionDAO extends BaseDAO<RolePermissionPO, RolePermissionQueryModel> {

}
