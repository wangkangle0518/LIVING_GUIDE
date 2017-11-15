package com.livingguide.common.dao;

import java.util.List;

public interface BaseDAO<M, QM> {

	public int create(M m);
	public int update(M m);
	public int delete(int id);
	
	public M getById(int id);
	public List<M> queryByPage(QM qm);
}
