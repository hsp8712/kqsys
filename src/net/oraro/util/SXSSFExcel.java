package net.oraro.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class SXSSFExcel {
	 
	private static Logger log = Logger.getLogger(SXSSFExcel.class);
	
	public static final String TEMP_EXCEL_DIR = SXSSFExcel.class.getResource("/").getPath() + "temp_excel_dir/";
	static {
		File tempExcelDir = new File(TEMP_EXCEL_DIR);
		if(!tempExcelDir.exists()) {
			tempExcelDir.mkdirs();
		}
	}
	
	public static String createExcel(List<Map<String, String>> datas, String[] keys) {
		 
		 if(datas == null || datas.size() <= 0) {
			 log.error("The datas is null or empty.");
			 return null;
		 }
		 
		 if(keys == null || keys.length <= 0) {
			 log.error("The keys is null or empty.");
			 return null;
		 }
		 
		 SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		 DataFormat dataFormat = wb.createDataFormat();
		 Font font = wb.createFont();
		 font.setFontHeight((short) 210);
		 font.setFontName("宋体");
		 CellStyle cellStyle = wb.createCellStyle();
		 cellStyle.setDataFormat(dataFormat.getFormat("@"));
		 cellStyle.setFont(font);
		 cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		 cellStyle.setBottomBorderColor(HSSFColor.BLUE_GREY.index);
		 cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//		 cellStyle.setTopBorderColor(HSSFColor.BLUE_GREY.index);
		 cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		 cellStyle.setLeftBorderColor(HSSFColor.BLUE_GREY.index);
		 cellStyle.setBorderRight(CellStyle.BORDER_THIN);
//		 cellStyle.setRightBorderColor(HSSFColor.BLUE_GREY.index);
		 
		 Sheet sh = wb.createSheet();
		 sh.setDefaultRowHeight((short) 350 );
	     for (int i = 0; i < datas.size(); i++) {
	    	 Map<String, String> map = datas.get(i);
			 if(map == null) continue;
			 
			 Row row = sh.createRow(i);
			 for (int j = 0; j < keys.length; j++) {
				 
				 if(i == 0) {
					 sh.setColumnWidth(j, 17 << 8);
				 }
				 
				String key = keys[j]; 
			 	String value = map.get(key);
			 	if(value == null) value = "";
			 	Cell cell = row.createCell(j);
			 	cell.setCellStyle(cellStyle);
			 	cell.setCellValue(value);
			 }
	     }
	     
	     String filePath = TEMP_EXCEL_DIR + genTempFileName();
	     filePath = filePath.replaceAll("%20", " ");
	     try {
			FileOutputStream out = new FileOutputStream(filePath);
			 wb.write(out);
			 out.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
	     // dispose of temporary files backing this workbook on disk
	     wb.dispose();
	     return filePath;
	 }
	
	public static String genTempFileName() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmsss");
		String time = df.format(new Date());
		String threadNo = Thread.currentThread().getId() + "";
		return time + threadNo;
	}

}
