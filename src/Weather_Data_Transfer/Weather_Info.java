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
    
    
    
    
    private String uv_light;//UVL
    private String solar_rad;
   
    
    
    private String rh;	//HMD RH(%)
    private String rainfall;
  
	
    // Wind 
    private String wind_dir;
    private String wind_gust;
    private String wind_speed;
    
    // Dew Point
    private String dew_point; //TMP ? 
    
    private String ecbc;//EC-SMEC300
   
    
    private String vwca; //soil_moist_vwc_a
    private String vwcb; //soil_moist_vwc_b
    private String vwcc; //soil_moist_vwc_c
    private String vwcd; //soil_moist_vwc_d
   // private String smec300; //soil_moist_vwc_triple_sensor
    
    private String time;//the clock time
    private String year;
    private String month;
    private String day;
    private String julian_date; //
	
   //////////////////////new strings 
    
    private String temperatureInt_in_F; //TEMP interval IN F
    private String temperatureIR__in_F; //TEMP IR IN F
    
    private String temperatureInt_in_C;  //TEMP INTERNAL in C
    private String temperatureIR__in_C; //TEMP IR IN C
    
    
    private String wsolar_rad;//WSolar_rad
    private String barometer; //BAR
    private String barometerXR; //BARXR
    private String milliAmp; //MAM
    private String wmilliAmp; //WMAM
    private String lmilliAmp; //LMAM
    
    
    private String rainfallIn;
    private String rainfallMM; //
    
    private String wetness; //WET
	private String par; //PAR
	private String wpar; //WPAR
	
	private String watermark; //SMS
	private String watermarkA; //SMSA
	private String watermarkB; //SMSB
	private String watermarkC; //SMSC
	private String watermarkD; //SMSD
	private String lowTension; //SMSLT
	private String co2;//CO2
	
	
	//STATE
	private String state;//STA
    private String ec; //ECB (Triple Sensor)
    
    private String echo25;//soil_moist_vwc25
    private String echo5; //soil_moist_vwc5
    private String echo10; //soil_moist_vwc10
    private String sm100; //soil_moist_vwc100 VWC
    
    private String none;
    private String raw; //RAW
    private String voltage; //VLT
    private String battery; //VLT_BAT
    
    private String batteryPct;//BTL
    private String wvoltage; //WVLT
    
 //////////////////////////new strings  
	
    
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
	/**
	 * @return the none
	 */
	public String getNone() {
		return none;
	}
	/**
	 * @param none the none to set
	 */
	public void setNone(String none) {
		this.none = none;
	}
	/**
	 * @return the raw
	 */
	public String getRaw() {
		return raw;
	}
	/**
	 * @param raw the raw to set
	 */
	public void setRaw(String raw) {
		this.raw = raw;
	}
	/**
	 * @return the voltage
	 */
	public String getVoltage() {
		return voltage;
	}
	/**
	 * @param voltage the voltage to set
	 */
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	 public String getTemperatureInt_in_F() {
			return temperatureInt_in_F;
		}
	public void setTemperatureInt_in_F(String temperatureInt_in_F) {
			this.temperatureInt_in_F = temperatureInt_in_F;
	}
	public String getTemperatureInt_in_C() {
			return temperatureInt_in_C;
	}
	public void setTemperatureInt_in_C(String temperatureInt_in_C) {
			this.temperatureInt_in_C = temperatureInt_in_C;
	}
	public String getTemperatureIR__in_F() {
		return temperatureIR__in_F;
	}
	public void setTemperatureIR__in_F(String temperatureIR__in_F) {
		this.temperatureIR__in_F = temperatureIR__in_F;
	}
	public String getTemperatureIR__in_C() {
		return temperatureIR__in_C;
	}
	public void setTemperatureIR__in_C(String temperatureIR__in_C) {
		this.temperatureIR__in_C = temperatureIR__in_C;
	}
	public String getUv_light() {
		return uv_light;
	}
	public void setUv_light(String uv_light) {
		this.uv_light = uv_light;
	}
	public String getWsolar_rad() {
		return wsolar_rad;
	}
	public void setWsolar_rad(String wsolar_rad) {
		this.wsolar_rad = wsolar_rad;
	}
	public String getBarometer() {
		return barometer;
	}
	public void setBarometer(String barometer) {
		this.barometer = barometer;
	}
	public String getBarometerXR() {
		return barometerXR;
	}
	public void setBarometerXR(String barometerXR) {
		this.barometerXR = barometerXR;
	}
	public String getMilliAmp() {
		return milliAmp;
	}
	public void setMilliAmp(String milliAmp) {
		this.milliAmp = milliAmp;
	}
	public String getWmilliAmp() {
		return wmilliAmp;
	}
	public void setWmilliAmp(String wmilliAmp) {
		this.wmilliAmp = wmilliAmp;
	}
	public String getLmilliAmp() {
		return lmilliAmp;
	}
	public void setLmilliAmp(String lmilliAmp) {
		this.lmilliAmp = lmilliAmp;
	}
	public String getRainfallIn() {
		return rainfallIn;
	}
	public void setRainfallIn(String rainfallIn) {
		this.rainfallIn = rainfallIn;
	}
	public String getRainfallMM() {
		return rainfallMM;
	}
	public void setRainfallMM(String rainfallMM) {
		this.rainfallMM = rainfallMM;
	}
	public String getWetness() {
		return wetness;
	}
	public void setWetness(String wetness) {
		this.wetness = wetness;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
	public String getWpar() {
		return wpar;
	}
	public void setWpar(String wpar) {
		this.wpar = wpar;
	}
	public String getWatermark() {
		return watermark;
	}
	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}
	public String getWatermarkA() {
		return watermarkA;
	}
	public void setWatermarkA(String watermarkA) {
		this.watermarkA = watermarkA;
	}
	public String getWatermarkB() {
		return watermarkB;
	}
	public void setWatermarkB(String watermarkB) {
		this.watermarkB = watermarkB;
	}
	public String getWatermarkC() {
		return watermarkC;
	}
	public void setWatermarkC(String watermarkC) {
		this.watermarkC = watermarkC;
	}
	public String getWatermarkD() {
		return watermarkD;
	}
	public void setWatermarkD(String watermarkD) {
		this.watermarkD = watermarkD;
	}
	public String getLowTension() {
		return lowTension;
	}
	public void setLowTension(String lowTension) {
		this.lowTension = lowTension;
	}
	public String getCo2() {
		return co2;
	}
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	public String getEcbc() {
		return ecbc;
	}
	public void setEcbc(String ecbc) {
		this.ecbc = ecbc;
	}
	public String getEc() {
		return ec;
	}
	public void setEc(String ec) {
		this.ec = ec;
	}
	public String getEcho25() {
		return echo25;
	}
	public void setEcho25(String echo25) {
		this.echo25 = echo25;
	}
	public String getEcho5() {
		return echo5;
	}
	public void setEcho5(String echo5) {
		this.echo5 = echo5;
	}
	public String getEcho10() {
		return echo10;
	}
	public void setEcho10(String echo10) {
		this.echo10 = echo10;
	}
	public String getJulian_date() {
		return julian_date;
	}
	public void setJulian_date(String julian_date) {
		this.julian_date = julian_date;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public String getBatteryPct() {
		return batteryPct;
	}
	public void setBatteryPct(String batteryPct) {
		this.batteryPct = batteryPct;
	}
	public String getWvoltage() {
		return wvoltage;
	}
	public void setWvoltage(String wvoltage) {
		this.wvoltage = wvoltage;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	public String getSm100() {
		return sm100;
	}
	public void setSm100(String sm100) {
		this.sm100 = sm100;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getTemperatureA_in_F() {
		return temperatureA_in_F;
	}
	public void setTemperatureA_in_F(String temperatureA_in_F) {
		this.temperatureA_in_F = temperatureA_in_F;
	}
	public String getTemperatureB_in_F() {
		return temperatureB_in_F;
	}
	public void setTemperatureB_in_F(String temperatureB_in_F) {
		this.temperatureB_in_F = temperatureB_in_F;
	}
	public String getTemperatureC_in_F() {
		return temperatureC_in_F;
	}
	public void setTemperatureC_in_F(String temperatureC_in_F) {
		this.temperatureC_in_F = temperatureC_in_F;
	}
	public String getTemperatureD_in_F() {
		return temperatureD_in_F;
	}
	public void setTemperatureD_in_F(String temperatureD_in_F) {
		this.temperatureD_in_F = temperatureD_in_F;
	}
	public String getTemperatureE_in_F() {
		return temperatureE_in_F;
	}
	public void setTemperatureE_in_F(String temperatureE_in_F) {
		this.temperatureE_in_F = temperatureE_in_F;
	}
	public String getTemperatureF_in_F() {
		return temperatureF_in_F;
	}
	public void setTemperatureF_in_F(String temperatureF_in_F) {
		this.temperatureF_in_F = temperatureF_in_F;
	}
	public String getTemperature_in_F() {
		return temperature_in_F;
	}
	public void setTemperature_in_F(String temperature_in_F) {
		this.temperature_in_F = temperature_in_F;
	}
	public String getTemperatureA_in_C() {
		return temperatureA_in_C;
	}
	public void setTemperatureA_in_C(String temperatureA_in_C) {
		this.temperatureA_in_C = temperatureA_in_C;
	}
	public String getTemperatureB_in_C() {
		return temperatureB_in_C;
	}
	public void setTemperatureB_in_C(String temperatureB_in_C) {
		this.temperatureB_in_C = temperatureB_in_C;
	}
	public String getTemperatureC_in_C() {
		return temperatureC_in_C;
	}
	public void setTemperatureC_in_C(String temperatureC_in_C) {
		this.temperatureC_in_C = temperatureC_in_C;
	}
	public String getTemperatureD_in_C() {
		return temperatureD_in_C;
	}
	public void setTemperatureD_in_C(String temperatureD_in_C) {
		this.temperatureD_in_C = temperatureD_in_C;
	}
	public String getTemperatureE_in_C() {
		return temperatureE_in_C;
	}
	public void setTemperatureE_in_C(String temperatureE_in_C) {
		this.temperatureE_in_C = temperatureE_in_C;
	}
	public String getTemperatureF_in_C() {
		return temperatureF_in_C;
	}
	public void setTemperatureF_in_C(String temperatureF_in_C) {
		this.temperatureF_in_C = temperatureF_in_C;
	}
	public String getTemperature_in_C() {
		return temperature_in_C;
	}
	public void setTemperature_in_C(String temperature_in_C) {
		this.temperature_in_C = temperature_in_C;
	}
	public String getSolar_rad() {
		return solar_rad;
	}
	public void setSolar_rad(String solar_rad) {
		this.solar_rad = solar_rad;
	}
	public String getRh() {
		return rh;
	}
	public void setRh(String rh) {
		this.rh = rh;
	}
	public String getWind_dir() {
		return wind_dir;
	}
	public void setWind_dir(String wind_dir) {
		this.wind_dir = wind_dir;
	}
	public String getWind_gust() {
		return wind_gust;
	}
	public void setWind_gust(String wind_gust) {
		this.wind_gust = wind_gust;
	}
	public String getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(String wind_speed) {
		this.wind_speed = wind_speed;
	}
	public String getDew_point() {
		return dew_point;
	}
	public void setDew_point(String dew_point) {
		this.dew_point = dew_point;
	}
	public String getVwca() {
		return vwca;
	}
	public void setVwca(String vwca) {
		this.vwca = vwca;
	}
	public String getVwcb() {
		return vwcb;
	}
	public void setVwcb(String vwcb) {
		this.vwcb = vwcb;
	}
	public String getVwcc() {
		return vwcc;
	}
	public void setVwcc(String vwcc) {
		this.vwcc = vwcc;
	}
	public String getVwcd() {
		return vwcd;
	}
	public void setVwcd(String vwcd) {
		this.vwcd = vwcd;
	}
}
