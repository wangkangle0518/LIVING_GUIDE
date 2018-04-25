package com.livingguide.common.office.excel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SplitInvoice {
	private static String commodityMax = "117000";
	private static String commoditySub;

	private void sort(Invoice invoice) {
		Collections.sort(invoice.getList(), new Comparator<Commodity>() {
			// 排序
			public int compare(Commodity o1, Commodity o2) {
				return o1.compareTo(o2);
			}

		});
	}

	public void equalsMAX(Invoice invoice, List<Invoice> invoiceList) {
		sort(invoice);
		Invoice ic = new Invoice();
		commoditySub = commodityMax;
		setInvoice(ic, invoice, 0);
		if (ic.getList().size() > 0) {
			invoiceList.add(ic);
		}
		Invoice invoiceTemp = new Invoice();
		if (invoice.getList().size() == 0) {
			return;
		}
		for (Commodity commodity : invoice.getList()) {
			if (!commodity.getNum().equals("0")) {
				invoiceTemp.getList().add(commodity);
			}
		}
		if (invoice.getList().size() > 0) {
			equalsMAX(invoiceTemp, invoiceList);
		}
	}

	/**
	 * 生成发票单
	 * 
	 * @param invoice
	 * @param list
	 * @param i
	 */
	private void setInvoice(Invoice invoice, Invoice invoice2, int i) {
		// 是否还有差量 = 差价-最小单价
		if (i < invoice2.getList().size()) {
			// 差量 = 差价/单价
			String cs = divide(commoditySub, invoice2.getList().get(i).getUnitPrice());
			if (cs.length() > 1 && cs.indexOf(".") > 0) {
				cs = cs.substring(0, cs.indexOf("."));
			}
			int num = Integer.valueOf(cs);
			// 如果还有差量，在发票单中添加商品集
			if (num >= 1) {
				addCommodity(invoice, invoice2, num, i);
			}
			setInvoice(invoice, invoice2, ++i);
		}
	}

	/**
	 * 设置符合条件商品集
	 * 
	 * @param invoice
	 * @param list
	 * @param i
	 */
	private void addCommodity(Invoice invoice, Invoice invoice2, int num, int i) {
		int oldNum = (int) Double.parseDouble(invoice2.getList().get(i).getNum());
		if (oldNum > 0 && num >= 1) {
			// 当前物品可转移的数量
			// 创建新物品集
			Commodity commodity = new Commodity();
			Commodity commodityTemp = invoice2.getList().get(i);
			// 转移物品
			commodity.setBarCode(commodityTemp.getBarCode());
			commodity.setName(commodityTemp.getName());
			commodity.setUnitPrice(commodityTemp.getUnitPrice());
			commodity.setCess(commodityTemp.getCess());
			int newNum = oldNum - num;
			// 原物品集减去相当数量
			if (newNum > 0) {
				commodity.setNum(String.valueOf(num));
//				list.get(i).getValue().setNum(String.valueOf(newNum));
				commodityTemp.setNum(String.valueOf(newNum));
			} else {
				commodity.setNum(commodityTemp.getNum());
//				list.get(i).getValue().setNum("0");
				commodityTemp.setNum("0");
			}
			commodity.getTotal();
			// 加入发票
			invoice.getList().add(commodity);
			// 新差价 = 差价-物品*数量
			commoditySub = subtract(commoditySub, multiply(commodity.getUnitPrice(), commodity.getNum()));
		}
	}
	
	private String divide(String bd1, String bd2) {
		return String.valueOf(strToBD(bd1).divide(strToBD(bd2), 0, RoundingMode.DOWN).doubleValue());
	}

	private String subtract(String bd1, String bd2) {
		return strToBD(bd1).subtract(strToBD(bd2)).toString();
	}

	private String multiply(String bd1, String bd2) {
		return strToBD(bd1).multiply(strToBD(bd2)).toString();
	}
	
	private BigDecimal strToBD(String str) {
		return new BigDecimal(str);
	}
}
