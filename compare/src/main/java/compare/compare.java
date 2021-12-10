package compare;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell; 
import org.apache.poi.xssf.usermodel.XSSFRow; 
import org.apache.poi.xssf.usermodel.XSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class compare {
	static FileInputStream file;
	static int rows;
	static XSSFSheet sheet;
	static List<List<String>> ret = new ArrayList<List<String>>();
	static BufferedReader br = null;
	static XSSFWorkbook workbook = new XSSFWorkbook();
	static XSSFSheet c_sheet = workbook.createSheet();
	static XSSFRow c_row;
	static XSSFCell c_cell;
	static int[] idx_items_db = {0, 0, 0, 0, 0, 0};
	static int[] idx_items_setDB = {0, 0, 0, 0, 0, 0, 0};
	static int cnt=0;
	
	private static void set_style(CellStyle style, int cnt) {
		for(int j=0;j<cnt;j++) {
			c_row.getCell(j).setCellStyle(style);
		}
	}
	private static void set_cell_value(String[] cell_data, String result) {
		for (int i=0;i<cell_data.length;i++) {
			c_row.createCell(i).setCellValue(cell_data[i]);
		}
		c_row.createCell(cell_data.length).setCellValue(result);
	}
	private static void compare_DB() {
		Data_setting dsetting = new Data_setting();
		CellStyle style = workbook.createCellStyle();
		CellStyle style_title = workbook.createCellStyle();
		Index_find idx = new Index_find(); 
		int rowNo = 0;
		
		String[] cell_title = {"id", "min", "max", "dec", "address", "bit", 
				"pid", "-","pmin", "pmax", "pdec", 
				"paddress", "pbit", "RESULT"};
		idx.idx_items_cnt();
		
		for(int i=0;i<ret.size();i++) {
			for(rowNo = 1 ; rowNo < rows ; rowNo++) { // PDB
				XSSFRow row = sheet.getRow(rowNo);
				int cells = row.getPhysicalNumberOfCells();

				String id = "", pc = "", pmin = "", pmax = "", pdec="",
						min="", max="", dec="", bit="", pbit = "",
						addr="", pdb_addr="";
				String pid = ret.get(i).get(0).toString(); 
				String result ="TRUE"; 
				
				
				if(row.getCell(idx_items_db[0]) == null) {
					id = "-";
				}
				else {
					id = row.getCell(idx_items_db[0]).toString(); 

					if(id.compareTo(pid)==0) {
						if((row.getCell(idx_items_db[1]) != null) && (idx_items_db[1] != 0)) {
							row.getCell(idx_items_db[1]).setCellType(CellType.STRING);
							min = row.getCell(idx_items_db[1]).getStringCellValue();
							pmin = ret.get(i).get(idx_items_setDB[2]);
							
							min = dsetting.set_dec_place(min);
							pmin = dsetting.set_dec_place(pmin);
							
						}
						if(row.getCell(idx_items_db[2]) != null && (idx_items_db[2] != 0)) {
							row.getCell(idx_items_db[2]).setCellType(CellType.STRING);
							max = row.getCell(idx_items_db[2]).getStringCellValue();
							pmax = ret.get(i).get(idx_items_setDB[3]);
							
							max = dsetting.set_dec_place(max);
							pmax = dsetting.set_dec_place(pmax);
							
						}
						if(row.getCell(idx_items_db[3]) != null && (idx_items_db[3] != 0)) {
							row.getCell(idx_items_db[3]).setCellType(CellType.STRING);
							dec = row.getCell(idx_items_db[3]).getStringCellValue();
							pdec = ret.get(i).get(idx_items_setDB[6]);
							
							dec = dsetting.set_dec_place(dec);
							pdec = dsetting.set_dec_place(pdec);
						}
						if(row.getCell(idx_items_db[4]) != null) {
							row.getCell(idx_items_db[4]).setCellType(CellType.STRING);
							String Parsing = row.getCell(idx_items_db[4]).getStringCellValue();
							StringTokenizer addr_token2 = new StringTokenizer(Parsing, ";");
							StringTokenizer addr_token = new StringTokenizer(addr_token2.nextToken(), "/");
							ArrayList<String> addr_list = new ArrayList<String>();
							
							while(addr_token.hasMoreTokens()) {
								addr_list.add(addr_token.nextToken());
							}
							pdb_addr = addr_list.get(3).substring(4);
							pdb_addr = dsetting.set_addr(row, pdb_addr);
							
							if(addr_list.size()>=5) {
								bit = addr_list.get(4);
							}
							addr = ret.get(i).get(idx_items_setDB[4]).toString();
							addr = addr.substring(6);
							
							if(ret.get(i).size()>24) {
								pbit = ret.get(i).get(idx_items_setDB[5]).toString();
							}
							if(cnt == 0) {
								style_title.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
								style_title.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								style_title.setAlignment(HorizontalAlignment.CENTER);
								
								Font font = workbook.createFont();
								font.setBold(true);
								style_title.setFont(font);
								
								c_row = c_sheet.createRow(cnt++);
								
								for (int k=0; k<cell_title.length ; k++) {
									c_row.createCell(k).setCellValue(cell_title[k]);	
								}
								
								set_style(style_title, cell_title.length);
								
								
							}
							
							String[] set_Cell_Data = {id, min, max, dec, pdb_addr, bit,
									pid, pc, pmin, pmax, pdec, addr, pbit};
							
							
							if((pdb_addr.compareTo(addr)==0)&&(bit.compareTo(pbit)==0)
									&&(min.compareTo(pmin)==0)&&(max.compareTo(pmax)==0)
									&&(dec.compareTo(pdec)==0)) {
								c_row = c_sheet.createRow(cnt++);
								set_cell_value(set_Cell_Data, result);
							}else {
								style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
								style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								
								c_row = c_sheet.createRow(cnt++);
								
								result = "FALSE";
								
								set_cell_value(set_Cell_Data, result);
								set_style(style, set_Cell_Data.length);
							}
							
						}
						else {
							style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
							style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							
							c_row = c_sheet.createRow(cnt++);
							
							addr = ret.get(i).get(idx_items_setDB[4]).toString();
							addr = addr.substring(6);
							if(ret.get(i).size()>24) {
								pbit = ret.get(i).get(idx_items_setDB[5]).toString();
							}
							String[] set_Cell_Data = {id, min, max, dec, "NULL", bit,
									pid, pc, pmin, pmax, pdec, addr, pbit};
							
							result = "FALSE";
							set_cell_value(set_Cell_Data, result);
							set_style(style, set_Cell_Data.length);
						}
						
						break;
					}else {
					}
				
				}
			
			}
		}
	}
	
	public static void main(String[] args) {
		Index_find idx = new Index_find(); 
		String[] p_sheet = {"A", "D", "A2", "D2"};
		// TODO Auto-generated method stub

		try {
			file = new FileInputStream("./DB_RAW.xlsx");
			br = Files.newBufferedReader(Paths.get("./p_db.csv"));
			// xlsx
			XSSFWorkbook wb = new XSSFWorkbook(file);
			
			int cellIndex = 0;

			// csv 
			String line ="";
			List<String> tmpList;
			
			
			while((line=br.readLine()) != null) {
				tmpList = new ArrayList<String>();
				String array[] = line.split(",");
				
				tmpList = Arrays.asList(array);
				ret.add(tmpList);
			}
			
			idx.idx_items_DB_cnt();
			
			for(int i=0;i<4;i++) {
				sheet = wb.getSheet(p_sheet[i]);
				rows = sheet.getPhysicalNumberOfRows();
				compare_DB(); 
			}
 
			try {
				FileOutputStream fout = new FileOutputStream("./compare_result.xlsx");
				workbook.write(fout);
				fout.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(br!=null) {
				br.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
