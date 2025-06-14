package sauce.qa.ecom.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	private Workbook book;
	private Sheet sheet;
	public static final String PATH = "./src/test/resources/TestData/ExcelData.xlsx";
Object data[][]=null;
	public Object[][] getExcelData(String sheetName) {
		try {
		FileInputStream ip = new FileInputStream(PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
			data =new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for(int i=0; i<sheet.getLastRowNum();i++){
				for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
					data[i][j]=sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException  e) {e.printStackTrace();}
		catch (InvalidFormatException  e) {e.printStackTrace();}
		catch (IOException e) {	e.printStackTrace();}
			return data;
		}
}
