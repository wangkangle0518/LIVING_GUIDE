package com.livingguide.common.office.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel
 */
public class ExcelUtils {
	private Workbook workbook;
	private InputStream is;
	private String regExDoule = "^\\-?[0-9]+\\.?[0-9]*$";
	private String url;

	public ExcelUtils(String url) {
		String ext = url.substring(url.lastIndexOf("."));
		try {
			is = new FileInputStream(url);
			if (".xls".equals(ext)) {
				workbook = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				workbook = new XSSFWorkbook(is);
			} else {
				workbook = null;
			}
			this.url = url;
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		try {
			String filepath = "C:\\Users\\Lele\\Desktop\\20180329数据拆分.xlsx";
			ExcelUtils eu = new ExcelUtils(filepath);
			Map<String, Commodity> map = eu.analysisExcel();
			SplitInvoice si = new SplitInvoice();
			List<Map.Entry<String, Commodity>> list = si.sort(map);
			List<Invoice> invoiceList = new ArrayList<>();
			si.equalsMAX(list, invoiceList);
			eu.ecportExcel(invoiceList);
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析excel
	 * 
	 * @return
	 * @throws IOException
	 */
	private Map<String, Commodity> analysisExcel() throws IOException {

		Pattern pD = Pattern.compile(regExDoule);
		// 获取每一个工作薄
		Map<String, Commodity> map = new HashMap<>();
		if (workbook == null) {
			return null;
		}
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			// 获取当前工作薄的每一行
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				Commodity commodity = new Commodity();
				if (row != null) {
					// 读取第一列数据
					Cell one = row.getCell(0);
					// 读取第二列数据
					Cell two = row.getCell(1);
					// 读取第三列数据
					Cell three = row.getCell(2);
					// 读取第四列数据
					Cell four = row.getCell(3);
					if (one != null && two != null && three != null && four != null) {
						Matcher mT = pD.matcher(three.toString());
						Matcher mF = pD.matcher(four.toString());
						// 字符串是否与正则表达式相匹配
						boolean rT = mT.matches();
						boolean rF = mF.matches();
						if (rT && rF) {
							commodity.setBarCode(one.toString());
							commodity.setName(two.toString());
							commodity.setNum(three.toString());
							commodity.setTotal(four.toString());
							map.put(one.toString(), commodity);
						}
					}
				}
			}
		}
		is.close();
		return map;
	}

	private void ecportExcel(List<Invoice> invoiceList) throws IOException {
		Sheet sheet = workbook.createSheet("发票单");
		// 设置缺省列高sheet.setDefaultColumnWidth(20);//设置缺省列宽
		// sheet.setDefaultRowHeightInPoints(10);
		// 设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一
		sheet.setColumnWidth(0, 256 * 15);
		sheet.setColumnWidth(1, 256 * 10);
		sheet.setColumnWidth(2, 256 * 10);
		sheet.setColumnWidth(3, 256 * 10);
		sheet.setColumnWidth(4, 256 * 50);
		int i = 0;
		Row row = sheet.createRow(i);
		row.createCell(0).setCellValue("条形码");
		row.createCell(1).setCellValue("数量");
		row.createCell(2).setCellValue("单价");
		row.createCell(3).setCellValue("总价");
		row.createCell(4).setCellValue("商品名");
		i++;
		for (Invoice invoice : invoiceList) {
			List<Commodity> commodityList = invoice.getList();
			i++;
			Row rowi = sheet.createRow(i);
			rowi.createCell(0).setCellValue("发票清单：");
			BigDecimal bg = new BigDecimal("0");
			for (Commodity commodity : commodityList) {
				i++;
				Row rows = sheet.createRow(i);
				rows.createCell(0).setCellValue(commodity.getBarCode());
				rows.createCell(1).setCellValue(Double.parseDouble(commodity.getNum()));
				rows.createCell(2).setCellValue(Double.parseDouble(commodity.getUnitPrice()));
				rows.createCell(3).setCellValue(Double.parseDouble(commodity.getTotal()));
				rows.createCell(4).setCellValue(commodity.getName());
				bg = bg.add(new BigDecimal(commodity.getTotal()));
			}
			i++;
			Row rows = sheet.createRow(i);
			rows.createCell(0).setCellValue("发票总值：");
			rows.createCell(1).setCellValue(bg.doubleValue());
			i++;
		}
		OutputStream os = new FileOutputStream(url);
		workbook.write(os);
		os.close();
	}
}