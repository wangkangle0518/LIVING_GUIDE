package com.livingguide.common.service;

import java.util.List;

import com.livingguide.common.BComponent;
import com.livingguide.common.dao.BaseDAO;
import com.livingguide.common.po.BaseModel;
import com.livingguide.common.utils.pageutil.Page;

public class BaseService<M, QM extends BaseModel> extends BComponent implements IBaseService<M, QM> {

	private BaseDAO<M, QM> dao;

	public void setDAO(BaseDAO<M, QM> dao) {
		this.dao = dao;
	}

	@Override
	public boolean create(M m) {
		return dao.create(m) == 1;
	}

	@Override
	public boolean update(M m) {
		return dao.update(m) == 1;
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id) == 1;
	}

	@Override
	public M getById(int id) {
		return dao.getById(id);
	}

	@Override
	public Page queryByPage(QM qm) {
		List<M> list = dao.queryByPage(qm);
		qm.getPage().setResult(list);
		return qm.getPage();
	}


}
