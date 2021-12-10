package compare;

import org.apache.poi.xssf.usermodel.XSSFRow;

public class Index_find extends compare{
	public static void idx_reset() {
		for(int i=0;i<5;i++) {
			idx_items_db[i]=0;
		}
	}
	public static void idx_items_DB_cnt() {
		for(int i=0;i<ret.get(0).size();i++) {
			if(ret.get(0).get(i).compareTo("pid")==0) {
				idx_items_setDB[0] = i;
			}else if(ret.get(0).get(i).compareTo("-")==0) {
				idx_items_setDB[1] = i;
			}else if(ret.get(0).get(i).compareTo("pmin")==0) {
				idx_items_setDB[2] = i;
			}else if(ret.get(0).get(i).compareTo("pmax")==0) {
				idx_items_setDB[3] = i;
			}else if(ret.get(0).get(i).compareTo("paddress")==0) {
				idx_items_setDB[4] = i;
			}else if(ret.get(0).get(i).compareTo("pbit")==0) {
				idx_items_setDB[5] = i;
			}else if(ret.get(0).get(i).compareTo("pdec")==0) {
				idx_items_setDB[6] = i;
			}
		}
	}
	public static void idx_items_cnt() {
		idx_reset();
		XSSFRow row = sheet.getRow(0);
		for(int i= 1 ; i < row.getLastCellNum(); i++) {
			if(row.getCell(i).toString().toLowerCase().compareTo("id")==0) {
				idx_items_db[0]=i;
			}else if(row.getCell(i).toString().toLowerCase().compareTo("min")==0) {
				idx_items_db[1]=i;
			}else if(row.getCell(i).toString().toLowerCase().compareTo("max")==0) {
				idx_items_db[2]=i;
			}else if(row.getCell(i).toString().toLowerCase().compareTo("dec")==0) {
				idx_items_db[3]=i;
			}else if(row.getCell(i).toString().toLowerCase().compareTo("address")==0) {
				idx_items_db[4]=i;
			}else if(row.getCell(i).toString().toLowerCase().compareTo("type")==0) {
				idx_items_db[5]=i;
			}
		}

	}
}
