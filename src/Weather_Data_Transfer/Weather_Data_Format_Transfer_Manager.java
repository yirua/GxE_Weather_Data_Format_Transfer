package Weather_Data_Transfer;


import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * This program will check the file structures and transfer them into one table of MYSQL table through 
 * one Arraylist of weather station info objects..
 * @author yiweisun
 *
 */

public class Weather_Data_Format_Transfer_Manager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		boolean check_file_result=false;
		Weather_Data_Structure_Checker weather_data_structure_checker = new Weather_Data_Structure_Checker();
	    try {
			 check_file_result = weather_data_structure_checker.check_SWD_files();
		} catch (NoSuchFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		if (!check_file_result){
				System.out.println("The files either has no INDEX.SWD or no SDXXXXXX.SWD file.");
			}
		else{
				//go to STEP 2
				System.out.println("The files are all set.");
			}
		} 
		
*/
////////////////STEP 2: Weather_Data into an Arraylist of objects of weather_data
	
	
			try {
			
				File file = new File("GxE_Weather_Data_Format_Transfer/res/SD201406.SWD");
				Read_SD_SWD_Files reader= new Read_SD_SWD_Files(file);
				reader.Read_SD_SWD_Files_Run(file);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//////////////////STEP 3: put the Arraylist of weather_data into a table of gxe_weather database

//		try{
//		
//			
//			
//			
//	
//	
//
//	}catch(IOException e){
//		e.printStackTrace();
//	}
/////////////////STEP 4: output the table info, total of weather stations with name and id, total years of data
//		try{
//			
//			
//			
//			
//			
//			
//
//		}catch(IOException e){
//			e.printStackTrace();
//		}
}
}

