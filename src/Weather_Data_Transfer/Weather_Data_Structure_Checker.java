package Weather_Data_Transfer;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;

/**
 * Check the given folder for certain files such as index.SWD and other SWD files, if all exist, give out check_marker to continue
 * and put them into a file Arraylist 
 * @author yiweisun
 *
 */
public class Weather_Data_Structure_Checker {
	public JFileChooser chooser;
	private ArrayList<File> files_SD_DATE;
	public File[] filesInDirectory;
	private File File_Path_Report;
	public ArrayList<String> sd_Dates;
	ArrayList<Integer>  sd_Dates_Int;
	public Path write_Path;
	public boolean INDEX_SWD_FLAG;
	public boolean SDDATE_SWD;
	private ArrayList<File> filesInList;
	private int INDEX_SWD_CTR;
	private int INDEX_SDDATE_SWD;
	

	/**
	 * ctor
	 */
	public Weather_Data_Structure_Checker(){
		chooser = new JFileChooser();
		INDEX_SWD_FLAG=false;
		SDDATE_SWD=false;
		files_SD_DATE= new ArrayList<File>();
		filesInList= new ArrayList<File>();
	}
	/*
	 * This method will check whether the certain folder has both SDyearmm.SWD AND INDEX.SWD...with given folder such as 2014 GXE data..
	 */
	public boolean check_SWD_Files() throws NoSuchFileException{
		try{
		chooser.setCurrentDirectory(new java.io.File("/Users/yiweisun/Public/Drop Box/"));
	//	chooser.setCurrentDirectory(new java.io.File("."));
	//	System.setProperty("user.dir", "/tmp");
		chooser.setDialogTitle("Please choose the INDEX.SWD and other SWD folder");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
	//	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(true);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory().getName());
	      File_Path_Report = chooser.getSelectedFile();
	      System.out.println("getSelectedFile() : " + chooser.getSelectedFile().getName());
	      filesInDirectory = chooser.getCurrentDirectory().listFiles();
	      System.out.println("filesInDirectory size "+filesInDirectory.length+"\nfileNames in the directory: \n");
	      for ( File file : filesInDirectory ) {
	    	  System.out.println(file.getName());
	      }
      //to get the files which has SWD into files_SD_DATE, the one has .txt into filesADK
	      for ( File file : filesInDirectory ) {
	    	  if(file.getName().equalsIgnoreCase("INDEX.SWD")){
	    		  INDEX_SWD_FLAG=true;
	    	  }
		      if((file.getName().contains("SD")|file.getName().contains("Watchdog"))&file.getName().contains(".SWD")){
		    	  System.out.println(file.getName());
		    	  files_SD_DATE.add(file);
		    	  SDDATE_SWD=true;
		      } 
		    
	//	      else if(file.getName().contains("Report")){
	//	    	  webReport=file;
	//	      }
	    	  
		    }
	      System.out.println("the output of SDDATE_SWD");
	      for (File file: files_SD_DATE){
	    	  System.out.println(file.getName());
	      }
      
      
      
	}
    else{
    	System.out.println("No Selection ");
    }
		}catch (NullPointerException e){
			e.printStackTrace();
		}
		finally{
			return INDEX_SWD_FLAG&SDDATE_SWD;
	}
}   //check_SWD_files
	
	
public boolean check_SWD_Files_In_Given_Path_List() throws NoSuchFileException{
	try{
	//	chooser.setCurrentDirectory(new java.io.File("/Users/yiweisun/Documents/2014 G X E data/"));
		chooser.setCurrentDirectory(new java.io.File("/Users/yiweisun/Desktop/"));
	//	System.setProperty("user.dir", "/tmp");
		chooser.setDialogTitle("Please choose the File which contains the file paths");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
	//	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(true);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory().getName());
	      File_Path_Report = chooser.getSelectedFile();
	      System.out.println("getSelectedFile() : " + chooser.getSelectedFile().getName());
	     
	      
	      //read the file line by line, if last element of file_names[] contains "
	      
	   // The name of the file to open.
	        String fileName = File_Path_Report.getCanonicalPath();

	        // This will reference one line at a time
	        String line = null;
	        String file_names[];
	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	                //System.out.println(line);
	            	file_names=line.split("/");
	                if(file_names[file_names.length-1].contains(".SWD")){
	                	filesInList.add(new File(line));
	                	//SDDATE_SWD=true;
	                }
	            }   
	            System.out.println("The total in the filesInList is: "+ filesInList.size());
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
	      
	        for ( File file : filesInList ) {
		    	  if(file.getName().equalsIgnoreCase("INDEX.SWD")){
		    		  INDEX_SWD_FLAG=true;
		    		//  System.out.print(file.getName()+"\t");
		    	  }
			      if((file.getName().contains("SD")|file.getName().contains("Watchdog"))&file.getName().contains(".SWD")){
			    	  System.out.print(file.getName()+"\t");
			    	  files_SD_DATE.add(file);
			    	  SDDATE_SWD=true;
			    	
			      } 
	      
	   
	   
	     
	        }
	        ///////////////////
	        // filesInDirectory = chooser.getCurrentDirectory().listFiles();
	/*       System.out.println();
	        System.out.println("the output of SDDATE_SWD");
		      for ( File file_in_SD: files_SD_DATE){
		    	  System.out.print(file_in_SD.getCanonicalPath()+"\t");
		      }
      
	        System.out.println("");
	        System.out.println("The SDDATE_SWD has the "+files_SD_DATE.size()+" files.");
	 */       
		}
	    else{
	    	System.out.println("No Selection ");
	    }
	}catch (NullPointerException e){
			e.printStackTrace();
	}
		finally{
			return INDEX_SWD_FLAG&SDDATE_SWD;
	}
} 
	/**
	 * THIS Method will get more information(total of SDYEARMN.SWD and writing path for next step.)
	 */
    public void Get_SD_DATES_Files(){
    	//from the files which has TC_x.log
      	sd_Dates =new ArrayList<String>();
       for(File file: files_SD_DATE){
    	 //  String[] firstHalf = file.getName().split(".");
    	//   System.out.println("the firstHalf size is:"+ firstHalf.length);
    	 //  System.out.println("the firstHalf [1] is:"+ firstHalf[1]);
    	   String temp = file.getName().substring(2, file.getName().indexOf("."));
    	  
    	   System.out.println("the date in the file name is:"+ temp);
    		//if there are files that has ...(1).json...
    	   if(!temp.contains("INDEX"))
    	   {
    		   sd_Dates.add(temp);
    	   }
    	   
    	   
       }
       // sort the tc_numbers
       
      
       //to check the SD DATE numbers
/*	       System.out.println("the sd_Dates we got:");
	       for(String sd_Date : sd_Dates){
	    	   System.out.print(sd_Date+",");
	    	  
	       }
	       System.out.println();
	       
*/	      
	
	
    }
    //this method will return files_SD_DATE to the class outside
    public ArrayList<File> get_Files_SD_DATE(){
    	return files_SD_DATE;
    }
}

