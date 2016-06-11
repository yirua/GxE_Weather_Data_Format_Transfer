package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import org.junit.Test;

import Weather_Data_Transfer.Frame_of_choice;
import Weather_Data_Transfer.Read_SD_SWD_Files;
import Weather_Data_Transfer.Weather_Data_Into_DB;
import Weather_Data_Transfer.Weather_Data_Structure_Checker;

public class gxe_weather_tester {
	static Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	static Read_SD_SWD_Files reader;
	static Weather_Data_Into_DB db_tester = new Weather_Data_Into_DB();
	static String name_string="gxe_weather";
	static ArrayList<String> table_names= new ArrayList<String>();
	@Test
	public void test() {
		//continue to run the program 
		try{
				
				long startTime = System.currentTimeMillis();
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
				
		//step 2: let the DB ready for taking data..	
				//checker.Get_SD_DATES_Files();
				//checker.get_Files_SD_DATE();
				files =checker.get_Files_SD_DATE();
				assertEquals(7,files.size());
				//String table_name="gxe_weather";
				//start to call the Frame_of_choice class to do action selections
				table_names= db_tester.get_all_table_names(Weather_Data_Into_DB.getConnection_Local(),name_string);
				Frame_of_choice Frame_of_choice_tester= new Frame_of_choice(db_tester,table_names);
				Frame_of_choice_tester.set_contents();
				 System.out.println("Press Any Key To Continue...");
		         new java.util.Scanner(System.in).nextLine();
				
				
				String table_name=Frame_of_choice_tester.get_table_name();
				
				
				//db_tester.Create_Table_Gxe_Weather(Weather_Data_Into_DB.getConnection_Remote(),table_name);
			
				//truncate the table to delete all records
				//db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Remote(),table_name);
				
				//drop then create to make the serial number starting with 1
				//db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Remote(),table_name);
				//db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Local(),table_name);
				
				//if it is not delete, then do the record insert action, else, close the frame to get out
				if (!Frame_of_choice_tester.isDelete_flag()){
				int file_ctr=0;
				int total_records=0;
				BufferedWriter out = null;	
		//step 3: put data into DB and write out the Pipeline Report.		
				try{
					FileWriter fstream = new FileWriter("Output/Weather_Data_PipeLine_Report.txt", false); //true tells to append data.
				//	FileWriter fstream = new FileWriter(System.getProperty("user.home") + "/.Desktop/Weather_Data_PipeLine_Report.txt", false); //true tells to append data.for .jar file
					
					
				
					out = new BufferedWriter((fstream));
				    
				    
				    out.write("Weather_Data_PipeLine_Report"+"\n\n");	
						for(File file: files){
								reader =new Read_SD_SWD_Files(file); //constructor 
								reader.Read_SD_Date_SWD_File_For_Abbr_Position(file); //get the positions of certain items
								reader.Read_SD_SWD_Files_Run(file); 
								int file_records_length=0;
							//	file_records_length=db_tester.insert_one_object_into_table(Weather_Data_Into_DB.getConnection_Remote(), reader.get_Weather_Info_List(),Frame_of_choice_tester.get_new_table_name());
								file_records_length=db_tester.insert_one_object_into_table(Weather_Data_Into_DB.getConnection_Local(), reader.get_Weather_Info_List(),table_name);
								//file_records_length=db_tester.Insert_Into_DB_By_Set(Weather_Data_Into_DB.getConnection(), reader.get_Weather_Info_Set());
				
								System.out.println(file.getName()+" File has records with the number: "+file_records_length);
								
								file_ctr++;
								
								reader.Set_Positions_To_Zero();//make the reader all positions back to zero for new reading..				
					
					    
					    
								out.write(file.getName()+" File has records: "+file_records_length+"\n");
								total_records+=file_records_length;
					
						}
						out.write("\n");
						
						out.write("There are total of "+files.size()+" SWD files contained "+ total_records+ " records in total are read and dumped into DB GXE_Weather.\n");
						
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
				
				System.out.println("We have put into the gxe_weather db and table: "+Frame_of_choice_tester.get_table_name()+" in the total of : "+file_ctr+" files.");
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("The total running time is:"+totalTime+" milleseconds");
				Frame_of_choice_tester.close_frame();
			}
		
	////////the delete_flag is true
				else {
					System.out.println("We have delete a table in the gxe_weather database: "+Frame_of_choice_tester.get_table_name());
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("The total running time is:"+totalTime+" milleseconds");
					Frame_of_choice_tester.close_frame();
				}
			
		}//try
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
	}
}
