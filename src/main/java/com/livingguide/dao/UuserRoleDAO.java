package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.UuserRolePO;
import com.livingguide.vo.query.UuserRoleQueryModel;

@Repository
public interface UuserRoleDAO extends BaseDAO<UuserRolePO, UuserRoleQueryModel> {

}
