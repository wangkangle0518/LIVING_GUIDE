package com.livingguide.common.office.excel;

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

	/**
	 * 创建商品集
	 * 
	 * @param barCode
	 * @param total
	 * @param num
	 * @param name
	 */
	public Commodity(String barCode, String total, String num, String name) {
		super();
		this.barCode = barCode;
		this.total = total;
		this.num = num;
		this.unitPrice = new BigDecimal(this.total).divide(new BigDecimal(this.num), 4, RoundingMode.HALF_UP)
				.toString();
		this.name = name;
	}

	/**
	 * 获取条形码
	 * 
	 * @return
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * 设置条形码
	 * 
	 * @param barCode
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * 获取商品名
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 生成总价并获取值
	 * 
	 * @return
	 */
	public String getTotal() {
		if (this.num.equals("0")) {
			return "0";
		}
		this.total = new BigDecimal(this.num).multiply(new BigDecimal(this.unitPrice)).toString();
		return this.total;
	}

	/**
	 * 设置总价
	 * 
	 * @param total
	 */
	public void setTotal(String total) {
		this.total = total;
		this.unitPrice = new BigDecimal(this.total).divide(new BigDecimal(this.num), 4, RoundingMode.HALF_UP)
				.toString();
	}

	/**
	 * 获取数量
	 * 
	 * @return
	 */
	public String getNum() {
		return num;
	}

	/**
	 * 设置数量
	 * 
	 * @param num
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * 获取单价
	 * 
	 * @return
	 */
	public String getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置单价
	 * 
	 * @param unitPrice
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "条形码：" + getBarCode() + ", 单价：" + getUnitPrice() + ", 数量：" + getNum() + ", 总价：" + getTotal() + ", 名称: "
				+ getName() + ";\n";
	}

	/**
	 * 比较单价
	 * 
	 * @param commodity
	 * @return
	 */
	public int compareTo(Commodity commodity) {
		if (Double.parseDouble(this.getTotal()) == Double.parseDouble(commodity.getTotal())) {
			return 0;
		} else if (Double.parseDouble(this.getTotal()) > Double.parseDouble(commodity.getTotal())) {
			return -1;
		}
		return 1;
	}

}
