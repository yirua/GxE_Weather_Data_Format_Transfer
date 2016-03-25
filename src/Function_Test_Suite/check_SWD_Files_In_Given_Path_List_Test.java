package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import org.junit.Test;

import Weather_Data_Transfer.Read_SD_SWD_Files;
import Weather_Data_Transfer.Weather_Data_Into_DB;
import Weather_Data_Transfer.Weather_Data_Structure_Checker;

public class check_SWD_Files_In_Given_Path_List_Test {
	Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	Read_SD_SWD_Files reader;
	Weather_Data_Into_DB db_tester = new Weather_Data_Into_DB();
	@Test
	public void test() {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//step 1: get the file names from the list
		try {
			checker.check_SWD_Files_In_Given_Path_List();//files_SD_DATE file list
			
			//feed the program and into the db
			
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<File> files;
		//checker.check_SWD_Files should return true, else user should check the file folder
		
			
			//checker.Get_SD_DATES_Files();
			//checker.get_Files_SD_DATE();
			files =checker.get_Files_SD_DATE();
			assertEquals(189,files.size());
			
//			db_tester.Create_Table_Gxe_Weather(Weather_Data_Into_DB.getConnection_Remote()); //if it is first run
//			db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Remote());   //if like to drop then create the table
			//truncate the table to delete all records
				db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Remote());
			
			//drop then create to make the serial number starting with 1
//				db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Remote());
			int file_ctr=0;
			BufferedWriter out = null;	
			
			try{
				FileWriter fstream = new FileWriter("res/Weather_Data_PipeLine_Report.txt", false); //true tells to append data.
			    out = new BufferedWriter(fstream);
			    
			    
			    out.write("Weather_Data_PipeLine_Report"+"\n\n");	
					for(File file: files){
							reader =new Read_SD_SWD_Files(file); //constructor 
							reader.Read_SD_Date_SWD_File_For_Abbr_Position(file); //get the positions of certain items
							reader.Read_SD_SWD_Files_Run(file); 
							int file_records_length=0;
							file_records_length=db_tester.insert_one_object_into_db(Weather_Data_Into_DB.getConnection_Remote(), reader.get_Weather_Info_List());
							//file_records_length=db_tester.Insert_Into_DB_By_Set(Weather_Data_Into_DB.getConnection(), reader.get_Weather_Info_Set());
			
							System.out.println(file.getName()+" File has records with the number: "+file_records_length);
							
							file_ctr++;
							
							reader.Set_Positions_To_Zero();//make the reader all positions back to zero for new reading..
							
				
				    
				    
							out.write(file.getName()+" File has records: "+file_records_length+"\n");
				   
				
					}
					out.write("\n");
					
					out.write("There are total of "+files.size()+" SWD files are read and dumped into DB GXE_Weather.\n");
					
			}
			catch (IOException e)
			{
			    System.err.println("Error: " + e.getMessage());
			}
			finally
			{
			    if(out != null) {
			        try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}
		
			System.out.println("We have put into the gxe_weather db in the total of : "+file_ctr+" files.");
	
	}

}
