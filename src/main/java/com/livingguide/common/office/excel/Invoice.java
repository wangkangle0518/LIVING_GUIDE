package com.livingguide.common.office.excel;

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

	/**
	 * 获取商品单
	 * 
	 * @return
	 */
	public List<Commodity> getList() {
		return list;
	}

	/**
	 * 设置商品单
	 * 
	 * @param list
	 */
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
