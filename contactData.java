package Project;

import java.sql.*;


import javax.swing.JList;

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
	public void updatePerson(PersonInfo person) {
		StringBuilder query = new StringBuilder();
		String pname = person.getName();
		query.append("UPDATE contacts SET name =?,phone=? , address=? , city=? ,state=? ,zip=? ,email=? Where name = " + "'").append(pname + "'");
		String sql = query.toString();
		try{	
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
			System.out.println("System did not connect.");
			System.out.println(sql);
		}
	}
	
}