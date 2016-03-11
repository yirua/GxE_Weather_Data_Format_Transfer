package Function_Test_Suite;

import static org.junit.Assert.*;

import java.nio.file.NoSuchFileException;

import org.junit.Test;

import Weather_Data_Transfer.Weather_Data_Structure_Checker;

public class check_SWD_Files_In_Given_Path_List_Test {
	Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	@Test
	public void test() {
		try {
			checker.check_SWD_Files_In_Given_Path_List();
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
