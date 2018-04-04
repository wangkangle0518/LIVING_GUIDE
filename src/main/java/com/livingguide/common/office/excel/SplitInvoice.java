package com.livingguide.common.office.excel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SplitInvoice {
	private static String commodityMax = "117000";
	private static String commoditySub;

	private List<Map.Entry<String, Commodity>> sort(Map<String, Commodity> map) {
		List<Map.Entry<String, Commodity>> list = new ArrayList<Map.Entry<String, Commodity>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Commodity>>() {
			// 排序
			public int compare(Entry<String, Commodity> o1, Entry<String, Commodity> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		return list;
	}

	public void equalsMAX(Map<String, Commodity> cdMap, List<Invoice> invoiceList) {
		List<Map.Entry<String, Commodity>> list = sort(cdMap);
		Invoice invoice = new Invoice();
		commoditySub = commodityMax;
		setInvoice(invoice, list, 0);
		invoiceList.add(invoice);
		Map<String, Commodity> mapTemp = new HashMap<>();
		for (Entry<String, Commodity> map : list) {
			Commodity commodity = map.getValue();
			if (!map.getValue().getNum().equals("0")) {
				mapTemp.put(commodity.getBarCode(), commodity);
			}
		}
		if (mapTemp.size() > 0) {
			equalsMAX(mapTemp, invoiceList);
		}
	}

	/**
	 * 生成发票单
	 * 
	 * @param invoice
	 * @param list
	 * @param i
	 */
	private void setInvoice(Invoice invoice, List<Map.Entry<String, Commodity>> list, int i) {
		// 是否还有差量 = 差价-最小单价
		if (i < list.size()) {
			// 差量 = 差价/单价
			String cs = divide(commoditySub, list.get(i).getValue().getUnitPrice());
			if (cs.length() > 1 && cs.indexOf(".") > 0) {
				cs = cs.substring(0, cs.indexOf("."));
			}
			int num = Integer.valueOf(cs);
			// 如果还有差量，在发票单中添加商品集
			if (num >= 1) {
				addCommodity(invoice, list, num, i);
			}
			setInvoice(invoice, list, ++i);
		}
	}

	/**
	 * 设置符合条件商品集
	 * 
	 * @param invoice
	 * @param list
	 * @param i
	 */
	private void addCommodity(Invoice invoice, List<Map.Entry<String, Commodity>> list, int num, int i) {
		int oldNum = (int) Double.parseDouble(list.get(i).getValue().getNum());
		if (oldNum > 0 && num >= 1) {
			// 当前物品可转移的数量
			// 创建新物品集
			Commodity commodity = new Commodity();
			Commodity commodityTemp = list.get(i).getValue();
			// 转移物品
			commodity.setBarCode(commodityTemp.getBarCode());
			commodity.setName(commodityTemp.getName());
			commodity.setUnitPrice(commodityTemp.getUnitPrice());
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
