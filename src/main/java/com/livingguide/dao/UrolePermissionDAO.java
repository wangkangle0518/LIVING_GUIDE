package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.UrolePermissionPO;
import com.livingguide.vo.query.UrolePermissionQueryModel;

@Repository
public interface UrolePermissionDAO extends BaseDAO<UrolePermissionPO, UrolePermissionQueryModel> {

}
