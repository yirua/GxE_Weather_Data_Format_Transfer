/**
 * 
 */
package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;

import org.junit.Test;

import Weather_Data_Transfer.Read_SD_SWD_Files;
import Weather_Data_Transfer.Weather_Data_Structure_Checker;

/**
 * @author yiweisun
 *
 */
public class Read_SD_SWD_Files_Into_CSV_File {
	Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	Read_SD_SWD_Files reader;
	@Test
	public void test() {
		try {
			ArrayList<File> files;
			checker.check_SWD_Files();
			checker.Get_SD_DATES_Files();
			checker.get_Files_SD_DATE();
			files =checker.get_Files_SD_DATE();
			reader = new Read_SD_SWD_Files(files);
			int file_ctr=0;
			for(File file: files){
				reader.Read_SD_Date_SWD_File_For_Abbr_Position(file);
				reader.Read_SD_SWD_Files_Run(file);
				reader.Write_Weather_Info_List_Into_CSV_File(reader.get_Weather_Info_List());
				file_ctr++;
				//test one file
				break;
			}
			assertEquals(1,file_ctr);
		//	assertEquals(file_ctr, files.size());
			System.out.println("total number of files:"+ file_ctr);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
