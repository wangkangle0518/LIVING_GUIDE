package com.livingguide.dao;

import org.springframework.stereotype.Repository;

import com.livingguide.common.dao.BaseDAO;
import com.livingguide.vo.UrolePO;
import com.livingguide.vo.query.UroleQueryModel;

@Repository
public interface UroleDAO extends BaseDAO<UrolePO, UroleQueryModel> {

}
