package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import Weather_Data_Transfer.Read_SD_SWD_Files;

public class Read_SD_SWD_Files_Test_Set_Get extends Read_SD_SWD_Files_Test {

	@Test
	public void test() {
		File file = new File("/Users/yiweisun/Documents/workspace/GxE_Weather_Data_Format_Transfer/res/SD201311.SWD");
		
		Read_SD_SWD_Files tester= new Read_SD_SWD_Files(file);
		tester.set_rh_pos(7);
		assertEquals(7,tester.get_rh_pos());
		tester.set_rh_pos(0);
		
		tester.Read_SD_Date_SWD_File_For_Abbr_Position(file);
		assertEquals(1,tester.get_tempa_f_pos());
		assertEquals(7,tester.get_rh_pos());
	}

}
