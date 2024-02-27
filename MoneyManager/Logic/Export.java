package Logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;  

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;   
//import org.apache.poi.ss.usermodel.Workbook;   

public class Export{
	
	private java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

	public Export(){
		
		createFile("Exported Files/" + currentDate + " Transaction History.csv");
	}
	
	public Export(String type){
		if(type.equals("xlsx")) createXlsx("Exported Files/" + currentDate + " Transaction History.xlsx");
	}
	
	public void createFile(String name){
		try{
			File outputFile = new File(name);
			outputFile.createNewFile();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void createXlsx(String name){
		//Workbook wb = new HSSFWorkbook();
		try{
			FileOutputStream outputFile = new FileOutputStream(name);
			//wb.write(outputFile);
			outputFile.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}