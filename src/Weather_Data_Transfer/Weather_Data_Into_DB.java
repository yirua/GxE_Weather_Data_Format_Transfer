package Weather_Data_Transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//Db connection
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import Weather_Data_Transfer.Weather_Data_Into_DB;
/**
 * this will try to connect the postgresql database for dumping data into it.
 * @author yiweisun
 *
 */
public class Weather_Data_Into_DB {
	File fin = new File("res/RSID_genotype.data");
	private static Weather_Data_Into_DB instance = new Weather_Data_Into_DB();
//	private static Weather_Data_Into_DB instance;

	public static String url = "jdbc:postgresql://localhost/gxe_weather";
	public static Properties props = new Properties();
	
	
	 public Weather_Data_Into_DB() {
		 	
		
	   
	 }
private Connection createConnection() {
        //Step 3: Establish Java MySQL connection
    	 Connection connection = null;
         try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
               .getConnection("jdbc:postgresql://localhost:5432/gxe_weather",
               "yiweisun", "");
         } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
         }
         System.out.println("Opened database successfully");
      	
    return connection;
}  
/*
 * try to delete all records
 */
public void delete_All_Records(Connection connected){
 
 try {
		Statement stmt = connected.createStatement();
		String delete_table="TRUNCATE TABLE gxe_weather";
		//String delete_table="TRUNCATE table raw_file";
		System.out.println("Deleting all records from table gxe_weather.... ");
	    stmt.executeUpdate(delete_table);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}

public void drop_Then_Create_Table(Connection connected){
	try {
		Statement stmt = connected.createStatement();
		String drop="drop TABLE gxe_weather";
		//String delete_table="TRUNCATE table gxe_weather";
		System.out.println("Dropping table gxe_weather.... ");
	    stmt.executeUpdate(drop);
	    String create_table="CREATE TABLE GXE_Weather(Record_id SERIAL NOT NULL,Station_id VARCHAR(6),Day VARCHAR(2),Month VARCHAR(2),Year VARCHAR(4),Julian_Date VARCHAR(3),"+
"Time VARCHAR(8),Temp_F VARCHAR(6),TempA_F VARCHAR(6), TempB_F VARCHAR(6),TempC_F VARCHAR(6),TempD_F VARCHAR(6),TempE_F VARCHAR(6),TempF_F VARCHAR(6),"+
"Temp_C VARCHAR(6),TempA_C VARCHAR(6), TempB_C VARCHAR(6),TempC_C VARCHAR(6),TempD_C VARCHAR(6),TempE_C VARCHAR(6),TempF_C VARCHAR(6),"+
"EC_SMEC300 VARCHAR(8),Soil_Moist_VWC_A VARCHAR(8),Soil_Moist_VWC_B VARCHAR(8),Soil_Moist_VWC_C VARCHAR(8),"+
"Soil_Moist_VWC_D VARCHAR(8),Rh VARCHAR(8),Dew_Point VARCHAR(8),Solar_Radiation VARCHAR(8),Rain_Fall VARCHAR(8),"+
"Wind_Direction VARCHAR(8),Wind_Speed VARCHAR(8),Wind_Gust VARCHAR(8),PRIMARY KEY(Record_id))";
	    stmt.executeQuery(create_table);
	    String table_constraint="alter table gxe_weather add constraint GXE_WEATHER_STATION_DATE_TIME unique(station_id,day, month, year,time)";
	    stmt.executeQuery(table_constraint);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		//e1.printStackTrace();
		System.out.println(e1.getMessage());
	}
}
public static Connection getConnection() {
    return instance.createConnection();
}

public int insert_one_object_into_db(Connection connected, ArrayList<Weather_Info> infos){
	
	for (Weather_Info info: infos){
			try {
				String insert_into_table="INSERT INTO gxe_weather(station_id,day,month,year,julian_date,time,temp_f,tempa_f,tempb_f,tempc_f,tempd_f,tempe_f,tempf_f,temp_c,tempa_c,tempb_c,tempc_c,tempd_c,tempe_c,tempf_c,ec_smec300,soil_moist_vwc_a,soil_moist_vwc_b,soil_moist_vwc_c,soil_moist_vwc_d,rh,dew_point,solar_radiation,rain_fall,wind_direction,wind_speed,wind_gust)"+
										"VALUES"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement pstmt = null;
				
			//	stmt = connected.createStatement();
					
					
			//		String sql = "INSERT INTO raw_file(rs_id,rs_value, chromosome, _index) " +
			//                   "VALUES" +"(?, ?, ?, ?)";
		
					pstmt=connected.prepareStatement(insert_into_table);
					pstmt.setString(1, info.getStationId());
					
					pstmt.setString(2, info.getDay());
					pstmt.setString(3, info.getMonth());
					pstmt.setString(4, info.getYear());
					pstmt.setString(5, info.getJulian_Date());
					
					pstmt.setString(6, info.getTime());
					//tmp in F
					pstmt.setString(7, info.getTMP());
					pstmt.setString(8, info.getTMPA());
					pstmt.setString(9, info.getTMPB());
					
					pstmt.setString(10, info.getTMPC());
					pstmt.setString(11, info.getTMPD());
					pstmt.setString(12, info.getTMPE());
					pstmt.setString(13, info.getTMPF());
					//tmp in c
					pstmt.setString(14, info.getTMP_C());
					pstmt.setString(15, info.getTMPA_C());
					pstmt.setString(16, info.getTMPB_C());
					pstmt.setString(17, info.getTMPC_C());
					
					pstmt.setString(18, info.getTMPD_C());
					pstmt.setString(19, info.getTMPE_C());
					pstmt.setString(20, info.getTMPF_C());
					//ecbc
					pstmt.setString(21, info.getECBC());
					
					pstmt.setString(22, info.getVWCA());
					pstmt.setString(23, info.getVWCB());
					pstmt.setString(24, info.getVWCC());
					pstmt.setString(25, info.getVWCD());
					
					pstmt.setString(26, info.getRH());
					pstmt.setString(27, info.getDew_Point());
					pstmt.setString(28, info.getSolar_Rad());
					
					pstmt.setString(29, info.getRainfall());
					pstmt.setString(30, info.getWind_Dir());
					pstmt.setString(31, info.getWind_Speed());
					pstmt.setString(32, info.getWind_Gust());
					
					ResultSet results=pstmt.executeQuery();
					//pstmt.executeUpdate();
					
					
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
			//only test one object
			
	}//for loop for the ArrayList of Weather_Info
	//close the connection to DB
	System.out.println("");
	try {
		connected.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return infos.size();
}
}
