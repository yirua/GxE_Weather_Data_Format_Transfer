/**
 * 
 */
package Function_Test_Suite;

import static org.junit.Assert.*;

import java.nio.file.NoSuchFileException;

import org.junit.Test;

import Weather_Data_Transfer.Weather_Data_Structure_Checker;

/**
 * @author yiwei
 *
 */
public class Weather_Data_Structure_Check_Test {

	@Test
	public void test() {
		
		Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
		try {
			checker.check_SWD_Files();
			checker.Get_SD_DATES_Files();
			checker.get_Files_SD_DATE();
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
