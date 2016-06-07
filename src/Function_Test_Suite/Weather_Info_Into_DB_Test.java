/**
 * 
 */
package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import org.junit.Test;

import Weather_Data_Transfer.Read_SD_SWD_Files;
import Weather_Data_Transfer.Weather_Data_Structure_Checker;
import Weather_Data_Transfer.Weather_Data_Into_DB;
/**
 * @author yiweisun
 *
 */
public class Weather_Info_Into_DB_Test {
	Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	Read_SD_SWD_Files reader;
	Weather_Data_Into_DB db_tester = new Weather_Data_Into_DB();
	@Test
	public void test() {
		try {
			ArrayList<File> files;
			//checker.check_SWD_Files should return true, else user should check the file folder
			if(checker.check_SWD_Files()){
				String table_name="gxe_weather";
				checker.Get_SD_DATES_Files();
				//checker.get_Files_SD_DATE();
				files =checker.get_Files_SD_DATE();
				assertEquals(7,files.size());
			
				
				//truncate the table to delete all records
				db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Local(),table_name);
				
				//drop then create to make the serial number starting with 1
				db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Local(),table_name);
				
				int file_ctr=0;
				for(File file: files){
					reader =new Read_SD_SWD_Files(file);
					reader.Read_SD_Date_SWD_File_For_Abbr_Position(file);
					reader.Read_SD_SWD_Files_Run(file);
					int file_records_length=0;
					file_records_length=db_tester.insert_one_object_into_table(Weather_Data_Into_DB.getConnection_Local(), reader.get_Weather_Info_List(),table_name);
					//file_records_length=db_tester.Insert_Into_DB_By_Set(Weather_Data_Into_DB.getConnection(), reader.get_Weather_Info_Set());

					System.out.println(file.getName()+" File has records with the number: "+file_records_length);
					
					file_ctr++;
					
					reader.Set_Positions_To_Zero();
					
					//only test one file
//					if(file_ctr==1){
//						break;
//					}
				}
				System.out.println("We have put into the gxe_weather db in the total of : "+file_ctr+" files.");
				
			}
			else{
				System.out.println("Please check the folder, it is either missing INDEX.SWD file or is missing SDyearmm.SWD files... ");
			}
			
		}
		 catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
