package Weather_Data_Transfer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.naming.directory.SearchResult;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import javax.swing.JDialog;


/**
 * This class will show a Jframe of action choices for user to choose, including append, drop/reimport, create_new_table, the delete function is developed
 * but not included in the Jframe since the user can do that easily through PostAdmin and the first target of design is to import data into a table
 * @author yiweisun
 *
 */
public class Frame_of_choice {
	JFrame frame;
//	JApplet frame;
	JTextField text_box;
	JTextField table_name_box;
	JComboBox combo_box;
	
	int count;
	ArrayList<String> table_names;
	Weather_Data_Into_DB db_tester;
	String table_name="";
	String new_table_name;
	boolean delete_flag;
	/**
	 * The constr of Frame_of_choice
	 * @param db_tester
	 * @param table_names
	 */
	public Frame_of_choice(Weather_Data_Into_DB db_tester, ArrayList<String> table_names){
	
	//1. Create the frame.
		frame = new JFrame("Choices of action");
	//	frame = new JApplet();
		this.table_names= table_names; 
		text_box = new JTextField(20);
		table_name_box= new JTextField(20);
   	  	combo_box = new JComboBox();

   	  	

   	  	count = 0;
   	    this.db_tester=db_tester;
   	    setDelete_flag(false);
	}
	
	public void set_contents(){
		//2. Optional: What happens when the frame closes?
		try{
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			//3. Create components and put them in the frame.
			//...create emptyLabel...
			//frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
			
			//Create a panel and add components to it.
			JPanel contentPanel = new JPanel(new BorderLayout());
			
		    JRadioButton append,drop_reimport,delete,drop_table;
		    JRadioButton create_new_table = new JRadioButton("Create new table");
		    contentPanel = new JPanel();
		    //combo box with the init() method..and set_table_name with the return value from it.
		    			    	 
		     set_table_name(init());
		     JLabel selected_table_name= new JLabel("Table to select");
		     contentPanel.add(selected_table_name);
		     contentPanel.add(text_box);
		     contentPanel.add(combo_box,BorderLayout.EAST);
		     
		    	  
		    //buttons append, truncate_reimport, delete and yes, no
		      ButtonGroup buttonGroup = new ButtonGroup();
		      
		      
		      
		      append = new JRadioButton("Append");
		      buttonGroup.add(append);
		      contentPanel.add(append, BorderLayout.LINE_START);
		      // append.setSelected(true);
		     // append.setActionCommand("");
		      drop_reimport = new JRadioButton("Drop_Reimport");
		      buttonGroup.add(drop_reimport);
		      contentPanel.add(drop_reimport,BorderLayout.CENTER);
		     // drop_reimport.setActionCommand("db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Remote(),table_name)");
		      /*
		       * As discussed with Darwin, the delete function is not necessary here
		       */
		    /*  delete = new JRadioButton("Delete");
		      buttonGroup.add(delete);
		      contentPanel.add(delete, BorderLayout.WEST);*/
		    ///////////drop to omit because it does not help with the data input.
//		      drop_table = new JRadioButton("Drop");
//		      buttonGroup.add(drop_table);
//		      contentPanel.add(drop_table, BorderLayout.EAST);
		      //put the input_table_name at the end of row
		      buttonGroup.add(create_new_table);
		      contentPanel.add(create_new_table, BorderLayout.SOUTH);
		      contentPanel.add(table_name_box, BorderLayout.PAGE_END);
		      
////////////different radio button selection will lead to differnt action..		
		      /*
		       * If the user choose to create a new table, then he has to input the new table name.
		       */
		      create_new_table.addItemListener(new ItemListener() {
		    	  JFrame aframe;
		    	    @Override
		    	    public void itemStateChanged(ItemEvent event) {
		    	        int state = event.getStateChange();
		    	        if (state == ItemEvent.SELECTED) {
		    	 
		    	            // do something when the button is selected
		    	        	//alert to do it or not
		    	        	//JOptionPane.showMessageDialog(null, "My Goodness, are you sure to drop and reimport the data?");
		    	        	JDialog.setDefaultLookAndFeelDecorated(true);
		    	            int response = JOptionPane.showConfirmDialog(null, "Do you want to continue to input new table name?", "Confirm",
		    	                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    	            NullPointerException ne= null;
							if (response == JOptionPane.NO_OPTION) {
		    	              System.out.println("No button clicked");
		    	            } else if (response == JOptionPane.YES_OPTION) {
		    	              
//		    	              Object result = JOptionPane.showInputDialog(frame, "Enter new table name followed gxe_weather:");
//		    			      System.out.println(result);
		    	            	
		    	            	try{
		    	            		/*
		    	            		 * the while loop will test the table_name_box string input to guarantee that the input is numeric and has 4 digits.
		    	            		 */
		    	            		while (true){
		    	            			String result = table_name_box.getText();
				    			      if (!result.isEmpty()&result.matches("[0-9]+")&result.length()==4 ){
				    			    	  set_table_name("gxe_weather_"+result);
				    			    	//  db_tester.Create_Table_Gxe_Weather(Weather_Data_Into_DB.getConnection_Local(),get_table_name());
				    			    	  db_tester.Create_Table_Gxe_Weather(Weather_Data_Into_DB.getConnection_Remote(),get_table_name());
				    			    	  setDelete_flag(false);
				    			    	  
				    			    	  break;
				    			      }
				    			      else
				    			    	  //give alert to input a table 
				    			    	   aframe = new JFrame();
				    			      		Object message_result = JOptionPane.showInputDialog(aframe, "Enter valid table year which is four digits:");
				    			      		//result= (String)message_result;
				    			      		table_name_box.setText((String)message_result);
			    	            	}
			    			      
		    	            	}catch(NullPointerException e ){
		    	            		e.printStackTrace();
		    	            		response=JOptionPane.CLOSED_OPTION;
		    	            	}
		    			      response=JOptionPane.CLOSED_OPTION;
		    	            } else if (response == JOptionPane.CLOSED_OPTION) {
		    	              System.out.println("JOptionPane closed");
		    	            }
		    	          
		    	        }
		    	        	
		    	 
		    	        } 
		    	    
		    	});
		      /*
		       * If the user choose the append action.
		       */
		      append.addItemListener(new ItemListener() {
		    	  
		    	    @Override
		    	    public void itemStateChanged(ItemEvent event) {
		    	        int state = event.getStateChange();
		    	        if (state == ItemEvent.SELECTED) {
		    	 
		    	            // do something when the button is selected
		    	        	setDelete_flag(false);
		    	        	//if the table 
		    	        	if(get_table_name().equals("")){
		    	        		set_table_name("gxe_weather_2015");
		    	        		db_tester.Create_Table_Gxe_Weather(Weather_Data_Into_DB.getConnection_Remote(),get_table_name());
		    	        	}
		    	        } else if (state == ItemEvent.DESELECTED) {
		    	 
		    	            // do something else when the button is deselected
		    	 
		    	        }
		    	    }
		    	});
		      /*
		       * If the user choose the drop_reimport action.
		       */
		      drop_reimport.addItemListener(new ItemListener() {
		    	  
		    	    @Override
		    	    public void itemStateChanged(ItemEvent event) {
		    	        int state = event.getStateChange();
		    	        if (state == ItemEvent.SELECTED) {
		    	 
		    	            // do something when the button is selected
		    	        	//alert to do it or not
		    	        	//JOptionPane.showMessageDialog(null, "My Goodness, are you sure to drop and reimport the data?");
		    	        	JDialog.setDefaultLookAndFeelDecorated(true);
		    	            int response = JOptionPane.showConfirmDialog(null, "Do you want to continue to drop_create table and reimport data?", "Confirm",
		    	                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    	            if (response == JOptionPane.NO_OPTION) {
		    	              System.out.println("No button clicked");
		    	            } else if (response == JOptionPane.YES_OPTION) {
		    	              System.out.println("Yes button clicked");
		    	              //get_table_name can not be ""
		    	              if(get_table_name().equals("")|get_table_name().isEmpty()){
		    	              
		    	            	  set_table_name("gxe_weather_2015");
		    	              }
		    	              //		    	            db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Local(),get_table_name());
	    	            	  	db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Remote(),get_table_name());
		    	              //if the get_table_name is "" close the JOptionPane
		    	             
		    	              
		    	            } else if (response == JOptionPane.CLOSED_OPTION) {
		    	              System.out.println("JOptionPane closed");
		    	            }
		    	          
		    	        }
		    	        	
		    	 
		    	        } 
		    	    
		    	});
		      /*
		       * If the user choose the delete radio button.
		       */
/*		      delete.addItemListener(new ItemListener() {
		    	  
		    	    @Override
		    	    public void itemStateChanged(ItemEvent event) {
		    	        int state = event.getStateChange();
		    	        if (state == ItemEvent.SELECTED) {
		    	 
		    	            // do something when the button is selected
		    	        	//alert to do it or not
		    	        	JDialog.setDefaultLookAndFeelDecorated(true);
		    	            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete all the table data?", "Confirm",
		    	                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    	            if (response == JOptionPane.NO_OPTION) {
		    	              System.out.println("No button clicked");
		    	            } else if (response == JOptionPane.YES_OPTION) {
		    	              System.out.println("Yes button clicked");
		    	              //set the delete flag to true to mark the two way out delete or other actions
		    	              //the get_table_name() can not be ""
		    	              setDelete_flag(true);
		    	              db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Local(), get_table_name());
		    	             // db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Remote(), get_table_name());
		    	              //if the get_table_name is ""
		    	            } else if (response == JOptionPane.CLOSED_OPTION) {
		    	              System.out.println("JOptionPane closed");
		    	            }
		    	        	
		    	 
		    	        } 
		    	    }
		    	});*/
//////////////only drop_table function can get from PosgreAdmin application		      
/*		      drop_table.addItemListener(new ItemListener() {
		    	  
		    	    @Override
		    	    public void itemStateChanged(ItemEvent event) {
		    	        int state = event.getStateChange();
		    	        if (state == ItemEvent.SELECTED) {
		    	 
		    	            // do something when the button is selected
		    	        	//alert to do it or not
		    	        	JDialog.setDefaultLookAndFeelDecorated(true);
		    	            int response = JOptionPane.showConfirmDialog(null, "Do you want to drop the table?", "Confirm",
		    	                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    	            if (response == JOptionPane.NO_OPTION) {
		    	              System.out.println("No button clicked");
		    	            } else if (response == JOptionPane.YES_OPTION) {
		    	              System.out.println("Yes button clicked");
		    	              //set the delete flag to true to mark the two way out delete or other actions
		    	              setDelete_flag(true);
		    	              db_tester.drop_Table(Weather_Data_Into_DB.getConnection_Local(), get_table_name());
		    	             // db_tester.drop_Table(Weather_Data_Into_DB.getConnection_Remote(), get_table_name());
		    	            } else if (response == JOptionPane.CLOSED_OPTION) {
		    	              System.out.println("JOptionPane closed");
		    	            }
		    	        	
		    	 
		    	        } 
		    	    }
		    	});*/
/////////////////////////////////////////////////////////////////////////////////////////////////End of action choices.		      
		      frame.add(contentPanel);
		      frame.setSize(182,150);
		
			//topLevelContainer.setContentPane(contentPane);
		
			//4. Size the frame.
			frame.pack();
		
			//5. Show it.
			frame.setVisible(true);
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	/**
	 * This will init the combobox.
	 */
	public String init() {
		try{
		    for (int i = 0; i < table_names.size(); i++)
		      combo_box.addItem(table_names.get(count++));
	    }
		//if the ArrayList of table_names is empty, then it will throw a NullPointerException
		catch (NullPointerException nullpointer){
			nullpointer.printStackTrace();
		}
		finally{
		    text_box.setEditable(false);
	//	    select_table.addActionListener(new ActionListener() {
	//	      public void actionPerformed(ActionEvent e) {
	//	        if (count < table_names.size())
	//	          combo_box.addItem(table_names.get(count++));
	//	      }
	//	    });
		    combo_box.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        text_box.setText(""+ ((JComboBox) e.getSource()).getSelectedItem());
		        table_name=(String) ((JComboBox) e.getSource()).getSelectedItem();
		      }
		    });
		    if (table_name!=""){
		    	set_table_name(table_name);
		    	return  table_name;
		    }
		    else
		    	return "";
		}
	    
	  }
	
	public void set_table_name(String table_name){
		this.table_name=table_name;
	}
	public String get_table_name(){
		
		return table_name;
	}

	/**
	 * @return the delete_flag
	 */
	public boolean isDelete_flag() {
		return delete_flag;
	}

	/**
	 * @param delete_flag the delete_flag to set
	 */
	public void setDelete_flag(boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	public void close_frame(){
		frame.setVisible(false);
	//if it is a JFrame
	 	frame.dispose();
	// if JApplet
	//	frame.destroy();
	}
}	
	

