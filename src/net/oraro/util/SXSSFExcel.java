package net.oraro.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class SXSSFExcel {
	 
	private static Logger log = Logger.getLogger(SXSSFExcel.class);
	
	public static final String TEMP_EXCEL_DIR = SXSSFExcel.class.getResource("/").getPath();
	
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
	     Sheet sh = wb.createSheet();
	     
	     for (int i = 0; i < datas.size(); i++) {
	    	 Map<String, String> map = datas.get(i);
			 if(map == null) continue;
			 
			 Row row = sh.createRow(i);
			 for (int j = 0; j < keys.length; j++) {
				String key = keys[j]; 
			 	String value = map.get(key);
			 	if(value == null) value = "";
			 	Cell cell = row.createCell(j);
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
