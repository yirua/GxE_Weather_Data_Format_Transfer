package Weather_Data_Transfer;
import Weather_Data_Transfer.Weather_Info;
import components.DialogDemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.StringUtils;

import Weather_Data_Transfer.Weather_Data_Structure_Checker;



//Read the files in the files_SD_DATE from weather_data_structure_checker, one by one.
//The first element might have station_id, check that, if not, might ask user to input station_id 
//The fifth line contains abbreviation, which also gives out the position of certain elements, I will
//put every abbreviation the position int to a variable, the one default can be zero, so if it is zero,
//then the string of that can be 0...	
//The seventh line contains Date and time stamp which need to spread out, then 

public class Read_SD_SWD_Files {
	
	ArrayList<File> sd_swd_files;//local ArrayList for the sd_swd_files;
	ArrayList<String> weather_data_abbre;
	ArrayList<Weather_Info> weather_info_list;
	
	//No duplicate one in the set
	Set<Weather_Info> weather_info_set;
	
	int line_width;
	private String station_id; //default station_id is empty string
	File file;
	         
	int temp_c_pos;       
	int tempa_c_pos;          
	int tempb_c_pos;          
	int tempc_c_pos;         
	int tempd_c_pos;          
	int tempe_c_pos;          
	int tempf_c_pos;
	
	int temp_f_pos;       
	int tempa_f_pos;          
	int tempb_f_pos;          
	int tempc_f_pos;         
	int tempd_f_pos;          
	int tempe_f_pos;          
	int tempf_f_pos;  
	int rh_pos;              
	int dew_point_pos;       
	int solar_radiation_pos;  
	int rain_fall_pos;       
	int wind_direction_pos;  
	int wind_speed_pos;      
	int wind_gust_pos;
	private int ecbc_pos;
	private int vwca_pos;
	private int vwcb_pos;
	private int vwcc_pos;
	private int vwcd_pos; 
	/*
	 * 
	 * ctor for list of Files
	 */
	public Read_SD_SWD_Files(ArrayList<File> sd_swd_files){
		this.sd_swd_files= sd_swd_files;
		weather_data_abbre= new ArrayList<String>();
		weather_info_list = new ArrayList<Weather_Info>();
		weather_info_set= new HashSet<Weather_Info>();
		Set_Positions_To_Zero();
		station_id="";
		
	}
	//constructor for testing one file
	public Read_SD_SWD_Files(File file){
		this.file=file;
		weather_data_abbre= new ArrayList<String>();
		weather_info_list = new ArrayList<Weather_Info>();
		weather_info_set= new HashSet<Weather_Info>();
		//default the positions are all zero.. 
		Set_Positions_To_Zero();
		station_id="";
	}

	/*
	 * This will return the ArrayList<Weather_Info>
	 */
	public ArrayList<Weather_Info> get_Weather_Info_List(){
		return weather_info_list;
	}
	/*
	 * This will return the Set<Weather_Info>...
	 */
	public Set<Weather_Info> get_Weather_Info_Set(){
		return weather_info_set;
	}
	/*
	 * This method will get the position for each items with certain names + abbreviations
	 */
	public void Read_SD_Date_SWD_File_For_Abbr_Position(File file){
		// read the index file into a map<String, String>, to get the certain positions,decide which columns are there..
	       // String fileName = file.getName();
			String fileName;
			// positions for the SWD columns default 
			//int station_id;     
			ArrayList<String> one_line= new ArrayList<String>();
			ArrayList<ArrayList<String>> two_dimention_table = new ArrayList<ArrayList<String>>();
			
			try {
						fileName = file.getCanonicalPath();
					
			        // This will reference one line at a time
			        String line = null;

			        try {
			            // FileReader reads text files in the default encoding.
			            FileReader fileReader = 
			                new FileReader(fileName);

			            // Always wrap FileReader in BufferedReader.
			            BufferedReader bufferedReader = 
			                new BufferedReader(fileReader);
			            int line_ctr=0;
			            while((line = bufferedReader.readLine()) != null&line_ctr<=2) {
			              //  System.out.println(line); // show up the line input
			                
			                String[] words= line.split("\t");
			                //read the first line
			               for(String word: words){
			            	   		
			            		   one_line.add(word);
			            	  
			            	   
			               }
			               //test the results into the ArrayList
	/*		               System.out.println("one_line output in Read_SD_Date_SWD_File_For_Abbr_Position");
			               for(int i=0;i<one_line.size();i++){
			            	   System.out.println(one_line.get(i)); 
			               } 
	*/
			               //read into the abbreviations to get certain positions of them
			                two_dimention_table.add(one_line);
			                line_width=one_line.size();
			                //make sure that the ArrayList is not empty..
			                if(!one_line.isEmpty()){
			                	one_line=new ArrayList<String>();
			                } //clean the ArrayList for next line...
			                else{
			                	System.out.println("The line is empty!");
			                }
			                line_ctr++;
			            }   

			            // Always close files.
			            bufferedReader.close();         
			        }
			        catch(FileNotFoundException ex) {
			            System.out.println(
			                "Unable to open file '" + 
			                fileName + "'");                
			        }
			        catch(IOException ex) {
			            System.out.println(
			                "Error reading file '" 
			                + fileName + "'");                  
			            // Or we could just do this: 
			            // ex.printStackTrace();
			        }
					//station_id, 
			} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}
		
		//after reading the file, decide the position of each item...traverse the 2d ArrayList
/*			System.out.println("Output the two_dimention_table");
			for(int i=0;i<two_dimention_table.size();i++){	
				for(int index=0;index<two_dimention_table.get(i).size();index++){
					//System.out.println("Output the two_dimention_table");
					System.out.println(two_dimention_table.get(0).get(index));
					
				}
		}	
*/		
			for(int index=1;index<two_dimention_table.get(0).size();index++){
				
				////////////TMP IN F
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					set_temp_f_pos(index);
				}
				//TMPA IN F
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPA")){
					set_tempa_f_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPB")){
					set_tempb_f_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPC")){
					set_tempc_f_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPD")){
					set_tempd_f_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPE")){
					set_tempe_f_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*F")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPF")){
					set_tempf_f_pos(index);
				}
				
				
			////////TMP IN C
				
				if(two_dimention_table.get(1).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					set_temp_c_pos(index);
				}
				//TMPA IN F
				if(two_dimention_table.get(0).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPA")){
					set_tempa_c_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPB")){
					set_tempb_c_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPC")){
					set_tempc_c_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPD")){
					set_tempd_c_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPE")){
					set_tempe_c_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("*C")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMPF")){
					set_tempf_c_pos(index);
				}
				
			//VWCA-D Soil Moist
				if(two_dimention_table.get(0).get(index).contains("Soil Moist-VWC")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWCA")){
					set_vwca_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("Soil Moist-VWC")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWCB")){
					set_vwcb_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("Soil Moist-VWC")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWCC")){
					set_vwcc_pos(index);
				}
				if(two_dimention_table.get(0).get(index).contains("Soil Moist-VWC")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWCD")){
					set_vwcd_pos(index);
				}
				
			//Solar Rad
				if(two_dimention_table.get(0).get(index).contains("Solar Rad")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SRD")){
					set_solar_radiation_pos(index);
				}
			//EC-SMEC	
				if(two_dimention_table.get(0).get(index).contains("EC-SMEC300")&two_dimention_table.get(2).get(index).equalsIgnoreCase("ECBC")){
					set_ecbc_pos(index);
				}
				////////
			//RH
				if(two_dimention_table.get(0).get(index).contains("RH (%)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("HMD")){
					set_rh_pos(index);
				}
			//	Rainfall
				if(two_dimention_table.get(0).get(index).contains("Rainfall")&two_dimention_table.get(2).get(index).equalsIgnoreCase("RNF")){
					set_rain_fall_pos(index);
				}
			//Wind dir
				if(two_dimention_table.get(0).get(index).contains("Wind Dir")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WND")){
					set_wind_direction_pos(index);
				}
			//Wind Gust
				if(two_dimention_table.get(0).get(index).contains("Wind Gust")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WNG")){
					set_wind_gust_pos(index);
				}
			//Wind Speed	
				if(two_dimention_table.get(0).get(index).contains("Wind Speed")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WNS")){
					set_wind_speed_pos(index);
				}
			//Dew Point
				if(two_dimention_table.get(0).get(index).contains("Dew Point")&two_dimention_table.get(2).get(index).equalsIgnoreCase("DEW")){
					set_dew_point_pos(index);
				}
			}
			
			//out put the postion we got 
			

				
				////////////TMP IN F
				System.out.println("TMP IN F " + get_temp_f_pos());
				System.out.println("TMPA IN F " + get_tempa_f_pos());	
				System.out.println("TMPB IN F " + get_tempb_f_pos());	
				System.out.println("TMPC IN F " + get_tempc_f_pos());	
				System.out.println("TMPD IN F " + get_tempd_f_pos());	
				System.out.println("TMPE IN F " + get_tempe_f_pos());
				System.out.println("TMPF IN F " + get_tempf_f_pos());	
				//////////////TMP IN C
				System.out.println("TMP IN C " + get_temp_c_pos());
				System.out.println("TMPA IN C " + get_tempa_c_pos());	
				System.out.println("TMPB IN C " + get_tempb_c_pos());	
				System.out.println("TMPC IN C " + get_tempc_c_pos());	
				System.out.println("TMPD IN C " + get_tempd_c_pos());	
				System.out.println("TMPE IN C " + get_tempe_c_pos());
				System.out.println("TMPF IN C " + get_tempf_c_pos());	
				
				//VWCA-D Soil Moist
				System.out.println("Soil Moist-VWC A " + get_vwca_pos());
				System.out.println("Soil Moist-VWC B " + get_vwcb_pos());	
				System.out.println("Soil Moist-VWC C " + get_vwcc_pos());	
				System.out.println("Soil Moist-VWC D " + get_vwcd_pos());	
				////////////Solar Rad
				System.out.println("Solar Rad " + get_solar_radiation_pos());
				
				//EC-SMEC	
				System.out.println("EC-SMEC300 " + get_ecbc_pos());	
				
				//RH
				System.out.println("RH (%) " + get_rh_pos());	
//				Rainfall
				System.out.println("Rainfall " + get_rain_fall_pos());	
				
				//Wind dir
				System.out.println("Wind Dir " + get_wind_direction_pos());	
				//Wind Gust
				System.out.println("Wind Gust " + get_wind_gust_pos());	
				//Wind Speed	
				System.out.println("Wind Speed " + get_wind_speed_pos());	
				//Dew Point
				System.out.println("Dew Point " + get_dew_point_pos());	
			

			
				
			
			
			
			
			
	}
	// set all the positions back to zero
	public void Set_Positions_To_Zero(){
		temp_c_pos=0;       
		tempa_c_pos=0;          
		tempb_c_pos=0;          
		tempc_c_pos=0;         
		tempd_c_pos=0;          
		tempe_c_pos=0;          
		tempf_c_pos=0;
		
		temp_f_pos=0;       
		tempa_f_pos=0;          
		tempb_f_pos=0;          
		tempc_f_pos=0;         
		tempd_f_pos=0;          
		tempe_f_pos=0;          
		tempf_f_pos=0;  
		//ec_smec300 
		ecbc_pos=0;
		//soil_moist a to d
		vwca_pos=0;
		vwcb_pos=0;
		vwcc_pos=0;
		vwcd_pos=0;
		
		rh_pos=0;              
		dew_point_pos=0;       
		solar_radiation_pos=0;  
		 rain_fall_pos=0;   
		 //wind 3 items
		 wind_direction_pos=0;  
		 wind_speed_pos=0;      
		 wind_gust_pos=0; 
	}
	
	
	public void Read_SD_SWD_Files_Run(File file){
		//read the files one by one
		
///////////////// for loop 		
//			for (File file: sd_swd_files){
				// The name of the file to open.
		///////////////////////////// for testing one file
		       // String fileName = file.getName();
		String fileName;
		// positions for the SWD columns default 
		//int station_id;     
		          
		
		try {
					fileName = file.getCanonicalPath();
				
		        // This will reference one line at a time
		        String line = null;

		        try {
		            // FileReader reads text files in the default encoding.
		            FileReader fileReader = 
		                new FileReader(fileName);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);
		            int line_ctr=0;
		            System.out.println("Read_SD_SWD_Files_Run ");
		            while((line = bufferedReader.readLine()) != null) {
		            	
		                System.out.println(line); // show up the line input
		                String temp_station_id;
		                String[] words= line.split("\t");
		                //read the first line of the first file
		                if (line_ctr==0){
		                	if(get_Station_Id().equals("")){
		                		temp_station_id=words[0];
		                	int len=temp_station_id.length();
		                	
		                	if(len>=5){
		                		temp_station_id=temp_station_id.substring(len-5, len);
		                		//is it an integer?
		                	//	int test_station_id=Integer.parseInt(station_id);
		                		
		                		if(isNumeric(temp_station_id)){
		                			//assume that it will be o.k
		                			set_Station_Id(temp_station_id);
		                		}
		                		else
		                		{
		                			//need to input the station_id by user 
		                			
		                		      set_Station_Id(User_Input_Station_Id());
		                			
		                		}
		                	}
		                	else{
		                		//need to input the station_id by user...
		                		
		                		      set_Station_Id(User_Input_Station_Id());
		                			
		                	}
		                	
		                	}
		                	
		                }
		                //read into the abbreviations to get certain positions of them
		               
		                //read the data information 
		                if(line_ctr>=3){
		                	//IF the first one is empty, ignore it
		                	if (words[0].equalsIgnoreCase("")|words[0]==null){
		                		continue;
		                	}
		                	//else read in the line, 
		                	else{
		                		//
		                		//words[0] is not empty, it will contain year-mm-dd plus time
		                		//add zeros at the rest of words
		                		String[] new_words=add_zero_to_words(words);
		                		//check the new_words String
/*		                		System.out.println("The new words String[]: ");
		                		for(int i=0; i<new_words.length;i++){
		                			System.out.println(i+"\t"+new_words[i]+"\t");
		                		}
*/
		                		put_Weather_Data_Into_List(new_words);
		                		//given the certain words pos by Read_SD_Date_SWD_File_For_Abbr_Position() if they are not zero, otherwise send the string to "0"
		                		
		                		//create an object of Weather_Info and put that into an ArrayList of it..
		                	}
		                }
		                line_ctr++;
		            }   

		            // Always close files.
		            bufferedReader.close();         
		        }
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                fileName + "'");                
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + fileName + "'");                  
		            // Or we could just do this: 
		            // ex.printStackTrace();
		        }
				//station_id, 
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
//			}//for loop the the File treatment      for loop---
		
	}
	//////////////////////
	/*
	 * This will put the one line non empty String[] into an ArrayList
	 */
	private void put_Weather_Data_Into_List(String[] words) {
		// TODO Auto-generated method stub
	 try{	
		 
		 if(!words.toString().isEmpty()){
			 
		 
			String time_info= words[0];
			String[] year_mm_dd_time=time_info.split(" ");
			String time = year_mm_dd_time[1];
			String[] year_mm_dd=year_mm_dd_time[0].split("-");
			Weather_Info weather_info_one= new Weather_Info();
			//////////////////////////////////checking the weather_info_one
/*					System.out.println("The weather_info_one contents before reading: ");
			System.out.print("Station_id:"+ weather_info_one.getStationId()+"\tMonth:"+weather_info_one.getMonth()+"\tDay:"+weather_info_one.getDay()+"\tYear:"+weather_info_one.getYear()+"\tTime:"+weather_info_one.getTime());
			System.out.print("Dew Point:"+weather_info_one.getDew_Point()+"\tSolar Rad:"+weather_info_one.getSolar_Rad()+"\tRainfall:"+weather_info_one.getRainfall());
			System.out.print("TMP_in_C:"+ weather_info_one.getTMP_C()+"\tTMPA_in_C:"+weather_info_one.getTMPA_C()+"\tTMPB_in_C:"+weather_info_one.getTMPB_C()+"\tTMPC_in_C:"+weather_info_one.getTMPC_C()+"\tTMPD_in_C:"+weather_info_one.getTMPD_C()+"\tTMPE_in_C:"+weather_info_one.getTMPE_C()+"\tTMPF_in_C:"+weather_info_one.getTMPF_C());
			System.out.println();
			System.out.print("TMP_in_F:"+ weather_info_one.getTMP()+"\tTMPA_in_F:"+weather_info_one.getTMPA()+"\tTMPB_in_F:"+weather_info_one.getTMPB()+"\tTMPC_in_F:"+weather_info_one.getTMPC()+"\tTMPD_in_F:"+weather_info_one.getTMPD()+"\tTMPE_in_F:"+weather_info_one.getTMPE()+"\tTMPF_in_F:"+weather_info_one.getTMPF());
			System.out.println();
			//ECBC +VWCs
			
			System.out.print("ECBC:"+weather_info_one.getECBC()+"\tVWCA:"+weather_info_one.getVWCA()+"\tVWCB:"+weather_info_one.getVWCB()+"\tVWCC:"+weather_info_one.getVWCC()+"\tVWCD:"+weather_info_one.getVWCD());
		    //WIND 
			System.out.print("Wind Dir:"+weather_info_one.getWind_Dir()+"\tWind Gust:"+weather_info_one.getWind_Gust()+"\tWind Speed:"+weather_info_one.getWind_Speed());
			//humidity
			System.out.print("Humidity:"+ weather_info_one.getRH());
			System.out.println();
/*//////////////////////////////////////////////////////////////////////////////////////////////////
			weather_info_one.setStationId(get_Station_Id());
			//set the year, mm, dd and time
			
			weather_info_one.setYear(year_mm_dd[0]);
			weather_info_one.setMonth(year_mm_dd[1]);
			weather_info_one.setDay(year_mm_dd[2]);
			weather_info_one.setTime(time);
			
			
			
			//TMP IN C
			if(get_temp_c_pos()==0){
				weather_info_one.setTMP_C("0");
			}
			else {
				weather_info_one.setTMP_C(words[get_temp_c_pos()]);
				System.out.println("The weather_info_one is setting TMP_C as: "+ weather_info_one.getTMP_C());
			}
			//TMPA IN C
			if(get_tempa_c_pos()==0){
				weather_info_one.setTMPA_C("0");
			}
			else {
				weather_info_one.setTMPA_C(words[get_tempa_c_pos()]);
			}
			//////////////////////////////
			if(get_tempb_c_pos()==0){
				weather_info_one.setTMPB_C("0");
			}
			else {
				weather_info_one.setTMPB_C(words[get_tempb_c_pos()]);
			}
			//////////////////////////////
			if(get_tempc_c_pos()==0){
				weather_info_one.setTMPC_C("0");
			}
			else {
				weather_info_one.setTMPC_C(words[get_tempc_c_pos()]);
			}
			if(get_tempd_c_pos()==0){
				weather_info_one.setTMPD_C("0");
			}
			else {
				weather_info_one.setTMPD_C(words[get_tempd_c_pos()]);
			}
			if(get_tempe_c_pos()==0){
				weather_info_one.setTMPE_C("0");
			}
			else {
				weather_info_one.setTMPE_C(words[get_tempe_c_pos()]);
			}
			if(get_tempf_c_pos()==0){
				weather_info_one.setTMPF_C("0");
			}
			else {
				weather_info_one.setTMPF_C(words[get_tempf_c_pos()]);
			}
			//TMP IN F
			
			if(get_temp_f_pos()==0){
				weather_info_one.setTMP("0");
			}
			else {
				int temp=get_temp_f_pos();
				weather_info_one.setTMP(words[temp]);

			}
			//TMPA IN F
			if(get_tempa_f_pos()==0){
				weather_info_one.setTMPA("0");
			}
			else {
				weather_info_one.setTMPA(words[get_tempa_f_pos()]);
			}
			//TMPB IN F
			if(get_tempb_f_pos()==0){
				weather_info_one.setTMPB("0");
			}
			else {
				weather_info_one.setTMPB(words[get_tempb_f_pos()]);
			}
			//TMPC IN F
			if(get_tempc_f_pos()==0){
				weather_info_one.setTMPC("0");
			}
			else {
				weather_info_one.setTMPC(words[get_tempc_f_pos()]);
			}
			//TMPD IN F
			if(get_tempd_f_pos()==0){
				weather_info_one.setTMPD("0");
			}
			else {
				weather_info_one.setTMPD(words[get_tempd_f_pos()]);
			}
			//TMPE IN F
			if(get_tempe_f_pos()==0){
				weather_info_one.setTMPE("0");
			}
			else {
				weather_info_one.setTMPE(words[get_tempe_f_pos()]);
			}
			//TMPF IN F
			if(get_tempf_f_pos()==0){
				weather_info_one.setTMPF("0");
			}
			else {
				weather_info_one.setTMPF(words[get_tempf_f_pos()]);
			}
			//RH
			if(get_rh_pos()==0){
				weather_info_one.setRH("0");
			}
			else {
				weather_info_one.setRH(words[get_rh_pos()]);
			}
			//DEW
			if(get_dew_point_pos()==0){
				weather_info_one.setDew_Point("0");
			}
			else {
				weather_info_one.setDew_Point(words[get_dew_point_pos()]);
			}
			//SOLAR RADIATION
			if(get_solar_radiation_pos()==0){
				weather_info_one.setSolar_Rad("0");
			}
			else {
				weather_info_one.setSolar_Rad(words[get_solar_radiation_pos()]);
			}
			//RAIN FALL
			if(get_rain_fall_pos()==0){
				weather_info_one.setRainfall("0");
			}
			else {
				weather_info_one.setRainfall(words[get_rain_fall_pos()]);
			}
			//WIND DIR
			if(get_wind_direction_pos()==0){
				weather_info_one.setWind_Dir("0");
			}
			else {
				weather_info_one.setWind_Dir(words[get_wind_direction_pos()]);
			}
			//WIND GUST
			if(get_wind_gust_pos()==0){
				weather_info_one.setWind_Gust("0");
			}
			else {
				weather_info_one.setWind_Gust(words[get_wind_gust_pos()]);
			}
			//WIND SPEED
			if(get_wind_speed_pos()==0){
				weather_info_one.setWind_Speed("0");
			}
			else {
				weather_info_one.setWind_Speed(words[get_wind_speed_pos()]);
			}
			//ECBC
			if(get_ecbc_pos()==0){
				weather_info_one.setECBC("0");
			}
			else {
				weather_info_one.setECBC(words[get_ecbc_pos()]);
			}
			//VWCA
			if(get_vwca_pos()==0){
				weather_info_one.setVWCA("0");
			}
			else {
				weather_info_one.setVWCA(words[get_vwca_pos()]);
			}
			//VWCB
			if(get_vwcb_pos()==0){
						weather_info_one.setVWCB("0");
			}
			else {
						weather_info_one.setVWCB(words[get_vwcb_pos()]);
			}
					//VWCC
			if(get_vwcc_pos()==0){
						weather_info_one.setVWCC("0");
			}
			else {
						weather_info_one.setVWCC(words[get_vwcc_pos()]);
			}
					//VWCD
			if(get_vwcd_pos()==0){
						weather_info_one.setVWCD("0");
			}
			else {
						weather_info_one.setVWCD(words[get_vwcd_pos()]);
			}
			if(!weather_info_list.contains(weather_info_one)){
				weather_info_list.add(weather_info_one);
			}
			//to avoid the redundancy
			if(!weather_info_set.contains(weather_info_one)){
				weather_info_set.add(weather_info_one);
			}
			//output the weather_info_one contents
			System.out.println("The weather_info_one contents after reading: ");
			System.out.print("Station_id:"+ weather_info_one.getStationId()+"\tMonth:"+weather_info_one.getMonth()+"\tDay:"+weather_info_one.getDay()+"\tYear:"+weather_info_one.getYear()+"\tTime:"+weather_info_one.getTime());
			System.out.print("Dew Point:"+weather_info_one.getDew_Point()+"\tSolar Rad:"+weather_info_one.getSolar_Rad()+"\tRainfall:"+weather_info_one.getRainfall());
			System.out.print("TMP_in_C:"+ weather_info_one.getTMP_C()+"\tTMPA_in_C:"+weather_info_one.getTMPA_C()+"\tTMPB_in_C:"+weather_info_one.getTMPB_C()+"\tTMPC_in_C:"+weather_info_one.getTMPC_C()+"\tTMPD_in_C:"+weather_info_one.getTMPD_C()+"\tTMPE_in_C:"+weather_info_one.getTMPE_C()+"\tTMPF_in_C:"+weather_info_one.getTMPF_C());
			System.out.println();
			System.out.print("TMP_in_F:"+ weather_info_one.getTMP()+"\tTMPA_in_F:"+weather_info_one.getTMPA()+"\tTMPB_in_F:"+weather_info_one.getTMPB()+"\tTMPC_in_F:"+weather_info_one.getTMPC()+"\tTMPD_in_F:"+weather_info_one.getTMPD()+"\tTMPE_in_F:"+weather_info_one.getTMPE()+"\tTMPF_in_F:"+weather_info_one.getTMPF());
			System.out.println();
			//ECBC +VWCs
			
			System.out.print("ECBC:"+weather_info_one.getECBC()+"\tVWCA:"+weather_info_one.getVWCA()+"\tVWCB:"+weather_info_one.getVWCB()+"\tVWCC:"+weather_info_one.getVWCC()+"\tVWCD:"+weather_info_one.getVWCD());
		    //WIND 
			System.out.print("Wind Dir:"+weather_info_one.getWind_Dir()+"\tWind Gust:"+weather_info_one.getWind_Gust()+"\tWind Speed:"+weather_info_one.getWind_Speed());
			//humidity
			System.out.print("Humidity:"+ weather_info_one.getRH());
			System.out.println();
			//////////////////////////////
		}	
		 
	 }
	 catch(Exception e){
		 e.printStackTrace();
	 }		
	}
	/*
	 * the get and set Station_Id methods
	 */
	public String get_Station_Id(){
		return station_id;
	}
	
	public void set_Station_Id(String station_id){
		this.station_id=station_id;
	}
	/*
	 * http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	 */
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	/*
	 * Let the user input the station id 
	 */
	public String User_Input_Station_Id(){
		
	
		//Create and set up the window.
		String s;
		 while(true){
	      Scanner in = new Scanner(System.in);
	 
	      System.out.println("Please Enter valid station id:");
	      s = in.nextLine();
	      System.out.println("You entered station id "+s);
	      s=s.trim();
	      if(isNumeric(s)&s.length()>=4){
	    	  break;
	      }
		 }
	      return s;
	}
	/*
	 * set and get temp_c_pos
	 */
	public void set_temp_c_pos(int pos){
		temp_c_pos=pos;
	} 
	public int get_temp_c_pos(){
		return temp_c_pos;
	}
	//TMPA IN C
	public void set_tempa_c_pos(int pos){
		tempa_c_pos=pos;
	}
	public int get_tempa_c_pos(){
		return tempa_c_pos;
	}
	//TMPB IN C
	public void set_tempb_c_pos(int pos){
		tempb_c_pos=pos;
	}
	public int get_tempb_c_pos(){
		return tempb_c_pos;
	}
	//TMPC IN C
	public void set_tempc_c_pos(int pos){
		tempc_c_pos=pos;
	}
	public int get_tempc_c_pos(){
		return tempc_c_pos;
	}
	//TMPD IN C
	public void set_tempd_c_pos(int pos){
		tempd_c_pos=pos;
	}
	public int get_tempd_c_pos(){
		return tempd_c_pos;
	}
	     
	//TMPE IN C      
	public void set_tempe_c_pos(int pos){
		tempe_c_pos=pos;
	}
	public int get_tempe_c_pos(){
		return tempe_c_pos;
	}
	
	//TMPF IN C
	public void set_tempf_c_pos(int pos){
		tempf_c_pos=pos;
	}
	public int get_tempf_c_pos(){
		return tempf_c_pos;
	}
	    
	//TMP IN F 
	public void set_temp_f_pos(int pos){
		temp_f_pos=pos;
	} 
	public int get_temp_f_pos(){
		return temp_f_pos;
	}
	//TMPA IN F
	public void set_tempa_f_pos(int pos){
		tempa_f_pos=pos;
	}
	public int get_tempa_f_pos(){
		return tempa_f_pos;
	}     
	
	//TMPB IN F
	public void set_tempb_f_pos(int pos){
		tempb_f_pos=pos;
	}
	public int get_tempb_f_pos(){
		return tempb_f_pos;
	}  
	//TMPC IN F
	public void set_tempc_f_pos(int pos){
		tempc_f_pos=pos;
	}
	public int get_tempc_f_pos(){
		return tempc_f_pos;
	}  
	//TMPD IN F
	public void set_tempd_f_pos(int pos){
		tempd_f_pos=pos;
	}
	public int get_tempd_f_pos(){
		return tempd_f_pos;
	}  
	//TMPE IN F
	public void set_tempe_f_pos(int pos){
		tempe_f_pos=pos;
	}
	public int get_tempe_f_pos(){
		return tempe_f_pos;
	}  
	
	public void set_tempf_f_pos(int pos){
		tempf_f_pos=pos;
	}
	public int get_tempf_f_pos(){
		return tempf_f_pos;
	} 
	
	public void set_rh_pos(int pos){
		rh_pos=pos;
	}
	public int get_rh_pos(){
		return rh_pos;
	} 
	
	
	public void set_dew_point_pos(int pos){
		dew_point_pos=pos;
	}
	public int get_dew_point_pos(){
		return dew_point_pos;
	} 
	
	public void set_solar_radiation_pos(int pos){
		solar_radiation_pos=pos;
	}
	public int get_solar_radiation_pos(){
		return solar_radiation_pos;
	}      
	
	public void set_rain_fall_pos(int pos){
		rain_fall_pos=pos;
	}
	public int get_rain_fall_pos(){
		return rain_fall_pos;
	}  
	public void set_wind_direction_pos(int pos){
		wind_direction_pos=pos;
	}
	public int get_wind_direction_pos(){
		return wind_direction_pos;
	}       
	public void set_wind_speed_pos(int pos){
		wind_speed_pos=pos;
	}
	public int get_wind_speed_pos(){
		return wind_speed_pos;
	}
	public void set_wind_gust_pos(int pos){
		wind_gust_pos=pos;
	}
	public int get_wind_gust_pos(){
		return wind_gust_pos;
	}   
	
	public void set_ecbc_pos(int pos){
		ecbc_pos=pos;
	}
	public int get_ecbc_pos(){
		return ecbc_pos;
	}  
	
	//soil_moist a to d
	public void set_vwca_pos(int pos){
		vwca_pos=pos;
	}
	public int get_vwca_pos(){
		return vwca_pos;
	}  
	
	
	public void set_vwcb_pos(int pos){
		vwcb_pos=pos;
	}
	public int get_vwcb_pos(){
		return vwcb_pos;
	}  
	
	public void set_vwcc_pos(int pos){
		vwcc_pos=pos;
	}
	public int get_vwcc_pos(){
		return vwcc_pos;
	} 
	public void set_vwcd_pos(int pos){
		vwcd_pos=pos;
	}
	public int get_vwcd_pos(){
		return vwcd_pos;
	} 
	//line width
	public void set_line_width(int pos){
		line_width=pos;
	}
	public int get_line_width(){
		return line_width;
	} 
	
	public void Write_Weather_Info_List_Into_CSV_File(ArrayList<Weather_Info> weather_info) throws IOException{
		BufferedWriter out = null;
		try  
		{
			int total_ctr=1;
			int ctr=1;
		    FileWriter fstream = new FileWriter("res/Weather_Info.CSV", false); //true tells to append data.
		    out = new BufferedWriter(fstream);
		    //titles
		    out.write("##### This is a comment line.\n");
		    out.write("##### Another comment line. \n");
		    out.write("station_id"+"\t"+"day"+"\t"+"month"+"\t"+"year"+"\t"+"julian_data"+"\t"+"time"+"\t"+"temp_f"+"\t"+"tempa_f"+"\t"+"tempb_f"+"\t"+"tempc_f"+"\t"+"tempd_f"+"\t"+"tempe_f"+"\t"+"tempf_f"+"\t"+"temp_c"+"\t"+"tempa_c"+"\t"+"tempb_c"+"\t"+"tempc_c"+"\t"+"tempd_c"+"\t"+"tempe_c"+"\t"+"tempf_c"+"\t"+"ec_smec300"+"\t"+"soil_moist_vwc_a"+"\t"+"soil_moist_vwc_b"+"\t"+"soil_moist_vwc_c"+"\t"+"soil_moist_vwc_d"+"\t"+"rh"+"\t"+"dew_point"+"\t"+"solar_radiation"+"\t"+"rain_fall"+"\t"+"wind_direction"+"\t"+"wind_speed"+"\t"+"wind_gust"+"\n");

		   
		    	for (Weather_Info info: weather_info){
		    	   
		    		
		    		
		    		///////////////
		    		out.write(info.getStationId()+"\t");
		    		out.write(info.getDay()+"\t");
					out.write(info.getMonth()+"\t");
					out.write(info.getYear()+"\t");
					
					out.write(info.getJulian_Date()+"\t");
					out.write(info.getTime()+"\t");
					
					//tmp in F
					out.write( info.getTMP()+"\t");
					out.write( info.getTMPA()+"\t");
					
					out.write( info.getTMPB()+"\t");
					out.write( info.getTMPC()+"\t");
					out.write( info.getTMPD()+"\t");
					out.write( info.getTMPE()+"\t");
					
					out.write( info.getTMPF()+"\t");
					//tmp in c
					out.write( info.getTMP_C()+"\t");
					out.write( info.getTMPA_C()+"\t");
					out.write( info.getTMPB_C()+"\t");
					
					out.write( info.getTMPC_C()+"\t");
					out.write( info.getTMPD_C()+"\t");
					out.write( info.getTMPE_C()+"\t");
					out.write( info.getTMPF_C()+"\t");
					//ecbc
					out.write( info.getECBC()+"\t");
					out.write( info.getVWCA()+"\t");
					out.write( info.getVWCB()+"\t");
					out.write( info.getVWCC()+"\t");
					
					out.write( info.getVWCD()+"\t");
					
					out.write( info.getRH()+"\t");
					out.write( info.getDew_Point()+"\t");
					out.write( info.getSolar_Rad()+"\t");
					
					out.write( info.getRainfall()+"\t");
					out.write( info.getWind_Dir()+"\t");
					out.write( info.getWind_Speed()+"\t");
					out.write( info.getWind_Gust()+"\t");
					
					out.write("\n");
					
					total_ctr++;
					
		    	}
			    	
		    	
		    	
		    
		}
		catch (IOException e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
		finally
		{
		    if(out != null) {
		        out.close();
		    }
		}
		
		
		
	}
	
	public String[] add_zero_to_words(String[] words){
		
		int dimension=get_line_width();
		String[] new_words= new String[dimension]; 
		String zero="0";
		if(get_line_width()>words.length){
			 
			 
			 for(int i=0;i<dimension;i++){
				 new_words[i]=zero;
			 }
			 for(int j=0;j<words.length;j++){
				 new_words[j]=words[j];
			 }
			 return new_words;
		}
		
		return words;
	}
	
	public void Weather_Data_ToString(){
		System.out.println("The Weather_Data_List_ToString: ");
		for(Weather_Info info: weather_info_list){
			System.out.print("Station_id:"+ info.getStationId()+"\tMonth:"+info.getMonth()+"\tDay:"+info.getDay()+"\tYear"+info.getYear());
			System.out.print("Dew Point:"+info.getDew_Point()+"\tSolar Rad:"+info.getSolar_Rad()+"\tRainfull:"+info.getSolar_Rad());
		}
	}
}

