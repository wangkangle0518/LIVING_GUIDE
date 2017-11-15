package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.LoginLogPO;
import com.livingguide.vo.query.LoginLogQueryModel;

@Repository
public interface LoginLogDAO extends BaseDAO<LoginLogPO, LoginLogQueryModel> {

}
