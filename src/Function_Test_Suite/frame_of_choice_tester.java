package Function_Test_Suite;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Weather_Data_Transfer.Frame_of_choice;
import Weather_Data_Transfer.Weather_Data_Into_DB;

public class frame_of_choice_tester {
	static Weather_Data_Into_DB db_tester = new Weather_Data_Into_DB();
	String table_name= "gxe_weather_test";
	String name_string="gxe_weather";
	ArrayList<String> table_names= new ArrayList<String>();
	@SuppressWarnings("resource")
	@Test
	public void test() {
		//db_tester.Create_Table_Gxe_Weather(Weather_Data_Into_DB.getConnection_Local(),table_name);
		table_names= db_tester.get_all_table_names(Weather_Data_Into_DB.getConnection_Local(),name_string);
		Frame_of_choice Frame_of_choice_tester= new Frame_of_choice(db_tester,table_names);
		Frame_of_choice_tester.set_contents();
		 System.out.println("Press Any Key To Continue...");
         new java.util.Scanner(System.in).nextLine();
	}

}
