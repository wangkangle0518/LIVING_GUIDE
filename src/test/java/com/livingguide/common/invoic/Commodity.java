package com.livingguide.common.invoic;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 商品集
 */
public class Commodity {

	/**
	 * 条形码
	 */
	private String barCode;
	/**
	 * 总价
	 */
	private String total;
	/**
	 * 数量
	 */
	private String num;
	/**
	 * 单价
	 */
	private String unitPrice;
	/**
	 * 商品名
	 */
	private String name;

	public Commodity() {
		super();
	}

	public Commodity(String barCode, String total, String num, String name) {
		super();
		this.barCode = barCode;
		this.total = total;
		this.num = num;
		this.unitPrice = new BigDecimal(this.total).divide(new BigDecimal(this.num), 4, RoundingMode.HALF_UP).toString();
		this.name = name;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotal() {
		if (this.num.equals("0")) {
			return "0";
		}
		this.total = new BigDecimal(this.num).multiply(new BigDecimal(this.unitPrice)).toString();
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "条形码：" + getBarCode() + ", 单价：" + getUnitPrice() + ", 数量：" + getNum() + ", 总价：" + getTotal() + ", 名称: "
				+ name + ";\n";
	}

	public int compareTo(Commodity commodity) {
		
		if (Double.parseDouble(this.unitPrice) == Double.parseDouble(commodity.getUnitPrice())) {
			return 0;
		} else if (Double.parseDouble(this.unitPrice) > Double.parseDouble(commodity.getUnitPrice())) {
			return -1;
		}
		return 1;
	}

}
