package Project;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.*;

import GUI.menututorial.event;



public class addressbook{
	contactData cd;
	String name ,address, city ,state ,email;
	int phone ,zip;
	ArrayList<String> personList;
	
	JLabel jlName ,jlPhone ,jlAddress ,jlCity ,jlState ,jlZip ,jlEmail; 
	JTextField tfName ,tfPhone ,tfAddress ,tfCity ,tfState ,tfZip ,tfEmail;
	JButton add ,delete ,directions ,update;
	JList <String> list;

	DefaultListModel <String>m;
	JMenuBar menubar = new JMenuBar();
	JMenu file = new JMenu();
	JMenuItem viewcontacts ,exit;
	Container cPane;
	JPanel leftpanel ,centerpanel ,bottompanel ,toppanel;
	private Connection con;
	public addressbook() {
		
		createGUI();
		cd = new contactData();
		
	}
	
	public Component addMenuWidgets() {
		toppanel = new JPanel(new FlowLayout());
			menubar = new JMenuBar();
			file = new JMenu("File");
			menubar.add(file);
			viewcontacts = new JMenuItem("View Contacts");
			file.add(viewcontacts);
			exit = new JMenuItem("Exit");
			file.add(exit);
//			toppanel.setAlignmentX(LEFT_ALIGNMENT);
			toppanel.add(menubar);
			return toppanel;
		}
		
	public void createGUI() {
		JFrame frame = new JFrame("Address Book 1.0");
		
		Container cPane = frame.getContentPane();
		cPane.setLayout(new BorderLayout());
		
		cPane.add(arrangeComponents(),BorderLayout.CENTER);
		cPane.add(addButton(), BorderLayout.SOUTH);
		cPane.add(addMenuWidgets() ,BorderLayout.NORTH);
		cPane.add(listContactnames(), BorderLayout.WEST);
		
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public Component listContactnames() {
		leftpanel = new JPanel(new FlowLayout());
		
		m = new DefaultListModel<String>();
	
		
		
		// Create list and put in scroll pane.
		list = new JList<String>(DML);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(10);
		JScrollPane pane = new JScrollPane(list);
		
		int cellWidth = 350;
		int cellHeight = 600;
		list.setFixedCellWidth(cellWidth);
		list.setFixedCellHeight(cellHeight);
		
		
		leftpanel.add(pane);
		return leftpanel;
	}
	
	
	public Component arrangeComponents() {
		centerpanel = new JPanel(new GridLayout(7 ,0,0,50));
		jlName = new JLabel("Name");
		tfName = new JTextField(10);
		jlPhone = new JLabel("Phone");
		tfPhone = new JTextField(10);
		jlAddress = new JLabel("Address");
		tfAddress = new JTextField(10);
		jlCity = new JLabel("City");
		tfCity = new JTextField(10);
		jlState = new JLabel("State");
		tfState = new JTextField(10);
		jlZip = new JLabel("Zipcode");
		tfZip = new JTextField(10);
		jlEmail = new JLabel("Email");
		tfEmail = new JTextField(10);
		
		centerpanel.add(jlName);
		centerpanel.add(tfName);
		centerpanel.add(jlPhone);
		centerpanel.add(tfPhone);
		centerpanel.add(jlAddress);
		centerpanel.add(tfAddress);
		centerpanel.add(jlCity);
		centerpanel.add(tfCity);
		centerpanel.add(jlState);
		centerpanel.add(tfState);
		centerpanel.add(jlZip);
		centerpanel.add(tfZip);
		centerpanel.add(jlEmail);
		centerpanel.add(tfEmail);
		
		return centerpanel;
		
	}
	public Component addButton(){
		bottompanel = new JPanel(new FlowLayout());
		add = new JButton("ADD");
		delete = new JButton("DELETE");
		directions = new JButton("DIRECTIONS");
		
		event e = new event();
		add.addActionListener(e);
		
		bottompanel.add(add);
		bottompanel.add(delete);
		bottompanel.add(directions);
		
		
		
		return bottompanel;
		
	}
	
	
	public class event implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == add){
				addContact();
				clear();
				cd.FillListBox();
				
			}else if (e.getSource() == delete){
				
			}
			
			else if (e.getSource() == directions){
				
			}
		}
	}
	public void addContact(){
		name = tfName.getText();
		name = name.toUpperCase();
		try{
			phone = Integer.parseInt("" + tfPhone.getText());
		}catch(Exception e){
			/*System.out.print("Input is a string");
	   		JOptionPane.showMessageDialog(null, "Please enter Phone Number");*/
		}
		address = tfAddress.getText();
		city = tfCity.getText();
		state = tfState.getText();
		try{
			zip = Integer.parseInt("" + tfZip.getText());
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "Please enter Zipcode");
		}
		
		email = tfEmail.getText();
		
		if(name.equals("")){
			JOptionPane.showMessageDialog(null, "Please enter person Name ");
		}else{
			//create a PersonInfo object and pass it to contactData to save it
			PersonInfo person = new PersonInfo(name , phone ,address ,city ,state ,zip ,email);
			cd.addPerson(person);
			
			
			JOptionPane.showMessageDialog(null, "Contact Saved");
		}
//		public void deleteContact(){
//			name = tfName.getText();
//			name.toUpperCase();
//			
//		}
	}
	
	 public void clear(){
		    
		   	tfName.setText("");
		   	tfPhone.setText("");
		   	tfAddress.setText("");
		    tfCity.setText("");
		    tfState.setText("");
		    tfZip.setText("");
		   	tfEmail.setText("");
		   	
		   	
		   	
	     }
	public static void main(String[] args) {
		new addressbook();
	}
}