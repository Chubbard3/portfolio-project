package Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class contactData {
	public JList <String> list;
	private String userid = "root";
	private String password = "Stlrams1";
	private String url = "jdbc:mysql://localhost:3306/addressbook?characterEncoding=UTF-8&useSSL=false";
	private Connection con;
	
	//constructor
	public contactData() {
		
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
	
	
	public ArrayList <String> FillListBox() {

		list = new JList<String>();
	DefaultListModel<String> DML = new DefaultListModel<String>();
		try{
			String sql = "SELECT name From contacts";
			//Create Prepared Statement
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			String pname = "";
			
			while(rs.next()){
				pname = rs.getString("name");
				DML.addElement(pname);	
				list.setModel(DML);
			}
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);	
		}
		return personList;
	}
	public void addPerson(PersonInfo person) {
		try{
		
			String sql = "INSERT INTO contacts(name ,phone , " + "address ,city , " + "state ,zip," + "email) VALUES (?,?,?,?,?,?,?);";
			//Create a Prepared statement
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, person.getName());
			ps.setInt(2 ,person.getPhone());
			ps.setString(3, person.getAddress());
			ps.setString(4, person.getCity());
			ps.setString(5, person.getState());
			ps.setInt(6, person.getZip());
			ps.setString(7, person.getEmail());
			
			ps.executeUpdate();
		
			
		}catch (Exception e){
			System.out.println(e);
		}
	}
	public int deletecontact(String name){
		int no = 0;
		try{
			String sql = "DELETE FROM contacts WHERE name = ?";
			//Create a Prepares statement
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			no = ps.executeUpdate();
		}catch (Exception e){
			System.out.println(e);
		}
		return no;
	}
}
