package Project;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.awt.Desktop;




public class addressbook{
	contactData cd;
	String name ,address, city ,state ,email;
	int phone ,zip;
	
	JLabel jlName ,jlPhone ,jlAddress ,jlCity ,jlState ,jlZip ,jlEmail; 
	JTextField tfName ,tfPhone ,tfAddress ,tfCity ,tfState ,tfZip ,tfEmail;
	JButton add ,delete ,directions ,update ,display;
	JList <String> list;

	DefaultListModel <String> DLM;
	JMenuBar menubar = new JMenuBar();
	JMenu file = new JMenu();
	JMenuItem viewcontacts ,exit ,save;
	Container cPane;
	JPanel leftpanel ,centerpanel ,bottompanel ,toppanel;
	private Connection con;
	
	private static final int LIST_ROW_COUNT = 40;
	private static final int LIST_CHAR_WIDTH = 90;
	private static final String LIST_PROTOTYPE = "%" + LIST_CHAR_WIDTH + "s";
	private String userid = "root";
	private String password = "Stlrams1";
	private String url = "jdbc:mysql://localhost:3306/addressbook?characterEncoding=UTF-8&useSSL=false";
	public addressbook() {
		
		createGUI();
		cd = new contactData();
		getConnection();
		
		
	}
	public Connection getConnection(){
		try{
			con = DriverManager.getConnection(url,userid,password);
		}catch (SQLException ex) {
			System.err.println("SQLException" + ex.getMessage());
		}
		return con;
	}
	
	public Component addMenuWidgets() {
		toppanel = new JPanel(new FlowLayout());
			menubar = new JMenuBar();
			file = new JMenu("File");
			menubar.add(file);
			viewcontacts = new JMenuItem("View Contacts");
			save = new JMenuItem("SAVE");
			file.add(save);
			file.add(viewcontacts);
			exit = new JMenuItem("Exit");
			file.add(exit);
			
			event1 ev = new event1();
			viewcontacts.addActionListener(ev);
			save.addActionListener(ev);
			exit.addActionListener(ev);
			
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
	
		list = new JList<String>();
		list.setVisibleRowCount(LIST_ROW_COUNT);
		list.setPrototypeCellValue(String.format(LIST_PROTOTYPE, ""));

		JScrollPane pane = new JScrollPane(list);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		list.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent event){
						StringBuilder query = new StringBuilder();
						String selected = list.getSelectedValue().toString();
						query.append("SELECT * FROM contacts WHERE name = "+ "'").append(selected).append("'");
						String sql = query.toString();
						try {
							Statement s = con.createStatement();
							ResultSet rs = s.executeQuery(sql);
							while(rs.next()){
								
								 name = rs.getString("name");
								 tfName.setText(name);
								 phone = rs.getInt("phone");
								 String phoneAsString = Integer.toString(phone);
								 tfPhone.setText(phoneAsString);
								 address = rs.getString("address");
								 tfAddress.setText(address);
								 city = rs.getString("city");
								 tfCity.setText(city);
								state = rs.getString("state");
								 tfState.setText(state);
								 zip = rs.getInt("zip");
								 String zipAsString = Integer.toString(zip);
								 tfZip.setText(zipAsString);
								email = rs.getString("email");
								 tfEmail.setText(email);
								
							}
						} catch (SQLException e) {
							System.out.println("System did not connect to database");
							System.out.println(sql);
						}
						
					}
				}
			);
		leftpanel.add(pane);
		return leftpanel;
	}
	
	
	public Component arrangeComponents() {
		centerpanel = new JPanel(new GridLayout(7 ,0,0,35));
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
		update = new JButton("UPDATE");
		delete = new JButton("DELETE");
		display = new JButton("DISPLAY");
		directions = new JButton("DIRECTIONS");
		
		event e = new event();
		add.addActionListener(e);
		update.addActionListener(e);
		delete.addActionListener(e);
		display.addActionListener(e);
		directions.addActionListener(e);
		
		bottompanel.add(add);
		bottompanel.add(update);
		bottompanel.add(delete);
		bottompanel.add(display);
		bottompanel.add(directions);
		
		
		
		return bottompanel;
		
	}
	
	
	public class event implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == add){
				addContact();
				clear();	
			}else if (e.getSource() == delete){
				deleteContacts();
				clear();
				displayContacts();
			}else if (e.getSource() == update){
				updateContacts();
				clear();
			}else if (e.getSource() == display){
				displayContacts();
			}else if (e.getSource() == directions ){
				if(Desktop.isDesktopSupported()){
					try {
						String address1 = tfAddress.getText();
						String city1 = tfCity.getText();
						String state1 = tfState.getText();
						String zip1 = tfZip.getText();
						StringBuilder query = new StringBuilder();
						query.append(address1).append(" ").append(city1).append(" ").append(state1).append(" ").append(zip1);
						String Url =  "https://maps.google.com/?q=" + URLEncoder.encode(query.toString(),"UTF-8");
						Desktop.getDesktop().browse(new URI(Url));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	public class event1 implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			if (ev.getSource() == viewcontacts){
				
			}else if(ev.getSource() == save){
				saveXml();
			}else if(ev.getSource() == exit){
				
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
			
			JOptionPane.showMessageDialog(null, "Please enter name");
		}else{
			//create a PersonInfo object and pass it to contactData to save it
			PersonInfo person = new PersonInfo(name , phone ,address ,city ,state ,zip ,email);
			cd.addPerson(person);
			DLM = new DefaultListModel<String>();
			String sql = "SELECT name FROM contacts";
			try {
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while(rs.next()){
					String name = rs.getString("name");
					DLM.addElement(name);
				}
				list.setModel(DLM);
				JOptionPane.showMessageDialog(null, "Contact Added");
			} catch (SQLException e) {
				System.out.println("System did not connect to database");
			}
			
			
			JOptionPane.showMessageDialog(null, "Contact Saved");
		}
	}
	public void updateContacts(){
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
			JOptionPane.showMessageDialog(null, "Please enter name");
		}else{
			//create a PersonInfo object and pass it to contactData to save it
			PersonInfo person = new PersonInfo(name , phone ,address ,city ,state ,zip ,email);
			cd.updatePerson(person);
			JOptionPane.showMessageDialog(null, "Contact Updated");
		}
		
	}
	public void displayContacts(){
		DLM = new DefaultListModel<String>();
		String sql = "SELECT name FROM contacts";
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				String name = rs.getString("name");
				DLM.addElement(name);
			}
			list.setModel(DLM);
		} catch (SQLException e) {
			System.out.println("System did not connect to database");
		}
		JOptionPane.showMessageDialog(null, "Database Displayed ");
	}
	
	public void deleteContacts(){
		PreparedStatement ps = null;
		StringBuilder query = new StringBuilder();
		String pname = list.getSelectedValue().toString();
		query.append("DELETE FROM contacts WHERE name =" + "'").append(pname).append("'");
		String sql = query.toString();
		try{
			// Create a Prepared statement
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		}
		catch(Exception e){
			System.out.println("System did not connect");
		}
	}
	public void saveXml() {
		String sql = "SELECT * FROM contacts";
		try {
//			Connect to Database
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
//			Execute JSON Object
			JSONObject json = new JSONObject();
			JSONArray jArray = new JSONArray();
			while(rs.next()){
				
			}
			
		} catch (SQLException e) {
			
			System.out.println("Error in Databate");
			 e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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