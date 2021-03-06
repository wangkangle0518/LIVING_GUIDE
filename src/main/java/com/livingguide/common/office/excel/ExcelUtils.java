package com.livingguide.common.office.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
	private static final String[] EXTENSIONS = new String[] { "xls", "xlsx", "bmp" };

	public ExcelUtils(String url) throws IOException {
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

	// filter to identify images based on their extensions
	private static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		// @Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	public static void main(String[] args) {
		try {
			String filepath = "D:\\商品分析";
			File file = new File(filepath);
			// make sure it's a directory
			if (file.exists() && !file.isDirectory()) {
				file.delete();
				throw new IOException("文件夹不存在");
			}
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
				throw new IOException("文件夹不存在");
			}
			for (File f : file.listFiles(IMAGE_FILTER)) {
				ExcelUtils eu = new ExcelUtils(f.getPath());
				Map<String, Invoice> map = eu.analysisExcel();
				if (map == null || map.size() == 0) {
					continue;
				}
				for (String cess : map.keySet()) {
					SplitInvoice si = new SplitInvoice();
					List<Invoice> invoiceList = new ArrayList<>();
					si.equalsMAX(map.get(cess), invoiceList);
					eu.ecportExcel(invoiceList, cess);
				}
				eu.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				File file = new File("D:\\商品分析\\异常记录.txt");
				if (file.exists()) {
					file.delete();
				}
				if (file.createNewFile()) {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write("异常信息：" + e);
					bw.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 解析excel
	 * 
	 * @return
	 * @throws IOException
	 */
	private Map<String, Invoice> analysisExcel() throws IOException {

		Pattern pD = Pattern.compile(regExDoule);
		// 获取每一个工作薄
		Map<String, Invoice> map = new HashMap<>();
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
					// 读取第五行数据
					Cell five = row.getCell(4);
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
							String cess = five.toString();
							commodity.setCess(cess);
							if (!map.containsKey(cess)) {
								map.put(cess, new Invoice());
							}
							map.get(cess).getList().add(commodity);
						}
					}
				}
			}
		}
		is.close();
		return map;
	}

	private void ecportExcel(List<Invoice> invoiceList, String cess) throws IOException {
		Sheet sheet = workbook.createSheet("税率"+cess+"的发票单");
		// 设置缺省列高sheet.setDefaultColumnWidth(20);//设置缺省列宽
		// sheet.setDefaultRowHeightInPoints(10);
		// 设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一
		sheet.setColumnWidth(0, 256 * 15);
		sheet.setColumnWidth(1, 256 * 15);
		sheet.setColumnWidth(2, 256 * 15);
		sheet.setColumnWidth(3, 256 * 15);
		sheet.setColumnWidth(4, 256 * 55);
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("条形码");
		row.createCell(1).setCellValue("数量");
		row.createCell(2).setCellValue("单价");
		row.createCell(3).setCellValue("总价");
		row.createCell(4).setCellValue("商品名");
		int i = 1;
		BigDecimal bg;
		for (Invoice invoice : invoiceList) {
			List<Commodity> commodityList = invoice.getList();
			if (commodityList.size() == 0) {
				continue;
			}
			Row rowi = sheet.createRow(++i);
			rowi.createCell(0).setCellValue("发票清单：");
			bg = new BigDecimal("0");
			for (Commodity commodity : commodityList) {
				Row rows = sheet.createRow(++i);
				rows.createCell(0).setCellValue(commodity.getBarCode());
				rows.createCell(1).setCellValue(Double.parseDouble(commodity.getNum()));
				rows.createCell(2).setCellValue(Double.parseDouble(commodity.getUnitPrice()));
				rows.createCell(3).setCellValue(Double.parseDouble(commodity.getTotal()));
				rows.createCell(4).setCellValue(commodity.getName());
				rows.createCell(5).setCellValue(commodity.getCess());
				bg = bg.add(new BigDecimal(commodity.getTotal()));
			}
			Row rows = sheet.createRow(++i);
			rows.createCell(0).setCellValue("发票总值：");
			Cell cell = rows.createCell(3);
			CellStyle style = workbook.createCellStyle();
			// 设置背景颜色
			style.setFillPattern(FillPatternType.FINE_DOTS);
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cell.setCellValue(bg.doubleValue());
			cell.setCellStyle(style);
			i++;
			bg = null;
		}
	}
	
	public void close() throws IOException {
		OutputStream os = new FileOutputStream(url);
		workbook.write(os);
		os.close();
		workbook.close();
	}
}