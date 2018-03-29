package com.livingguide.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Invoice {

	private double total;
	private int num;
	private double unitPrice;
	private String name;

	public Invoice(double total, int num, String name) {
		super();
		this.total = total;
		this.num = num;
		this.name = name;
		BigDecimal bd1 = new BigDecimal(total);
		BigDecimal bd2 = new BigDecimal(num);
		this.unitPrice = bd1.divide(bd2, 2, RoundingMode.HALF_UP).doubleValue();
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "：单价是：" + unitPrice + ", 数量是：" + num + ", 总价是：" + total;
	}

	public static void main(String[] args) {
		Invoice i = new Invoice(7682.40, 3600, "俏香阁 每日坚果 坚果炒货 核桃仁巴旦木混合坚果25g/袋");
		System.out.println(i.toString());
	}
}
