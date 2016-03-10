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
import java.util.Calendar;
import java.util.HashMap;
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
	int dew_point_in_F_pos; 
	
	int dew_point_in_C_pos;
	int wind_speed_kmh_pos;
	int wind_gust_kmh_pos;
	
	int solar_radiation_pos;  
	int rain_fall_pos;       
	int wind_direction_pos;  
	
	int wind_speed_mph_pos;      
	int wind_gust_mph_pos;
	
	private int ecbc_pos;
	private int vwca_pos;
	private int vwcb_pos;
	private int vwcc_pos;
	private int vwcd_pos; 
	

	private int uv_light_pos;
	
	
	//Newly POS added according to Darwin's list 
	
	 	private int temperatureInt_in_F_pos; //TEMP interval IN F
	    private int temperatureIR_in_F_pos; //TEMP IR IN F
	    
	    private int temperatureInt_in_C_pos;  //TEMP INTERNAL in C
	    private int temperatureIR__in_C_pos; //TEMP IR IN C
	    
	    
	    private int wsolar_rad_pos;//WSolar_rad
	    private int barometer_pos; //BAR
	    private int barometerXR_pos; //BARXR
	    private int milliAmp_pos; //MAM
	    private int wmilliAmp_pos; //WMAM
	    private int lmilliAmp_pos; //LMAM
	    
	    
	    private int rainfallIn_pos;
	    private int rainfallMM_pos; //
	    
	    private int wetness_pos; //WET
		private int par_pos; //PAR
		private int wpar_pos; //WPAR
		
		private int watermark_pos; //SMS
		private int watermarkA_pos; //SMSA
		private int watermarkB_pos; //SMSB
		private int watermarkC_pos; //SMSC
		private int watermarkD_pos; //SMSD
		private int lowTension_pos; //SMSLT
		private int co2_pos;//CO2
		
		
		//STATE
		private int state_pos;//STA
	    private int ec_pos; //ECB (Triple Sensor)
	    
	    private int echo25_pos;//soil_moist_vwc25
	    private int echo5_pos; //soil_moist_vwc5
	    private int echo10_pos; //soil_moist_vwc10
	    private int sm100_pos; //soil_moist_vwc100 VWC
	    
	    private int none_pos;
	    private int raw_pos; //RAW
	    private int voltage_pos; //VLT
	    private int battery_pos; //VLT_BAT
	    
	    private int batteryPct_pos;//BTL
	    private int wvoltage_pos; //WVLT
	    
	 //////////////////////////new strings  POS
	
	
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
 * This method will read all the files in the list and return the Set<Pair> that appear in the files	
 */
	
	public Set<Pair> Check_Weather_Data_Para_And_Abbr(ArrayList<File> files){

		// read the index file into a map<String, String>, to get the certain positions,decide which columns are there..
	       // String fileName = file.getName();
			String fileName;
			// positions for the SWD columns default 
			//int station_id;     
			ArrayList<String> one_line= new ArrayList<String>();
			ArrayList<ArrayList<String>> two_dimention_table = new ArrayList<ArrayList<String>>();
			Set<Pair> weather_data_pairs = new HashSet<Pair>();
			try {
					
				
					for (File file: files){
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
					}
			} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}
		
		//after reading the file, decide the position of each item...traverse the 2d ArrayList
			System.out.println("Output the two_dimention_table");
			
				for(int index=0;index<two_dimention_table.get(0).size();index++){
					//System.out.println("Output the two_dimention_table");
					System.out.println(two_dimention_table.get(0).get(index));
					weather_data_pairs.add(new Pair(two_dimention_table.get(0).get(index), two_dimention_table.get(2).get(index)));
					
				}
		
		     System.out.println("Output the set contents: ");
		     for(Pair pair: weather_data_pairs){
		    	 System.out.print(pair.get_prv()+"\t");
		     }
		     System.out.println();
		     for(Pair pair: weather_data_pairs){
		    	 System.out.print(pair.get_next()+"\t");
		     }
			return 	weather_data_pairs;
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
				
				//TempInt
				if(two_dimention_table.get(0).get(index).contains("TemperatureInt (*F)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setTemperatureInt_in_F_pos(index);
				}
				//IRTemp
				if(two_dimention_table.get(0).get(index).contains("IRTemp (*F)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setTemperatureIR_in_F_pos(index);
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
				if(two_dimention_table.get(0).get(index).contains("Rainfall (In)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("RNF")){
					set_rain_fall_pos(index);
				}
			//Wind dir
				if(two_dimention_table.get(0).get(index).contains("Wind Dir")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WND")){
					set_wind_direction_pos(index);
				}
			//Wind Gust in mph
				if(two_dimention_table.get(0).get(index).contains("Wind Gust (mph)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WNG")){
					setWind_gust_mph_pos(index);
				}
			//Wind Gust in kmh	
				if(two_dimention_table.get(0).get(index).contains("Wind Gust (km/h)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WNG")){
					setWind_gust_kmh_pos(index);
				}
				
			//Wind Speed in mph	
				if(two_dimention_table.get(0).get(index).contains("Wind Speed (mph)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WNS")){
					setWind_speed_mph_pos(index);
				}
				//Wind Speed kmh
				if(two_dimention_table.get(0).get(index).contains("Wind Speed (km/h)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WNS")){
					setWind_speed_kmh_pos(index);
				}
			//Dew Point
				if(two_dimention_table.get(0).get(index).contains("Dew Point (*F)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("DEW")){
					setDew_point_in_F_pos(index);
				}
				//According to new list from darwin, Dew Point also has TMP as abbr..
				if(two_dimention_table.get(0).get(index).contains("Dew Point (*F)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setDew_point_in_F_pos(index);
				}
				
			//Dew Point in C	
				if(two_dimention_table.get(0).get(index).contains("Dew Point (*C)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("DEW")){
					setDew_point_in_C_pos(index);
				}
				//According to new list from darwin, Dew Point also has TMP as abbr..
				if(two_dimention_table.get(0).get(index).contains("Dew Point (*C)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setDew_point_in_C_pos(index);
				}
			//UVL
				if(two_dimention_table.get(0).get(index).contains("UV Light")&two_dimention_table.get(2).get(index).equalsIgnoreCase("UVL")){
					set_uv_light_pos(index);
				}
			/////////////////////////////////////////////////new items
				//TempInt in F
				if(two_dimention_table.get(0).get(index).contains("TemperatureInt (*F)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setTemperatureInt_in_F_pos(index);
				}
				//IRTemp in F
				if(two_dimention_table.get(0).get(index).contains("IRTemp (*F)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setTemperatureIR_in_F_pos(index);
				}	
				
				//TempInt in C
				if(two_dimention_table.get(0).get(index).contains("TemperatureInt (*C)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setTemperatureInt_in_C_pos(index);
				}
				//IRTemp in C
				if(two_dimention_table.get(0).get(index).contains("IRTemp (*C)")&two_dimention_table.get(2).get(index).equalsIgnoreCase("TMP")){
					setTemperatureIR_in_C_pos(index);
				}	
				////////////wsolar_rad_pos
				if(two_dimention_table.get(0).get(index).contains("WSolarRad")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SRD")){
					setWsolar_rad_pos(index);
				}	
				
				// private int barometer_pos; //BAR
				if(two_dimention_table.get(0).get(index).contains("Barometer")&two_dimention_table.get(2).get(index).equalsIgnoreCase("BAR")){
					setBarometer_pos(index);
				}	
				// private int barometerXR_pos; //BARXR
				if(two_dimention_table.get(0).get(index).contains("BarometerXR")&two_dimention_table.get(2).get(index).equalsIgnoreCase("BAR")){
					setBarometerXR_pos(index);
				}	
				//private int milliAmp_pos; //MAM
				if(two_dimention_table.get(0).get(index).contains("MilliAmp")&two_dimention_table.get(2).get(index).equalsIgnoreCase("MAM")){
					setMilliAmp_pos(index);
				}	
				// private int wmilliAmp_pos; //WMAM
				if(two_dimention_table.get(0).get(index).contains("WMilliAmp")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WMAM")){
					setWmilliAmp_pos(index);
				}	
				//private int lmilliAmp_pos; //LMAM
				   
				if(two_dimention_table.get(0).get(index).contains("LMilliAmp")&two_dimention_table.get(2).get(index).equalsIgnoreCase("LMAM")){
					setLmilliAmp_pos(index);
				}	
				 // private int rainfallIn_pos;  
				if(two_dimention_table.get(0).get(index).contains("RainfallIn")&two_dimention_table.get(2).get(index).equalsIgnoreCase("RNF")){
					setRainfallIn_pos(index);
				}	
				// private int rainfallMM_pos; //
				if(two_dimention_table.get(0).get(index).contains("RainfallMM")&two_dimention_table.get(2).get(index).equalsIgnoreCase("RNF")){
					setRainfallMM_pos(index);
				}	
				// private int wetness_pos; //WET
				if(two_dimention_table.get(0).get(index).contains("Wetness")&two_dimention_table.get(2).get(index).equalsIgnoreCase("WET")){
					setWetness_pos(index);
				}	
				//private int par_pos; //PAR
				if(two_dimention_table.get(0).get(index).contains("PAR")&two_dimention_table.get(2).get(index).equalsIgnoreCase("PAR")){
					setPar_pos(index);
				}	
				//private int wpar_pos; //WPAR
				   
				if(two_dimention_table.get(0).get(index).contains("WPAR")&two_dimention_table.get(2).get(index).equalsIgnoreCase("PAR")){
					setWpar_pos(index);
				}	
				    
				//    private int watermark_pos; //SMS
				if(two_dimention_table.get(0).get(index).contains("Watermark")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SMS")){
					setWatermark_pos(index);
				}	
				       
				//private int watermarkA_pos; //SMSA   
				if(two_dimention_table.get(0).get(index).contains("WatermarkA")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SMS")){
					setWatermarkA_pos(index);
				}   
				    
				//private int watermarkB_pos; //SMSB   
				if(two_dimention_table.get(0).get(index).contains("WatermarkB")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SMS")){
					setWatermarkB_pos(index);
				} 
					
				//private int watermarkC_pos; //SMSC	
				if(two_dimention_table.get(0).get(index).contains("WatermarkC")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SMS")){
					setWatermarkC_pos(index);
				} 	
				//private int watermarkD_pos; //SMSD	
				if(two_dimention_table.get(0).get(index).contains("WatermarkD")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SMS")){
					setWatermarkD_pos(index);
				} 
				
				//private int lowTension_pos; //SMS Low Tension
				if(two_dimention_table.get(0).get(index).contains("LowTension")&two_dimention_table.get(2).get(index).equalsIgnoreCase("SMS")){
					setLowTension_pos(index);
				} 
				//	private int co2_pos;//CO2
				if(two_dimention_table.get(0).get(index).contains("CO2")&two_dimention_table.get(2).get(index).equalsIgnoreCase("CO2")){
					setCo2_pos(index);
				} 
				////STATE
				//private int state_pos;//STA
				if(two_dimention_table.get(0).get(index).contains("State")&two_dimention_table.get(2).get(index).equalsIgnoreCase("STA")){
					setState_pos(index);
				} 
					
				//EC	private int ec_pos; //ECB (Triple Sensor)
				
				if(two_dimention_table.get(0).get(index).contains("EC")&two_dimention_table.get(2).get(index).equalsIgnoreCase("ECB")){
					setEc_pos(index);
				} 	
				
				//private int echo5_pos; //soil_moist_vwc5
				
				if(two_dimention_table.get(0).get(index).contains("ECHO5")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWC")){
					setEcho5_pos(index);
				} 
				
				//private int echo10_pos; //soil_moist_vwc10
				if(two_dimention_table.get(0).get(index).contains("ECHO10")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWC")){
					setEcho10_pos(index);
				}
				
				// private int echo25_pos;//soil_moist_vwc25
				if(two_dimention_table.get(0).get(index).contains("ECHO25")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWC")){
					setEcho25_pos(index);
				} 	
					
				//    private int sm100_pos; //soil_moist_vwc100 VWC
				    
				if(two_dimention_table.get(0).get(index).contains("SM100")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VWC")){
					setSm100_pos(index);
				} 
				
				//private int none_pos;
				if(two_dimention_table.get(0).get(index).contains("NONE")&two_dimention_table.get(2).get(index).equalsIgnoreCase("")){
					setNone_pos(index);
				} 
				
				// private int raw_pos; //RAW
				if(two_dimention_table.get(0).get(index).contains("RAW")&two_dimention_table.get(2).get(index).equalsIgnoreCase("RAW")){
					setRaw_pos(index);
				} 
				    
				//    private int voltage_pos; //VLT
				if(two_dimention_table.get(0).get(index).contains("Voltage")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VLT")){
					setVoltage_pos(index);
				}    
				    
				//    private int battery_pos; //VLT_BAT
				   
				if(two_dimention_table.get(0).get(index).contains("Battery")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VLT")){
					setBattery_pos(index);
				}    
				//   private int batteryPct_pos;//BTL 
				if(two_dimention_table.get(0).get(index).contains("BatteryPct")&two_dimention_table.get(2).get(index).equalsIgnoreCase("BTL")){
					setBatteryPct_pos(index);
				}  
				
				// private int wvoltage_pos; //WVLT
				if(two_dimention_table.get(0).get(index).contains("WVoltage")&two_dimention_table.get(2).get(index).equalsIgnoreCase("VLT")){
					setBattery_pos(index);
				}  
				    
				   
				
				
				
				
			/////////////////////////////////////////////////new items	
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
				System.out.println("Wind Gust " + get_wind_gust_mph_pos());	
				//Wind Speed	
				System.out.println("Wind Speed " + get_wind_speed_mph_pos());	
				//Dew Point
				System.out.println("Dew Point " + getDew_point_in_F_pos());	
				//UV Light
				System.out.println("UV Light " + get_uv_light_pos());	

			//////new items
				
				
			/////new items
				
			
			
			
			
			
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
		dew_point_in_F_pos=0;       
		solar_radiation_pos=0;  
		 rain_fall_pos=0;   
		 //wind 3 items
		 wind_direction_pos=0;  
		 wind_speed_mph_pos=0;      
		 wind_gust_mph_pos=0; 
		 uv_light_pos=0;
		 
		 
		 //new items
		   temperatureInt_in_F_pos=0; //TEMP interval IN F
		      temperatureIR_in_F_pos=0; //TEMP IR IN F
		    
		      temperatureInt_in_C_pos=0;  //TEMP INTERNAL in C
		      temperatureIR__in_C_pos=0; //TEMP IR IN C
		    
		    
		      wsolar_rad_pos=0;//WSolar_rad
		      barometer_pos=0; //BAR
		      barometerXR_pos=0; //BARXR
		      milliAmp_pos=0; //MAM
		      wmilliAmp_pos=0; //WMAM
		      lmilliAmp_pos=0; //LMAM
		    
		    
		      rainfallIn_pos=0;
		      rainfallMM_pos=0; //
		    
		      wetness_pos=0; //WET
			  par_pos=0; //PAR
			  wpar_pos=0; //WPAR
			
			  watermark_pos=0; //SMS
			  watermarkA_pos=0; //SMSA
			  watermarkB_pos=0; //SMSB
			  watermarkC_pos=0; //SMSC
			  watermarkD_pos=0; //SMSD
			  lowTension_pos=0; //SMSLT
			  co2_pos=0;//CO2
			
			
			//STATE
			  state_pos=0;//STA
		      ec_pos=0; //ECB (Triple Sensor)
		    
		      echo25_pos=0;//soil_moist_vwc25
		      echo5_pos=0; //soil_moist_vwc5
		      echo10_pos=0; //soil_moist_vwc10
		      sm100_pos=0; //soil_moist_vwc100 VWC
		    
		      none_pos=0;
		      raw_pos=0; //RAW
		      voltage_pos=0; //VLT
		      battery_pos=0; //VLT_BAT
		    
		      batteryPct_pos=0;//BTL
		      wvoltage_pos=0; //WVLT
		 
		 //new items
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
	@SuppressWarnings("null")
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
			
			//day of year
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Integer.parseInt(year_mm_dd[0]),Integer.parseInt(year_mm_dd[1])-1,Integer.parseInt(year_mm_dd[2]));
			int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR); 
			//System.out.println("int value of year, mm, dd: "+ Integer.parseInt(year_mm_dd[0])+", "+(Integer.parseInt(year_mm_dd[1])-1)+", "+Integer.parseInt(year_mm_dd[2]));
			weather_info_one.setJulian_Date(Integer.toString(dayOfYear));
			
			
			//System.out.println("The weather_info_one is setting dayOfYear as:"+ weather_info_one.getJulian_Date());
			
			
			weather_info_one.setTime(time);
			
			
			
			//TMP IN C
			if(get_temp_c_pos()==0){
				weather_info_one.setTMP_C("");
			}
			else {
				weather_info_one.setTMP_C(words[get_temp_c_pos()]);
				System.out.println("The weather_info_one is setting TMP_C as: "+ weather_info_one.getTMP_C());
			}
			//TMPA IN C
			if(get_tempa_c_pos()==0){
				weather_info_one.setTMPA_C("");
			}
			else {
				weather_info_one.setTMPA_C(words[get_tempa_c_pos()]);
			}
			//////////////////////////////
			if(get_tempb_c_pos()==0){
				weather_info_one.setTMPB_C("");
			}
			else {
				weather_info_one.setTMPB_C(words[get_tempb_c_pos()]);
			}
			//////////////////////////////
			if(get_tempc_c_pos()==0){
				weather_info_one.setTMPC_C("");
			}
			else {
				weather_info_one.setTMPC_C(words[get_tempc_c_pos()]);
			}
			if(get_tempd_c_pos()==0){
				weather_info_one.setTMPD_C("");
			}
			else {
				weather_info_one.setTMPD_C(words[get_tempd_c_pos()]);
			}
			if(get_tempe_c_pos()==0){
				weather_info_one.setTMPE_C("");
			}
			else {
				weather_info_one.setTMPE_C(words[get_tempe_c_pos()]);
			}
			if(get_tempf_c_pos()==0){
				weather_info_one.setTMPF_C("");
			}
			else {
				weather_info_one.setTMPF_C(words[get_tempf_c_pos()]);
			}
			//TMP IN F
			
			if(get_temp_f_pos()==0){
				weather_info_one.setTMP("");
			}
			else {
				int temp=get_temp_f_pos();
				weather_info_one.setTMP(words[temp]);

			}
			//TMPA IN F
			if(get_tempa_f_pos()==0){
				weather_info_one.setTMPA("");
			}
			else {
				weather_info_one.setTMPA(words[get_tempa_f_pos()]);
			}
			//TMPB IN F
			if(get_tempb_f_pos()==0){
				weather_info_one.setTMPB("");
			}
			else {
				weather_info_one.setTMPB(words[get_tempb_f_pos()]);
			}
			//TMPC IN F
			if(get_tempc_f_pos()==0){
				weather_info_one.setTMPC("");
			}
			else {
				weather_info_one.setTMPC(words[get_tempc_f_pos()]);
			}
			//TMPD IN F
			if(get_tempd_f_pos()==0){
				weather_info_one.setTMPD("");
			}
			else {
				weather_info_one.setTMPD(words[get_tempd_f_pos()]);
			}
			//TMPE IN F
			if(get_tempe_f_pos()==0){
				weather_info_one.setTMPE("");
			}
			else {
				weather_info_one.setTMPE(words[get_tempe_f_pos()]);
			}
			//TMPF IN F
			if(get_tempf_f_pos()==0){
				weather_info_one.setTMPF("");
			}
			else {
				weather_info_one.setTMPF(words[get_tempf_f_pos()]);
			}
			//RH
			if(get_rh_pos()==0){
				weather_info_one.setRH("");
			}
			else {
				weather_info_one.setRH(words[get_rh_pos()]);
			}
			//DEW in F
			if(get_dew_point_in_F_pos()==0){
				weather_info_one.setDew_point_F("");
			}
			else {
				weather_info_one.setDew_point_F(words[getDew_point_in_F_pos()]);
			}
			//SOLAR RADIATION
			if(get_solar_radiation_pos()==0){
				weather_info_one.setSolar_Rad("");
			}
			else {
				weather_info_one.setSolar_Rad(words[get_solar_radiation_pos()]);
			}
			//RAIN FALL
			if(get_rain_fall_pos()==0){
				weather_info_one.setRainfall("");
			}
			else {
				weather_info_one.setRainfall(words[get_rain_fall_pos()]);
			}
			//WIND DIR
			if(get_wind_direction_pos()==0){
				weather_info_one.setWind_Dir("");
			}
			else {
				weather_info_one.setWind_Dir(words[get_wind_direction_pos()]);
			}
			//WIND GUST mph
			if(get_wind_gust_mph_pos()==0){
				weather_info_one.setWind_Gust_mph("");
			}
			else {
				weather_info_one.setWind_Gust_mph(words[get_wind_gust_mph_pos()]);
			}
			//WIND SPEED mph
			if(get_wind_speed_mph_pos()==0){
				weather_info_one.setWind_Speed_mph("");
			}
			else {
				weather_info_one.setWind_Speed_mph(words[get_wind_speed_mph_pos()]);
			}
			//ECBC
			if(get_ecbc_pos()==0){
				weather_info_one.setECBC("");
			}
			else {
				weather_info_one.setECBC(words[get_ecbc_pos()]);
			}
			//VWCA
			if(get_vwca_pos()==0){
				weather_info_one.setVWCA("");
			}
			else {
				weather_info_one.setVWCA(words[get_vwca_pos()]);
			}
			//VWCB
			if(get_vwcb_pos()==0){
						weather_info_one.setVWCB("");
			}
			else {
						weather_info_one.setVWCB(words[get_vwcb_pos()]);
			}
			//VWCC
			if(get_vwcc_pos()==0){
						weather_info_one.setVWCC("");
			}
			else {
						weather_info_one.setVWCC(words[get_vwcc_pos()]);
			}
					//VWCD
			if(get_vwcd_pos()==0){
						weather_info_one.setVWCD("");
			}
			else {
						weather_info_one.setVWCD(words[get_vwcd_pos()]);
			}
			//UV LIGHT
			if(get_uv_light_pos()==0){
				weather_info_one.setUVL("");
			}
			else {
						weather_info_one.setUVL(words[get_uv_light_pos()]);
			}
			///////////////////////////////////// new items from the list
			
			//private int temperatureInt_in_F_pos; //TEMP interval IN F
			if(getTemperatureInt_in_F_pos()==0){
				weather_info_one.setTemperatureInt_in_F("");
			}
			else {
						weather_info_one.setUVL(words[getTemperatureInt_in_F_pos()]);
			}
			
			if(get_uv_light_pos()==0){
				weather_info_one.setUVL("");
			}
			else {
						weather_info_one.setUVL(words[get_uv_light_pos()]);
			}
		 	
			//Dew point C
			if(getDew_point_in_C_pos()==0){
				weather_info_one.setDew_point_C("");
			}
			else {
				weather_info_one.setDew_point_C(words[getDew_point_in_C_pos()]);
			}
			
			//Wind gust kmh
			if(getWind_gust_kmh_pos()==0){
				weather_info_one.setWind_gust_kmh("");
			}
			else {
				weather_info_one.setWind_gust_kmh(words[getWind_gust_kmh_pos()]);
			}
			//Wind speed kmh
			
			if(getWind_speed_kmh_pos()==0){
				weather_info_one.setWind_speed_kmh("");
			}
			else {
				weather_info_one.setWind_speed_kmh(words[getWind_speed_kmh_pos()]);
			}
			
			//////////////////////////////////////////////
/*		    private int temperatureIR_in_F_pos; //TEMP IR IN F
		    
		    private int temperatureInt_in_C_pos;  //TEMP INTERNAL in C
		    private int temperatureIR__in_C_pos; //TEMP IR IN C
		    
		    
		    private int wsolar_rad_pos;//WSolar_rad
		    private int barometer_pos; //BAR
		    private int barometerXR_pos; //BARXR
		    private int milliAmp_pos; //MAM
		    private int wmilliAmp_pos; //WMAM
		    private int lmilliAmp_pos; //LMAM
		    
		    
		    private int rainfallIn_pos;
		    private int rainfallMM_pos; //
		    
		    private int wetness_pos; //WET
			private int par_pos; //PAR
			private int wpar_pos; //WPAR
			
			private int watermark_pos; //SMS
			private int watermarkA_pos; //SMSA
			private int watermarkB_pos; //SMSB
			private int watermarkC_pos; //SMSC
			private int watermarkD_pos; //SMSD
			private int lowTension_pos; //SMSLT
*/			
			
			//private int co2_pos;//CO2
			if(getCo2_pos()==0){
				weather_info_one.setCo2("");
			}
			else {
				weather_info_one.setCo2(words[getCo2_pos()]);
			}
			
			//STATE
/*			private int state_pos;//STA
		    private int ec_pos; //ECB (Triple Sensor)
		    
		    private int echo25_pos;//soil_moist_vwc25
		    private int echo5_pos; //soil_moist_vwc5
		    private int echo10_pos; //soil_moist_vwc10
		    private int sm100_pos; //soil_moist_vwc100 VWC
		    
		    private int none_pos;
		    private int raw_pos; //RAW
		    private int voltage_pos; //VLT
		    private int battery_pos; //VLT_BAT
		    
		    private int batteryPct_pos;//BTL
		    private int wvoltage_pos; //WVLT
			
*/			
			///////////////////////////////////////new items from the list
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
			System.out.println("Day Of Year: "+weather_info_one.getJulian_Date());
			System.out.print("Dew Point:"+weather_info_one.getDew_point_F()+"\tSolar Rad:"+weather_info_one.getSolar_Rad()+"\tRainfall:"+weather_info_one.getRainfall());
			System.out.print("TMP_in_C:"+ weather_info_one.getTMP_C()+"\tTMPA_in_C:"+weather_info_one.getTMPA_C()+"\tTMPB_in_C:"+weather_info_one.getTMPB_C()+"\tTMPC_in_C:"+weather_info_one.getTMPC_C()+"\tTMPD_in_C:"+weather_info_one.getTMPD_C()+"\tTMPE_in_C:"+weather_info_one.getTMPE_C()+"\tTMPF_in_C:"+weather_info_one.getTMPF_C());
			System.out.println();
			System.out.print("TMP_in_F:"+ weather_info_one.getTMP()+"\tTMPA_in_F:"+weather_info_one.getTMPA()+"\tTMPB_in_F:"+weather_info_one.getTMPB()+"\tTMPC_in_F:"+weather_info_one.getTMPC()+"\tTMPD_in_F:"+weather_info_one.getTMPD()+"\tTMPE_in_F:"+weather_info_one.getTMPE()+"\tTMPF_in_F:"+weather_info_one.getTMPF());
			System.out.println();
			//ECBC +VWCs
			
			System.out.print("ECBC:"+weather_info_one.getECBC()+"\tVWCA:"+weather_info_one.getVWCA()+"\tVWCB:"+weather_info_one.getVWCB()+"\tVWCC:"+weather_info_one.getVWCC()+"\tVWCD:"+weather_info_one.getVWCD());
		    //WIND 
			System.out.print("Wind Dir:"+weather_info_one.getWind_Dir()+"\tWind Gust:"+weather_info_one.getWind_Gust_mph()+"\tWind Speed:"+weather_info_one.getWind_Speed_mph());
			//humidity
			System.out.print("\tHumidity:"+ weather_info_one.getRH());
			//uv light
			System.out.print("\tUV Light: "+ weather_info_one.getUVL());
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
	
	
	public void set_dew_point_in_F_pos(int pos){
		dew_point_in_F_pos=pos;
	}
	public int get_dew_point_in_F_pos(){
		return dew_point_in_F_pos;
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
	public void set_wind_speed_mph_pos(int pos){
		wind_speed_mph_pos=pos;
	}
	public int get_wind_speed_mph_pos(){
		return wind_speed_mph_pos;
	}
	public void set_wind_gust_mph_pos(int pos){
		wind_gust_mph_pos=pos;
	}
	public int get_wind_gust_mph_pos(){
		return wind_gust_mph_pos;
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
	
	public int get_uv_light_pos() {
		return uv_light_pos;
	}
	public void set_uv_light_pos(int pos) {
		this.uv_light_pos = pos;
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
					out.write( info.getDew_point_F()+"\t");
					out.write( info.getSolar_Rad()+"\t");
					
					out.write( info.getRainfall()+"\t");
					out.write( info.getWind_Dir()+"\t");
					out.write( info.getWind_Speed_mph()+"\t");
					out.write( info.getWind_Gust_mph()+"\t");
					
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
			System.out.print("Dew Point:"+info.getDew_point_F()+"\tSolar Rad:"+info.getSolar_Rad()+"\tRainfull:"+info.getSolar_Rad());
		}
	}
	public ArrayList<File> getSd_swd_files() {
		return sd_swd_files;
	}
	public void setSd_swd_files(ArrayList<File> sd_swd_files) {
		this.sd_swd_files = sd_swd_files;
	}
	public ArrayList<String> getWeather_data_abbre() {
		return weather_data_abbre;
	}
	public void setWeather_data_abbre(ArrayList<String> weather_data_abbre) {
		this.weather_data_abbre = weather_data_abbre;
	}
	public ArrayList<Weather_Info> getWeather_info_list() {
		return weather_info_list;
	}
	public void setWeather_info_list(ArrayList<Weather_Info> weather_info_list) {
		this.weather_info_list = weather_info_list;
	}
	public Set<Weather_Info> getWeather_info_set() {
		return weather_info_set;
	}
	public void setWeather_info_set(Set<Weather_Info> weather_info_set) {
		this.weather_info_set = weather_info_set;
	}
	public int getLine_width() {
		return line_width;
	}
	public void setLine_width(int line_width) {
		this.line_width = line_width;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getTemp_c_pos() {
		return temp_c_pos;
	}
	public void setTemp_c_pos(int temp_c_pos) {
		this.temp_c_pos = temp_c_pos;
	}
	public int getTempa_c_pos() {
		return tempa_c_pos;
	}
	public void setTempa_c_pos(int tempa_c_pos) {
		this.tempa_c_pos = tempa_c_pos;
	}
	public int getTempb_c_pos() {
		return tempb_c_pos;
	}
	public void setTempb_c_pos(int tempb_c_pos) {
		this.tempb_c_pos = tempb_c_pos;
	}
	public int getTempc_c_pos() {
		return tempc_c_pos;
	}
	public void setTempc_c_pos(int tempc_c_pos) {
		this.tempc_c_pos = tempc_c_pos;
	}
	public int getTempd_c_pos() {
		return tempd_c_pos;
	}
	public void setTempd_c_pos(int tempd_c_pos) {
		this.tempd_c_pos = tempd_c_pos;
	}
	public int getTempe_c_pos() {
		return tempe_c_pos;
	}
	public void setTempe_c_pos(int tempe_c_pos) {
		this.tempe_c_pos = tempe_c_pos;
	}
	public int getTempf_c_pos() {
		return tempf_c_pos;
	}
	public void setTempf_c_pos(int tempf_c_pos) {
		this.tempf_c_pos = tempf_c_pos;
	}
	public int getTemp_f_pos() {
		return temp_f_pos;
	}
	public void setTemp_f_pos(int temp_f_pos) {
		this.temp_f_pos = temp_f_pos;
	}
	public int getTempa_f_pos() {
		return tempa_f_pos;
	}
	public void setTempa_f_pos(int tempa_f_pos) {
		this.tempa_f_pos = tempa_f_pos;
	}
	public int getTempb_f_pos() {
		return tempb_f_pos;
	}
	public void setTempb_f_pos(int tempb_f_pos) {
		this.tempb_f_pos = tempb_f_pos;
	}
	public int getTempc_f_pos() {
		return tempc_f_pos;
	}
	public void setTempc_f_pos(int tempc_f_pos) {
		this.tempc_f_pos = tempc_f_pos;
	}
	public int getTempd_f_pos() {
		return tempd_f_pos;
	}
	public void setTempd_f_pos(int tempd_f_pos) {
		this.tempd_f_pos = tempd_f_pos;
	}
	public int getTempe_f_pos() {
		return tempe_f_pos;
	}
	public void setTempe_f_pos(int tempe_f_pos) {
		this.tempe_f_pos = tempe_f_pos;
	}
	public int getTempf_f_pos() {
		return tempf_f_pos;
	}
	public void setTempf_f_pos(int tempf_f_pos) {
		this.tempf_f_pos = tempf_f_pos;
	}
	public int getRh_pos() {
		return rh_pos;
	}
	public void setRh_pos(int rh_pos) {
		this.rh_pos = rh_pos;
	}
	public void setDew_point_pos(int dew_point_pos) {
		this.dew_point_in_F_pos = dew_point_pos;
	}
	public int getSolar_radiation_pos() {
		return solar_radiation_pos;
	}
	public void setSolar_radiation_pos(int solar_radiation_pos) {
		this.solar_radiation_pos = solar_radiation_pos;
	}
	public int getRain_fall_pos() {
		return rain_fall_pos;
	}
	public void setRain_fall_pos(int rain_fall_pos) {
		this.rain_fall_pos = rain_fall_pos;
	}
	public int getWind_direction_pos() {
		return wind_direction_pos;
	}
	public void setWind_direction_pos(int wind_direction_pos) {
		this.wind_direction_pos = wind_direction_pos;
	}
	public int getWind_speed_pos() {
		return wind_speed_mph_pos;
	}
	public void setWind_speed_pos(int wind_speed_pos) {
		this.wind_speed_mph_pos = wind_speed_pos;
	}
	public int getWind_gust_pos() {
		return wind_gust_mph_pos;
	}
	public void setWind_gust_pos(int wind_gust_pos) {
		this.wind_gust_mph_pos = wind_gust_pos;
	}
	public int getEcbc_pos() {
		return ecbc_pos;
	}
	public void setEcbc_pos(int ecbc_pos) {
		this.ecbc_pos = ecbc_pos;
	}
	public int getVwca_pos() {
		return vwca_pos;
	}
	public void setVwca_pos(int vwca_pos) {
		this.vwca_pos = vwca_pos;
	}
	public int getVwcb_pos() {
		return vwcb_pos;
	}
	public void setVwcb_pos(int vwcb_pos) {
		this.vwcb_pos = vwcb_pos;
	}
	public int getVwcc_pos() {
		return vwcc_pos;
	}
	public void setVwcc_pos(int vwcc_pos) {
		this.vwcc_pos = vwcc_pos;
	}
	public int getVwcd_pos() {
		return vwcd_pos;
	}
	public void setVwcd_pos(int vwcd_pos) {
		this.vwcd_pos = vwcd_pos;
	}
	public int getUv_light_pos() {
		return uv_light_pos;
	}
	public void setUv_light_pos(int uv_light_pos) {
		this.uv_light_pos = uv_light_pos;
	}
	
	/**
	 * @return the temperatureInt_in_F_pos
	 */
	public int getTemperatureInt_in_F_pos() {
		return temperatureInt_in_F_pos;
	}
	/**
	 * @param index the temperatureInt_in_F_pos to set
	 */
	public void setTemperatureInt_in_F_pos(int index) {
		this.temperatureInt_in_F_pos = index;
	}
	/**
	 * @return the temperatureIR__in_F_pos
	 */
	public int getTemperatureIR_in_F_pos() {
		return temperatureIR_in_F_pos;
	}
	/**
	 * @param temperatureIR__in_F_pos the temperatureIR__in_F_pos to set
	 */
	public void setTemperatureIR_in_F_pos(int temperatureIR_in_F_pos) {
		this.temperatureIR_in_F_pos = temperatureIR_in_F_pos;
	}
	/**
	 * @return the temperatureInt_in_C_pos
	 */
	public int getTemperatureInt_in_C_pos() {
		return temperatureInt_in_C_pos;
	}
	/**
	 * @param temperatureInt_in_C_pos the temperatureInt_in_C_pos to set
	 */
	public void setTemperatureInt_in_C_pos(int temperatureInt_in_C_pos) {
		this.temperatureInt_in_C_pos = temperatureInt_in_C_pos;
	}
	/**
	 * @return the temperatureIR__in_C_pos
	 */
	public int getTemperatureIR__in_C_pos() {
		return temperatureIR__in_C_pos;
	}
	/**
	 * @param temperatureIR__in_C_pos the temperatureIR__in_C_pos to set
	 */
	public void setTemperatureIR_in_C_pos(int temperatureIR__in_C_pos) {
		this.temperatureIR__in_C_pos = temperatureIR__in_C_pos;
	}
	/**
	 * @return the wsolar_rad_pos
	 */
	public int getWsolar_rad_pos() {
		return wsolar_rad_pos;
	}
	/**
	 * @param wsolar_rad_pos the wsolar_rad_pos to set
	 */
	public void setWsolar_rad_pos(int wsolar_rad_pos) {
		this.wsolar_rad_pos = wsolar_rad_pos;
	}
	/**
	 * @return the barometer_pos
	 */
	public int getBarometer_pos() {
		return barometer_pos;
	}
	/**
	 * @param barometer_pos the barometer_pos to set
	 */
	public void setBarometer_pos(int barometer_pos) {
		this.barometer_pos = barometer_pos;
	}
	/**
	 * @return the barometerXR_pos
	 */
	public int getBarometerXR_pos() {
		return barometerXR_pos;
	}
	/**
	 * @param barometerXR_pos the barometerXR_pos to set
	 */
	public void setBarometerXR_pos(int barometerXR_pos) {
		this.barometerXR_pos = barometerXR_pos;
	}
	/**
	 * @return the milliAmp_pos
	 */
	public int getMilliAmp_pos() {
		return milliAmp_pos;
	}
	/**
	 * @param milliAmp_pos the milliAmp_pos to set
	 */
	public void setMilliAmp_pos(int milliAmp_pos) {
		this.milliAmp_pos = milliAmp_pos;
	}
	/**
	 * @return the wmilliAmp_pos
	 */
	public int getWmilliAmp_pos() {
		return wmilliAmp_pos;
	}
	/**
	 * @param wmilliAmp_pos the wmilliAmp_pos to set
	 */
	public void setWmilliAmp_pos(int wmilliAmp_pos) {
		this.wmilliAmp_pos = wmilliAmp_pos;
	}
	/**
	 * @return the lmilliAmp_pos
	 */
	public int getLmilliAmp_pos() {
		return lmilliAmp_pos;
	}
	/**
	 * @param lmilliAmp_pos the lmilliAmp_pos to set
	 */
	public void setLmilliAmp_pos(int lmilliAmp_pos) {
		this.lmilliAmp_pos = lmilliAmp_pos;
	}
	/**
	 * @return the rainfallIn_pos
	 */
	public int getRainfallIn_pos() {
		return rainfallIn_pos;
	}
	/**
	 * @param rainfallIn_pos the rainfallIn_pos to set
	 */
	public void setRainfallIn_pos(int rainfallIn_pos) {
		this.rainfallIn_pos = rainfallIn_pos;
	}
	/**
	 * @return the rainfallMM_pos
	 */
	public int getRainfallMM_pos() {
		return rainfallMM_pos;
	}
	/**
	 * @param rainfallMM_pos the rainfallMM_pos to set
	 */
	public void setRainfallMM_pos(int rainfallMM_pos) {
		this.rainfallMM_pos = rainfallMM_pos;
	}
	/**
	 * @return the wetness_pos
	 */
	public int getWetness_pos() {
		return wetness_pos;
	}
	/**
	 * @param wetness_pos the wetness_pos to set
	 */
	public void setWetness_pos(int wetness_pos) {
		this.wetness_pos = wetness_pos;
	}
	/**
	 * @return the par_pos
	 */
	public int getPar_pos() {
		return par_pos;
	}
	/**
	 * @param par_pos the par_pos to set
	 */
	public void setPar_pos(int par_pos) {
		this.par_pos = par_pos;
	}
	/**
	 * @return the wpar_pos
	 */
	public int getWpar_pos() {
		return wpar_pos;
	}
	/**
	 * @param wpar_pos the wpar_pos to set
	 */
	public void setWpar_pos(int wpar_pos) {
		this.wpar_pos = wpar_pos;
	}
	/**
	 * @return the watermark_pos
	 */
	public int getWatermark_pos() {
		return watermark_pos;
	}
	/**
	 * @param watermark_pos the watermark_pos to set
	 */
	public void setWatermark_pos(int watermark_pos) {
		this.watermark_pos = watermark_pos;
	}
	/**
	 * @return the watermarkA_pos
	 */
	public int getWatermarkA_pos() {
		return watermarkA_pos;
	}
	/**
	 * @param watermarkA_pos the watermarkA_pos to set
	 */
	public void setWatermarkA_pos(int watermarkA_pos) {
		this.watermarkA_pos = watermarkA_pos;
	}
	/**
	 * @return the watermarkB_pos
	 */
	public int getWatermarkB_pos() {
		return watermarkB_pos;
	}
	/**
	 * @param watermarkB_pos the watermarkB_pos to set
	 */
	public void setWatermarkB_pos(int watermarkB_pos) {
		this.watermarkB_pos = watermarkB_pos;
	}
	/**
	 * @return the watermarkC_pos
	 */
	public int getWatermarkC_pos() {
		return watermarkC_pos;
	}
	/**
	 * @param watermarkC_pos the watermarkC_pos to set
	 */
	public void setWatermarkC_pos(int watermarkC_pos) {
		this.watermarkC_pos = watermarkC_pos;
	}
	/**
	 * @return the watermarkD_pos
	 */
	public int getWatermarkD_pos() {
		return watermarkD_pos;
	}
	/**
	 * @param watermarkD_pos the watermarkD_pos to set
	 */
	public void setWatermarkD_pos(int watermarkD_pos) {
		this.watermarkD_pos = watermarkD_pos;
	}
	/**
	 * @return the lowTension_pos
	 */
	public int getLowTension_pos() {
		return lowTension_pos;
	}
	/**
	 * @param lowTension_pos the lowTension_pos to set
	 */
	public void setLowTension_pos(int lowTension_pos) {
		this.lowTension_pos = lowTension_pos;
	}
	/**
	 * @return the co2_pos
	 */
	public int getCo2_pos() {
		return co2_pos;
	}
	/**
	 * @param co2_pos the co2_pos to set
	 */
	public void setCo2_pos(int co2_pos) {
		this.co2_pos = co2_pos;
	}
	/**
	 * @return the state_pos
	 */
	public int getState_pos() {
		return state_pos;
	}
	/**
	 * @param state_pos the state_pos to set
	 */
	public void setState_pos(int state_pos) {
		this.state_pos = state_pos;
	}
	/**
	 * @return the ec_pos
	 */
	public int getEc_pos() {
		return ec_pos;
	}
	/**
	 * @param ec_pos the ec_pos to set
	 */
	public void setEc_pos(int ec_pos) {
		this.ec_pos = ec_pos;
	}
	/**
	 * @return the echo25_pos
	 */
	public int getEcho25_pos() {
		return echo25_pos;
	}
	/**
	 * @param echo25_pos the echo25_pos to set
	 */
	public void setEcho25_pos(int echo25_pos) {
		this.echo25_pos = echo25_pos;
	}
	/**
	 * @return the echo5_pos
	 */
	public int getEcho5_pos() {
		return echo5_pos;
	}
	/**
	 * @param echo5_pos the echo5_pos to set
	 */
	public void setEcho5_pos(int echo5_pos) {
		this.echo5_pos = echo5_pos;
	}
	/**
	 * @return the echo10_pos
	 */
	public int getEcho10_pos() {
		return echo10_pos;
	}
	/**
	 * @param echo10_pos the echo10_pos to set
	 */
	public void setEcho10_pos(int echo10_pos) {
		this.echo10_pos = echo10_pos;
	}
	/**
	 * @return the sm100_pos
	 */
	public int getSm100_pos() {
		return sm100_pos;
	}
	/**
	 * @param sm100_pos the sm100_pos to set
	 */
	public void setSm100_pos(int sm100_pos) {
		this.sm100_pos = sm100_pos;
	}
	/**
	 * @return the none_pos
	 */
	public int getNone_pos() {
		return none_pos;
	}
	/**
	 * @param none_pos the none_pos to set
	 */
	public void setNone_pos(int none_pos) {
		this.none_pos = none_pos;
	}
	/**
	 * @return the raw_pos
	 */
	public int getRaw_pos() {
		return raw_pos;
	}
	/**
	 * @param raw_pos the raw_pos to set
	 */
	public void setRaw_pos(int raw_pos) {
		this.raw_pos = raw_pos;
	}
	/**
	 * @return the voltage_pos
	 */
	public int getVoltage_pos() {
		return voltage_pos;
	}
	/**
	 * @param voltage_pos the voltage_pos to set
	 */
	public void setVoltage_pos(int voltage_pos) {
		this.voltage_pos = voltage_pos;
	}
	/**
	 * @return the battery_pos
	 */
	public int getBattery_pos() {
		return battery_pos;
	}
	/**
	 * @param battery_pos the battery_pos to set
	 */
	public void setBattery_pos(int battery_pos) {
		this.battery_pos = battery_pos;
	}
	/**
	 * @return the batteryPct_pos
	 */
	public int getBatteryPct_pos() {
		return batteryPct_pos;
	}
	/**
	 * @param batteryPct_pos the batteryPct_pos to set
	 */
	public void setBatteryPct_pos(int batteryPct_pos) {
		this.batteryPct_pos = batteryPct_pos;
	}
	/**
	 * @return the wvoltage_pos
	 */
	public int getWvoltage_pos() {
		return wvoltage_pos;
	}
	/**
	 * @param wvoltage_pos the wvoltage_pos to set
	 */
	public void setWvoltage_pos(int wvoltage_pos) {
		this.wvoltage_pos = wvoltage_pos;
	}
	/**
	 * @return the dew_point_in_C_pos
	 */
	public int getDew_point_in_C_pos() {
		return dew_point_in_C_pos;
	}
	/**
	 * @param dew_point_in_C_pos the dew_point_in_C_pos to set
	 */
	public void setDew_point_in_C_pos(int dew_point_in_C_pos) {
		this.dew_point_in_C_pos = dew_point_in_C_pos;
	}
	/**
	 * @return the wind_speed_in_kmh_pos
	 */
	public int getWind_speed_kmh_pos() {
		return wind_speed_kmh_pos;
	}
	/**
	 * @param wind_speed_in_kmh_pos the wind_speed_in_kmh_pos to set
	 */
	public void setWind_speed_in_kmh_pos(int wind_speed_in_kmh_pos) {
		this.wind_speed_kmh_pos = wind_speed_in_kmh_pos;
	}
	/**
	 * @return the wind_gust_in_kmh_pos
	 */
	public int getWind_gust_kmh_pos() {
		return wind_gust_kmh_pos;
	}
	/**
	 * @param temperatureIR__in_C_pos the temperatureIR__in_C_pos to set
	 */
	public void setTemperatureIR__in_C_pos(int temperatureIR__in_C_pos) {
		this.temperatureIR__in_C_pos = temperatureIR__in_C_pos;
	}
	/**
	 * @return the dew_point_in_F_pos
	 */
	public int getDew_point_in_F_pos() {
		return dew_point_in_F_pos;
	}
	/**
	 * @param dew_point_in_F_pos the dew_point_in_F_pos to set
	 */
	public void setDew_point_in_F_pos(int dew_point_in_F_pos) {
		this.dew_point_in_F_pos = dew_point_in_F_pos;
	}
	/**
	 * @return the wind_speed_mph_pos
	 */
	public int getWind_speed_mph_pos() {
		return wind_speed_mph_pos;
	}
	/**
	 * @param wind_speed_mph_pos the wind_speed_mph_pos to set
	 */
	public void setWind_speed_mph_pos(int wind_speed_mph_pos) {
		this.wind_speed_mph_pos = wind_speed_mph_pos;
	}
	/**
	 * @return the wind_gust_mph_pos
	 */
	public int getWind_gust_mph_pos() {
		return wind_gust_mph_pos;
	}
	/**
	 * @param wind_gust_mph_pos the wind_gust_mph_pos to set
	 */
	public void setWind_gust_mph_pos(int wind_gust_mph_pos) {
		this.wind_gust_mph_pos = wind_gust_mph_pos;
	}
	/**
	 * @param wind_speed_kmh_pos the wind_speed_kmh_pos to set
	 */
	public void setWind_speed_kmh_pos(int wind_speed_kmh_pos) {
		this.wind_speed_kmh_pos = wind_speed_kmh_pos;
	}
	/**
	 * @param wind_gust_kmh_pos the wind_gust_kmh_pos to set
	 */
	public void setWind_gust_kmh_pos(int wind_gust_kmh_pos) {
		this.wind_gust_kmh_pos = wind_gust_kmh_pos;
	}
	
}

