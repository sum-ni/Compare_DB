package compare;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class Data_setting extends compare{
	public static String set_dec_place(String str) {
		char[] buf = new char[str.length()];
		String result = "";
		for (int i = 0 ; i < str.length() ; i++) {
			buf[i] = str.charAt(i);
		}
		if(buf.length > 2) {
			if(buf[buf.length-2] == '.' && buf[buf.length-1] == '0') {
				for(int i=0;i<str.length()-2;i++) {
					result = str.substring(0, str.length()-2);
				}
				str = result;
			}
		}
		
		return str;
	}
	public static String set_addr(XSSFRow row, String str) {
		int num = 0;
		String type =row.getCell(idx_items_db[5]).toString();
		if(row.getCell(idx_items_db[5]) != null) {
			num = Integer.parseInt(str);
			if(row.getCell(idx_items_db[5]).getCellType()==CellType.FORMULA) {
				type = row.getCell(idx_items_db[5]).getStringCellValue();
			}
			if(type.compareTo("A")==0) {
				num = num;
			}else if(type.compareTo("B")==0) { 
				num = num + 440;
			}else if(type.compareTo("C")==0) {
				num = num +880;
			}else if(type.compareTo("D")==0) {
				num = num + 1100;
			}else if(type.compareTo("E")==0) {
				num = num + 1320;
			}else if(type.compareTo("F")==0) {
				num = num + 1430;
			}else if(type.compareTo("G")==0) {
				num = num + 1540;
			}else if(type.compareTo("H")==0) {
				num = num + 1650;
			}else if(type.compareTo("I")==0) {
				num = num + 1760;
			}else if(type.compareTo("J")==0) {
				num = num + 1810;
			}else if(type.compareTo("K")==0) {
				num = num + 1870;
			}else if(type.compareTo("L")==0) {
				num = num + 1920;
			}else if(type.compareTo("M")==0) {
				num = num + 1980;
			}else if(type.compareTo("N")==0) {
				num = num + 2030;
			}
			str = Integer.toString(num);
		}
		return str;
	}
}
