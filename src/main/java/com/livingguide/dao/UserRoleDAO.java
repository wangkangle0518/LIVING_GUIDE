package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.UserRolePO;
import com.livingguide.vo.query.UserRoleQueryModel;

@Repository
public interface UserRoleDAO extends BaseDAO<UserRolePO, UserRoleQueryModel> {

}
