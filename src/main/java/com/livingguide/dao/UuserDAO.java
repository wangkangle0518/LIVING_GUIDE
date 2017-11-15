package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.UuserPO;
import com.livingguide.vo.query.UuserQueryModel;

@Repository
public interface UuserDAO extends BaseDAO<UuserPO, UuserQueryModel> {

}
