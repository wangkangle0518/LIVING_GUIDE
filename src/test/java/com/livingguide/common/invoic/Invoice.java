package com.livingguide.common.invoic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 发票
 */
public class Invoice {

	/**
	 * 商品单
	 */
	private List<Commodity> list = new ArrayList<>();

	public List<Commodity> getList() {
		return list;
	}

	public void setList(List<Commodity> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("发票清单: \n");
		BigDecimal bg = new BigDecimal("0");
		for (Commodity commodity : list) {
			bg = bg.add(new BigDecimal(commodity.getTotal()));
			sb.append(commodity.toString());
		}
		sb.append("发票总值：");
		sb.append(bg.toString());
		sb.append("\n");
		return sb.toString();
	}

}
