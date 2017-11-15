package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.RegisterPO;
import com.livingguide.vo.query.RegisterQueryModel;

@Repository
public interface RegisterDAO extends BaseDAO<RegisterPO, RegisterQueryModel> {

}
