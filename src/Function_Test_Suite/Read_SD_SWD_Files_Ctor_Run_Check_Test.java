/**
 * 
 */
package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.junit.Test;

import Weather_Data_Transfer.Read_SD_SWD_Files;
import Weather_Data_Transfer.Weather_Data_Structure_Checker;
import Weather_Data_Transfer.Weather_Info;

/**
 * @author yiweisun
 *
 */
public class Read_SD_SWD_Files_Ctor_Run_Check_Test {
	
	Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	Read_SD_SWD_Files reader;
	@Test
	public void test() {
		
		//JFileChooser chooser= new JFileChooser();
		
		
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
				//test_Weather_info_list();
				

				int size=reader.get_Weather_Info_List().size();
				//out put the last element of the list
				for(int index=0; index<size;index++){
					if(index==size-1){
						Weather_Info info= reader.get_Weather_Info_List().get(index);
						System.out.println(index+" element in the file:");
						System.out.print("Station_id:"+ info.getStationId()+"\tMonth:"+info.getMonth()+"\tDay:"+info.getDay()+"\tYear:"+info.getYear()+"\tTime:"+info.getTime());
						System.out.print("Dew Point F:"+info.getDew_point_F()+"\tSolar Rad:"+info.getSolar_Rad()+"\tRainfall:"+info.getRainfall_in());
						System.out.print("TMP_in_C:"+ info.getTMP_C()+"\tTMPA_in_C:"+info.getTMPA_C()+"\tTMPB_in_C:"+info.getTMPB_C()+"\tTMPC_in_C:"+info.getTMPC_C()+"\tTMPD_in_C:"+info.getTMPD_C()+"\tTMPE_in_C:"+info.getTMPE_C()+"\tTMPF_in_C:"+info.getTMPF_C());
						System.out.println();
						System.out.print("TMP_in_F:"+ info.getTMP()+"\tTMPA_in_F:"+info.getTMPA()+"\tTMPB_in_F:"+info.getTMPB()+"\tTMPC_in_F:"+info.getTMPC()+"\tTMPD_in_F:"+info.getTMPD()+"\tTMPE_in_F:"+info.getTMPE()+"\tTMPF_in_F:"+info.getTMPF());
						System.out.println();
						//ECBC +VWCs
						
						System.out.print("ECBC:"+info.getECBC()+"\tVWCA:"+info.getVWCA()+"\tVWCB:"+info.getVWCB()+"\tVWCC:"+info.getVWCC()+"\tVWCD:"+info.getVWCD());
					    //WIND 
						System.out.print("Wind Dir:"+info.getWind_Dir()+"\tWind Gust:"+info.getWind_Gust_mph()+"\tWind Speed:"+info.getWind_Speed_mph());
						//humidity
						System.out.print("Humidity:"+ info.getRH());
						System.out.println();
				
					}
				
				}
				reader.Set_Positions_To_Zero();
/////////////////////////////////////////				
				file_ctr++;
				//test only one file
				//break;
			}
			//point to Delaware Station 9078
			 assertEquals(7,files.size());
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
