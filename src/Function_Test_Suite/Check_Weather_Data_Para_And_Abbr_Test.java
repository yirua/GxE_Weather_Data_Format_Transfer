/**
 * 
 */
package Function_Test_Suite;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import Weather_Data_Transfer.Pair;
import Weather_Data_Transfer.Read_SD_SWD_Files;
import Weather_Data_Transfer.Weather_Data_Structure_Checker;
import Weather_Data_Transfer.Weather_Info;

/**
 * @author yiweisun
 *
 */
public class Check_Weather_Data_Para_And_Abbr_Test {
	Weather_Data_Structure_Checker checker= new Weather_Data_Structure_Checker();
	Read_SD_SWD_Files reader;
	Set<Pair> result_pairs= new HashSet<Pair>();
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
			int pair_ctr=0;
			result_pairs=reader.Check_Weather_Data_Para_And_Abbr(files);
			System.out.println("The result of the folder cominations of pairs");
			for(Pair pair: result_pairs){
				System.out.print(pair.get_prv()+"\t");
/////////////////////////////////////////				
				
				//test only one file
				//break;
			}
			System.out.println();
			for(Pair pair: result_pairs){
				System.out.print(pair.get_next()+"\t");
/////////////////////////////////////////				
				pair_ctr++;
				//test only one file
				//break;
			}
			//point to Delaware Station 9078
			// assertEquals(6,files.size());
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
