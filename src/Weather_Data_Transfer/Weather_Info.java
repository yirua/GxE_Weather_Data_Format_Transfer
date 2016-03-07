package Weather_Data_Transfer;
/*
 * Station_id VARCHAR(10) PRIMARY KEY NOT NULL,
Day VARCHAR(2),
Month VARCHAR(2),
Year VARCHAR(4),
Julian_Date VARCHAR(255),
Time VARCHAR(255),
Temp_F VARCHAR(255),
TempA_F VARCHAR(255), 
TempB_F VARCHAR(255),
TempC_F VARCHAR(255),
TempD_F VARCHAR(255),
TempE_F VARCHAR(255),
TempF_F VARCHAR(255),
Temp_C VARCHAR(255),
TempA_C VARCHAR(255), 
TempB_C VARCHAR(255),
TempC_C VARCHAR(255),
TempD_C VARCHAR(255),
TempE_C VARCHAR(255),
TempF_C VARCHAR(255),
EC_SMEC300 VARCHAR(255),
Soil_Moist_VWC_A VARCHAR(255),
Soil_Moist_VWC_B VARCHAR(255),
Soil_Moist_VWC_C VARCHAR(255),
Soil_Moist_VWC_D VARCHAR(255),
Rh VARCHAR(255),
Dew_Point VARCHAR(255),
Solar_Radiation VARCHAR(255),
Rain_Fall VARCHAR(255),
Wind_Direction VARCHAR(255),
Wind_Speed VARCHAR(255),
Wind_Gust VARCHAR(255)
);
//? 
alter table raw_file add constraint RAW_FILE_RSID_UQ unique (rs_id);
alter table raw_file add constraint RAW_FILE_CH_INDX_UQ unique (chromosome,_index);
 */
public class Weather_Info {
	//private String station_state; //the state name of station, such as Illinois
	private String station_id;//integers to illustrate the weather station
    private String temperatureA_in_F; //TEMP A IN F
    private String temperatureB_in_F;//TEMP B IN F
    private String temperatureC_in_F; //TEMP C IN F
    private String temperatureD_in_F; //TEMP D IN F
    private String temperatureE_in_F; //TEMP E IN F
    private String temperatureF_in_F; //TEMP F IN F
    private String temperature_in_F; //TEMP IN F*
    
    private String temperatureA_in_C; //TEMP A IN c
    private String temperatureB_in_C;//TEMP B IN C
    private String temperatureC_in_C; //TEMP C IN C
    private String temperatureD_in_C; //TEMP D IN C
    private String temperatureE_in_C; //TEMP E IN C
    private String temperatureF_in_C; //TEMP F IN C
    private String temperature_in_C; //TEMP IN C*
    
    private String solar_rad;
    private String rh;	//HMD RH(%)
    private String rainfall;
    private String wind_dir;
    private String wind_gust;
    private String wind_speed;
    private String dew_point;
    
    private String ecbc;//EC-SMEC300
    private String vwca; //soil_moist_vmc_a
    private String vwcb; //soil_moist_vmc_b
    private String vwcc; //soil_moist_vmc_c
    private String vwcd; //soil_moist_vmc_d
    
    private String time;//the clock time
    private String year;
    private String month;
    private String day;
    private String julian_date; //
	private String uv_light;
    
    
    public String getStationId() {
        return station_id;
    }
    public void setStationId(String station_id) {
        this.station_id= station_id;
    }
    //stationState_
/*    public String getStationState() {
        return station_state;
    }
    public void setstation_state(String station_state) {
        this.station_state= station_state;
    }
*/
    //TMP IN F to set and get
    //TEMP A
    public String getTMPA() {
        return temperatureA_in_F;
    }
    public void setTMPA(String temperatureA_F) {
        this.temperatureA_in_F = temperatureA_F;
    }
    //TEMPB
    public String getTMPB() {
        return temperatureB_in_F;
    }
    public void setTMPB(String temperatureB_F) {
        this.temperatureB_in_F = temperatureB_F;
    }
    public String getTMPC() {
        return temperatureC_in_F;
    }
    public void setTMPC(String temperatureC_F) {
        this.temperatureC_in_F = temperatureC_F;
    }
    // TMPD
    public String getTMPD() {
        return temperatureD_in_F;
    }
    public void setTMPD(String temperatureD_F) {
        this.temperatureD_in_F = temperatureD_F;
    }
    //TMPE
    public void setTMPE(String temperatureE_in_F) {
        this.temperatureE_in_F = temperatureE_in_F;
    }
    public String getTMPE() {
        return temperatureE_in_F;
    }
    //TMPF
    public String getTMPF() {
        return temperatureF_in_F;
    }
    public void setTMPF(String temperatureF_in_F) {
        this.temperatureF_in_F = temperatureF_in_F;
    }
    //TMP in *F
    public String getTMP() {
        return temperature_in_F;
    }
    public void setTMP(String temperature_F) {
        this.temperature_in_F = temperature_F;
    }
    
    //TMP IN F to set and get
    //TEMP A
    public String getTMPA_C() {
        return temperatureA_in_C;
    }
    public void setTMPA_C(String temperatureA_C) {
        this.temperatureA_in_C = temperatureA_C;
    }
    //TEMPB IN C
    public String getTMPB_C() {
        return temperatureB_in_C;
    }
    public void setTMPB_C(String temperatureB_C) {
        this.temperatureB_in_C = temperatureB_C;
    }
    //TEMPC IN C
    public String getTMPC_C() {
        return temperatureC_in_C;
    }
    public void setTMPC_C(String temperatureC_C) {
        this.temperatureC_in_C = temperatureC_C;
    }
    // TMPD in C
    public String getTMPD_C() {
        return temperatureD_in_C;
    }
    public void setTMPD_C(String temperatureD_C) {
        this.temperatureD_in_C = temperatureD_C;
    }
    //TMPE in C
    public void setTMPE_C(String temperatureE_C) {
        this.temperatureE_in_C = temperatureE_C;
    }
    public String getTMPE_C() {
        return temperatureE_in_C;
    }
    //TMPF in C
    public String getTMPF_C() {
        return temperatureF_in_C;
    }
    public void setTMPF_C(String temperatureF_in_C) {
        this.temperatureF_in_C = temperatureF_in_C;
    }
    //TMP_C ; TMP IN *C
    public String getTMP_C() {
        return temperature_in_C;
    }
    public void setTMP_C(String temperature_C) {
        this.temperature_in_C = temperature_C;
    }
    
   //REST Weather Conditions 
    public String getSolar_Rad() {
        return solar_rad;
    }
    public void setSolar_Rad(String solar_rad) {
        this.solar_rad = solar_rad;
    }
    //HMD RH
    public void setRH(String rh) {
        this.rh = rh;
    }
    public String getRH() {
        return rh;
    }
    // RAINFALL
    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }
    public String getRainfall() {
        return rainfall;
    }
    //WIND DIR
    public void setWind_Dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }
    public String getWind_Dir() {
        return wind_dir;
    }
    //WIND GUST
    public void setWind_Gust(String wind_gust) {
        this.wind_gust = wind_gust;
    }
    public String getWind_Gust() {
        return wind_gust;
    }
    //WIND SPEED
    public void setWind_Speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }
    public String getWind_Speed() {
        return wind_speed;
    }
    //DEW POINT
    public void setDew_Point(String dew_point) {
        this.dew_point = dew_point;
    }
    public String getDew_Point() {
        return dew_point;
    }
    
    //ECBC
    public void setECBC(String ecbc) {
        this.ecbc = ecbc;
    }
    public String getECBC() {
        return ecbc;
    }
    //VWCA 
    public void setVWCA(String vwca) {
        this.vwca = vwca;
    }
    public String getVWCA() {
        return vwca;
    }
    //VWCB
  
    public void setVWCB(String vwcb) {
        this.vwcb = vwcb;
    }
    public String getVWCB() {
        return vwcb;
    }
    
  //VWCC
    
    public void setVWCC(String vwcc) {
        this.vwcc = vwcc;
    }
    public String getVWCC() {
        return vwcc;
    }
    //VWCD
    public void setVWCD(String vwcd) {
        this.vwcd = vwcd;
    }
    public String getVWCD() {
        return vwcd;
    }
    
    
    /**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	//toString()
    @Override
    public String toString() {
        return "Weather_Info [station_id=" + station_id + ", rs_id=" + temperatureA_in_F + ", solar_rad="
                + solar_rad + ", humidity=" + rh + ", rainfall=" + rainfall + "]";
    }
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return the julian_date
	 */
	public String getJulian_Date() {
		return julian_date;
	}
	/**
	 * @param julian_date the julian_date to set
	 */
	public void setJulian_Date(String julian_date) {
		this.julian_date = julian_date;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	public void setUVL(String uv_light) {
		// TODO Auto-generated method stub
		this.uv_light=uv_light;
	}  
	public String getUVL(){
		return uv_light;
	}
}
