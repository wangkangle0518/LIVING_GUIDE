package com.livingguide.vo.query;

import com.livingguide.common.po.BaseModel;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.vo.UserPO;

public class UserQueryModel extends UserPO implements BaseModel {
	
	private Page page = new Page();

	@Override
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
