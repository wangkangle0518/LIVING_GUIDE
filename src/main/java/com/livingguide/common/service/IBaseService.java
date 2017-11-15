package com.livingguide.common.service;

import com.livingguide.common.utils.pageutil.Page;

public interface IBaseService<M, QM> {
	public boolean create(M m);
	public boolean update(M m);
	public boolean delete(int id);
	
	public M getById(int id);
	public Page queryByPage(QM qm);
}
