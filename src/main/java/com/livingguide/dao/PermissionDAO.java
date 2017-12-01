package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.PermissionPO;
import com.livingguide.vo.query.PermissionQueryModel;

@Repository
public interface PermissionDAO extends BaseDAO<PermissionPO, PermissionQueryModel> {

}
