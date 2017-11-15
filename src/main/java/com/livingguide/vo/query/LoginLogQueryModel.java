package com.livingguide.vo.query;

import com.livingguide.common.po.BaseModel;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.vo.LoginLogPO;

public class LoginLogQueryModel extends LoginLogPO implements BaseModel {
	
	private Page page = new Page();

	@Override
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
