package Weather_Data_Transfer;

import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import javax.swing.JDialog;
public class Frame_of_choice {
	JFrame frame;
	JTextField textbox;
	JComboBox combo_box;
	JButton select_table;
	int count;
	ArrayList<String> table_names;
	Weather_Data_Into_DB db_tester;
	String table_name="";
	public Frame_of_choice(Weather_Data_Into_DB db_tester, ArrayList<String> table_names){
	
	//1. Create the frame.
		frame = new JFrame("Choices of action");
		this.table_names= table_names; 
		textbox = new JTextField(15);

   	  	combo_box = new JComboBox();

   	  	select_table = new JButton("Select table");

   	  	count = 0;
   	    this.db_tester=db_tester;
	}
	
	public void set_contents(){
		//2. Optional: What happens when the frame closes?
		try{
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			//3. Create components and put them in the frame.
			//...create emptyLabel...
			//frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
			
			//Create a panel and add components to it.
			JPanel contentPanel = new JPanel(new BorderLayout());
		
			frame.setLayout(new FlowLayout());
		    JRadioButton append,drop_reimport,delete;
		    contentPanel = new JPanel();
		    //combo box with the init() method..
		    			    	 
		     table_name=init();
		     contentPanel.add(textbox);
		     contentPanel.add(combo_box);
		     contentPanel.add(select_table);
		    	  
		    //buttons append, truncate_reimport, delete and yes, no
		      ButtonGroup buttonGroup = new ButtonGroup();
		      append = new JRadioButton("Append");
		      buttonGroup.add(append);
		      contentPanel.add(append);
		     // append.setActionCommand("");
		      drop_reimport = new JRadioButton("Drop_Reimport");
		      buttonGroup.add(drop_reimport);
		      contentPanel.add(drop_reimport);
		     // drop_reimport.setActionCommand("db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Remote(),table_name)");
		      
		      delete = new JRadioButton("Delete");
		      buttonGroup.add(delete);
		      contentPanel.add(delete);
		      append.setSelected(true);
		      
		      
//		      JButton b1=new JButton();
//		      b1.setBounds(300,100,2,2);
//
//		      b1.setText("Yes");
//		    
//		      JButton b2=new JButton();
//		      b2.setBounds(100,10,2,2);
//		      b2.setText("No");
//
//		      contentPanel.add(b1);
//		      contentPanel.add(b2);
		      
		    
		      boolean isAppendSelected = append.isSelected();
		      
		      if (isAppendSelected) {
		       
		          // the append option is selected, do nothing
		    	  
		      } 
		      
		      append.addItemListener(new ItemListener() {
		    	  
		    	    @Override
		    	    public void itemStateChanged(ItemEvent event) {
		    	        int state = event.getStateChange();
		    	        if (state == ItemEvent.SELECTED) {
		    	 
		    	            // do something when the button is selected
		    	 
		    	        } else if (state == ItemEvent.DESELECTED) {
		    	 
		    	            // do something else when the button is deselected
		    	 
		    	        }
		    	    }
		    	});
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
		    	              db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Local(),table_name);
		    	            } else if (response == JOptionPane.CLOSED_OPTION) {
		    	              System.out.println("JOptionPane closed");
		    	            }
		    	          
		    	        }
		    	        	
		    	 
		    	        } 
		    	    
		    	});
		      delete.addItemListener(new ItemListener() {
		    	  
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
		    	              db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Local(), table_name);
		    	            } else if (response == JOptionPane.CLOSED_OPTION) {
		    	              System.out.println("JOptionPane closed");
		    	            }
		    	        	
		    	 
		    	        } 
		    	    }
		    	});
//		      boolean isDropReimportSelected = drop_reimport.isSelected();
//		      
//		      if (isDropReimportSelected) {
//		       
//		          // the drop_Reimport option is selected
//		    	  db_tester.drop_Then_Create_Table(Weather_Data_Into_DB.getConnection_Local(),table_name);
//		      } 
//		      boolean isDeleteSelected = delete.isSelected();
//		      
//		      if (isDeleteSelected) {
//		       
//		          // the delete option is selected
//		    	  db_tester.delete_All_Records(Weather_Data_Into_DB.getConnection_Local(), table_name);
//		      } 

		      
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
		
	    for (int i = 0; i < table_names.size(); i++)
	      combo_box.addItem(table_names.get(count++));
	    textbox.setEditable(false);
	    select_table.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (count < table_names.size())
	          combo_box.addItem(table_names.get(count++));
	      }
	    });
	    combo_box.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        textbox.setText("index: " + combo_box.getSelectedIndex() + "   "
	            + ((JComboBox) e.getSource()).getSelectedItem());
	        table_name=(String) ((JComboBox) e.getSource()).getSelectedItem();
	      }
	    });
	    if (table_name!=""){
	    	return  table_name;
	    }
	    else
	    	return "";
	  }
}	
	

