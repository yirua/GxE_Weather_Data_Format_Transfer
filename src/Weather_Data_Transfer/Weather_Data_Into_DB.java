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
import java.util.Set;

import Weather_Data_Transfer.Weather_Data_Into_DB;
/**
 * this will try to connect the Postgresql local or remote database for dumping data into it, it also includes create, truncate, drop/reimport and delete methods to choose.
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
private Connection createConnection_Local() {
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
         System.out.println("Opened local database //localhost:5432/gxe_weather successfully");
      	
    return connection;
}  

private Connection createConnection_Remote() {
    //Step 3: Establish Java MySQL connection
	 Connection connection = null;
     try {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager
           .getConnection("jdbc:postgresql://129.186.85.29:5432/gxe_weather",
           "GenE", "Field$");
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }
     System.out.println("Opened remote database //129.186.85.29:5432/gxe_weather successfully");
  	
return connection;
}  
/*
 * try to delete all records
 */
public void delete_All_Records(Connection connected, String table_name){
 
 try {
		Statement stmt = connected.createStatement();
		String delete_table="TRUNCATE TABLE "+table_name;
		//String delete_table="TRUNCATE table raw_file";
		System.out.println("Deleting all records from table "+table_name+".... ");
	    stmt.executeUpdate(delete_table);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}

public void Create_Table_Gxe_Weather(Connection connected, String table_name){

	try {
		Statement stmt = connected.createStatement();
		
		String create_table="CREATE TABLE "+ table_name+"(Record_id SERIAL NOT NULL,Station_id INT,Day INT,Month INT,Year INT,Julian_Date INT,"+
				"Time VARCHAR(8),Temp_F VARCHAR(6),TempA_F VARCHAR(6), TempB_F VARCHAR(6),TempC_F VARCHAR(6),TempD_F VARCHAR(6),TempE_F VARCHAR(6),TempF_F VARCHAR(6),"+
				"Temp_C VARCHAR(6),TempA_C VARCHAR(6), TempB_C VARCHAR(6),TempC_C VARCHAR(6),TempD_C VARCHAR(6),TempE_C VARCHAR(6),TempF_C VARCHAR(6),"+
				"EC_SMEC300 VARCHAR(8),Soil_Moist_VWC_A VARCHAR(8),Soil_Moist_VWC_B VARCHAR(8),Soil_Moist_VWC_C VARCHAR(8),"+
				"Soil_Moist_VWC_D VARCHAR(8),Rh VARCHAR(8),Dew_Point_f VARCHAR(8),Dew_Point_c VARCHAR(8),Solar_Radiation VARCHAR(8),RainFall_In VARCHAR(8),RainFall_Mm VARCHAR(8),"+
				"Wind_Direction VARCHAR(8),Wind_Speed_Mph VARCHAR(8),Wind_Gust_Mph VARCHAR(8),Wind_Speed_Kmh VARCHAR(8),Wind_Gust_Kmh VARCHAR(8),uv_light VARCHAR(8),CO2 VARCHAR(8),PRIMARY KEY(Record_id))";
	    stmt.executeQuery(create_table);
	 //   String table_constraint="alter table gxe_weather add constraint GXE_WEATHER_STATION_DATE_TIME unique(station_id,day, month, year,time)";
	 //   stmt.executeQuery(table_constraint);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		//e1.printStackTrace();
		//System.out.println(e1.getMessage());
		System.out.println("Created one table: "+table_name+"....");
	}
	
}
public void drop_Then_Create_Table(Connection connected, String table_name){
	try {
		Statement stmt = connected.createStatement();
		String drop="drop TABLE "+table_name;
		//String delete_table="TRUNCATE table gxe_weather";
		System.out.println("Dropping table "+ table_name+".... ");
	    stmt.executeUpdate(drop);
	    String create_table="CREATE TABLE "+table_name+"(Record_id SERIAL NOT NULL,Station_id INT,Day INT,Month INT,Year INT,Julian_Date INT,"+
	    		"Time VARCHAR(8),Temp_F VARCHAR(6),TempA_F VARCHAR(6), TempB_F VARCHAR(6),TempC_F VARCHAR(6),TempD_F VARCHAR(6),TempE_F VARCHAR(6),TempF_F VARCHAR(6),"+
	    		"Temp_C VARCHAR(6),TempA_C VARCHAR(6), TempB_C VARCHAR(6),TempC_C VARCHAR(6),TempD_C VARCHAR(6),TempE_C VARCHAR(6),TempF_C VARCHAR(6),"+
	    		"EC_SMEC300 VARCHAR(8),Soil_Moist_VWC_A VARCHAR(8),Soil_Moist_VWC_B VARCHAR(8),Soil_Moist_VWC_C VARCHAR(8),"+
	    		"Soil_Moist_VWC_D VARCHAR(8),Rh VARCHAR(8),Dew_Point_f VARCHAR(8),Dew_Point_c VARCHAR(8),Solar_Radiation VARCHAR(8),RainFall_In VARCHAR(8),RainFall_Mm VARCHAR(8),"+
	    		"Wind_Direction VARCHAR(8),Wind_Speed_Mph VARCHAR(8),Wind_Gust_Mph VARCHAR(8),Wind_Speed_Kmh VARCHAR(8),Wind_Gust_Kmh VARCHAR(8),uv_light VARCHAR(8),CO2 VARCHAR(8),PRIMARY KEY(Record_id))";
	    stmt.executeQuery(create_table);
	    //The constraints to make the station_id and time stamp unique. 
	   // String table_constraint="alter table gxe_weather add constraint GXE_WEATHER_STATION_DATE_TIME unique(station_id,day, month, year,time)";
	   // stmt.executeQuery(table_constraint);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		
		//System.out.println(e1.getMessage());
		System.out.println("Created one table "+table_name+"....");
	}
}

public void drop_Table(Connection connected, String table_name){
	try {
		Statement stmt = connected.createStatement();
		String drop="drop TABLE "+table_name;
		//String delete_table="TRUNCATE table gxe_weather";
		System.out.println("Dropping table "+ table_name+".... ");
	    stmt.executeUpdate(drop);
	    
	    //The constraints to make the station_id and time stamp unique. 
	   // String table_constraint="alter table gxe_weather add constraint GXE_WEATHER_STATION_DATE_TIME unique(station_id,day, month, year,time)";
	   // stmt.executeQuery(table_constraint);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		
		//System.out.println(e1.getMessage());
		System.out.println("Dropped one table "+table_name+"....");
	}
}
public ArrayList<String> get_all_table_names(Connection connected, String name_string){
	Statement stmt=null;
	String name_results="";
	String schema_results="";
	ArrayList<String> table_names= new ArrayList<String>();
	try {
		stmt = connected.createStatement();
		String get_all_table_names="SELECT table_schema,table_name FROM information_schema.tables ORDER BY table_schema,table_name; ";
		//String delete_table="TRUNCATE table raw_file";
		
	    ResultSet rs=stmt.executeQuery(get_all_table_names);
	    while(rs.next()){
	    	name_results=rs.getString("table_name");
	    	schema_results=rs.getString("table_schema");
	    	if(name_results.contains(name_string)){
	    		table_names.add(name_results);
	    	}
	    	
	    }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		
	} finally{
		if (stmt != null ){
			try {
				stmt.close();				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return table_names;
	
}
/*
 * get localhost connection
 */
public static Connection getConnection_Local() {
    return instance.createConnection_Local();
}

/*
 * get the remote connection
 */
public static Connection getConnection_Remote() {
    return instance.createConnection_Remote();
}

/*
 * This one will input the ArrayList of Weather_Info into a connected DB.
 */
public int insert_one_object_into_table(Connection connected, ArrayList<Weather_Info> infos, String table_name){
	
	for (Weather_Info info: infos){
			try {
				String insert_into_table="INSERT INTO "+table_name+"(station_id,day,month,year,julian_date,time,temp_f,tempa_f,tempb_f,tempc_f,tempd_f,tempe_f,tempf_f,temp_c,tempa_c,tempb_c,tempc_c,tempd_c,tempe_c,tempf_c,ec_smec300,soil_moist_vwc_a,soil_moist_vwc_b,soil_moist_vwc_c,soil_moist_vwc_d,rh,dew_point_f,dew_point_c,solar_radiation,rainfall_in,rainfall_mm,wind_direction,wind_speed_mph,wind_gust_mph,wind_speed_kmh,wind_gust_kmh,uv_light,co2)"+
										"VALUES"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement pstmt = null;
				
			//	stmt = connected.createStatement();
					
					
			//		String sql = "INSERT INTO raw_file(rs_id,rs_value, chromosome, _index) " +
			//                   "VALUES" +"(?, ?, ?, ?)";
		
					pstmt=connected.prepareStatement(insert_into_table);
					pstmt.setInt(1, info.getStationId());
					
					pstmt.setInt(2, info.getDay());
					pstmt.setInt(3, info.getMonth());
					pstmt.setInt(4, info.getYear());
					pstmt.setInt(5, info.getJulian_Date());
					
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
					pstmt.setString(27, info.getDew_point_F());
					pstmt.setString(28, info.getDew_point_C());
					pstmt.setString(29, info.getSolar_Rad());
					//rainfall
					pstmt.setString(30, info.getRainfallIn());
					pstmt.setString(31,info.getRainfallMM());
					
					//Wind Dir, speed, gust
					pstmt.setString(32, info.getWind_Dir());
					pstmt.setString(33, info.getWind_speed_mph());
					pstmt.setString(34, info.getWind_gust_mph());
					
					
					pstmt.setString(35, info.getWind_speed_kmh());
					pstmt.setString(36, info.getWind_gust_kmh());
					pstmt.setString(37, info.getUv_light());
					pstmt.setString(38, info.getCo2());
					
					
					
					ResultSet results=pstmt.executeQuery();
					//pstmt.executeUpdate();
					
					
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				//System.out.println(e1.getMessage());
				System.out.println("Added one record....");
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

public int Insert_Into_DB_By_Set(Connection connected, Set<Weather_Info> infos){

	
	for (Weather_Info info: infos){
			try {
				String insert_into_table="INSERT INTO gxe_weather(station_id,day,month,year,julian_date,time,temp_f,tempa_f,tempb_f,tempc_f,tempd_f,tempe_f,tempf_f,temp_c,tempa_c,tempb_c,tempc_c,tempd_c,tempe_c,tempf_c,ec_smec300,soil_moist_vwc_a,soil_moist_vwc_b,soil_moist_vwc_c,soil_moist_vwc_d,rh,dew_point_f,dew_point_c,solar_radiation,rainfall_in,rainfall_mm,wind_direction,wind_speed_mph,wind_gust_mph,wind_speed_kmh,wind_gust_kmh,co2)"+
										"VALUES"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement pstmt = null;
				
			//	stmt = connected.createStatement();
					
					
			//		String sql = "INSERT INTO raw_file(rs_id,rs_value, chromosome, _index) " +
			//                   "VALUES" +"(?, ?, ?, ?)";
		
					pstmt=connected.prepareStatement(insert_into_table);
					pstmt.setInt(1, info.getStationId());
					
					pstmt.setInt(2, info.getDay());
					pstmt.setInt(3, info.getMonth());
					pstmt.setInt(4, info.getYear());
					pstmt.setInt(5, info.getJulian_Date());
					
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
					pstmt.setString(27, info.getDew_point_F());
					pstmt.setString(28, info.getDew_point_C());
					pstmt.setString(29, info.getSolar_Rad());
					
					pstmt.setString(30, info.getRainfall_in());
					pstmt.setString(31, info.getRainfallMM());
					pstmt.setString(32, info.getWind_Dir());
					pstmt.setString(33, info.getWind_speed_mph());
					pstmt.setString(34, info.getWind_gust_mph());
					pstmt.setString(35, info.getWind_speed_kmh());
					pstmt.setString(36, info.getWind_gust_kmh());
					pstmt.setString(37, info.getCo2());
					
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
