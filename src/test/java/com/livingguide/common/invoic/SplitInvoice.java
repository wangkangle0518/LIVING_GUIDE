package com.livingguide.common.invoic;

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

	public static void main(String[] args) {
		Map<String, Commodity> map = new HashMap<>();
		Commodity i1 = new Commodity("0", "7682.40", "3600", "俏香阁 每日坚果 坚果炒货 核桃仁巴旦木混合坚果25g/袋");
		Commodity i2 = new Commodity("6970506020083", "13401.12", "412", "俏香阁 休闲零食 肉干肉脯 独立小包装 麻辣牛肉干 400g/袋");
		Commodity i3 = new Commodity("6970506020090", "77997.35", "7228", "俏香阁 休闲零食 肉干肉脯 独立小包装 麻辣牛肉粒 100g/袋");
		Commodity i4 = new Commodity("6970952280024", "116946.72", "2140", "俏礼聚福1557g");
		Commodity i5 = new Commodity("6970952280062", "223403.40", "3600", "俏香阁 每日坚果 零食大礼包10袋装  狗年定制版京喜年礼1845g");
		map.put(i1.getBarCode(), i1);
		map.put(i2.getBarCode(), i2);
		map.put(i3.getBarCode(), i3);
		map.put(i4.getBarCode(), i4);
		map.put(i5.getBarCode(), i5);
		SplitInvoice si = new SplitInvoice();
		List<Map.Entry<String, Commodity>> list = si.sort(map);
		List<Invoice> invoiceList = new ArrayList<>();
		si.equalsMAX(list, invoiceList);
		for (Invoice invoice : invoiceList) {
			System.out.println(invoice.toString());
		}
	}

	private List<Map.Entry<String, Commodity>> sort(Map<String, Commodity> map) {
		List<Map.Entry<String, Commodity>> list = new ArrayList<Map.Entry<String, Commodity>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Commodity>>() {
			// 升序排序
			public int compare(Entry<String, Commodity> o1, Entry<String, Commodity> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		return list;
	}

	private void equalsMAX(List<Map.Entry<String, Commodity>> list, List<Invoice> invoiceList) {
		Invoice invoice = new Invoice();
		commoditySub = commodityMax;
		for (int i = 0; i < list.size(); i++) {
			setAllInvoice(invoice, commoditySub, list, i);
		}
		invoiceList.add(invoice);
		List<Map.Entry<String, Commodity>> listTemp = new ArrayList<>();
		for (Entry<String, Commodity> map : list) {
			if (!map.getValue().getNum().equals("0")) {
				listTemp.add(map);
			}
		}
		if (listTemp.size() > 0) {
			equalsMAX(listTemp, invoiceList);
		}
	}
	
	private String divide(String bd1, String bd2) {
		return String.valueOf(new BigDecimal(bd1).divide(new BigDecimal(bd2), 0, RoundingMode.DOWN).doubleValue());
	}
	
	private String subtract(String bd1, String bd2) {
		return new BigDecimal(bd1).subtract(new BigDecimal(bd2)).toString();
	}

	
	private String multiply(String bd1, String bd2) {
		return new BigDecimal(bd1).multiply(new BigDecimal(bd2)).toString();
	}
	
	/**
	 * @param invoiceList
	 * @param bd
	 * @param list
	 * @param i
	 */
	private void setInvoice(Invoice invoice, String bd, List<Map.Entry<String, Commodity>> list, int i) {
		if (i < list.size()) {
			// 差量 = 差价/单价
			String dd = divide(bd, list.get(i).getValue().getUnitPrice());
			int num;
			if (dd.indexOf(".") > 0) {
				num = Integer.valueOf(dd.substring(0, dd.indexOf(".")));
			} else {
				num = Integer.valueOf(dd);
			}
			if (Integer.valueOf(list.get(i).getValue().getNum()) > 0 && num >= 1) {
				// 当前物品可转移的数量
				int newNum = Integer.parseInt(list.get(i).getValue().getNum()) - num;
				// 创建新物品集
				Commodity cd = new Commodity();
				if (newNum > 0) {
					// 转移物品
					Commodity cdTemp = list.get(i).getValue();
					cd.setBarCode(cdTemp.getBarCode());
					cd.setName(cdTemp.getName());
					cd.setNum(String.valueOf(num));
					cd.setUnitPrice(cdTemp.getUnitPrice());
					cd.getTotal();
					// 加入发票
					invoice.getList().add(cd);
					// 原物品集减去相当数量
					list.get(i).getValue().setNum(String.valueOf(newNum));
				} else {
					// 转移物品
					Commodity cdTemp = list.get(i).getValue();
					cd.setBarCode(cdTemp.getBarCode());
					cd.setName(cdTemp.getName());
					cd.setNum(cdTemp.getNum());
					cd.setUnitPrice(cdTemp.getUnitPrice());
					cd.getTotal();
					// 加入发票
					invoice.getList().add(cd);
					// 原物品集减去相当数量
					list.get(i).getValue().setNum("0");
				}
				// 新差价 = 差价-物品*数量
				commoditySub = subtract(bd, multiply(cd.getUnitPrice(), cd.getNum()));
			} else {
				commoditySub = bd;
			}
			i++;
			setAllInvoice(invoice, commoditySub, list, i);
		} else {
			commoditySub = bd;
		}
	}
	
	private void setAllInvoice(Invoice invoice, String sub, List<Map.Entry<String, Commodity>> list, int i) {
		// 是否还有差量 = 差价-最小单价
		String min = divide(sub, list.get(list.size() - 1).getValue().getUnitPrice());
		if (Double.valueOf(min) >= 1) {
			setInvoice(invoice, sub, list, i);
		}
	}
	
}
