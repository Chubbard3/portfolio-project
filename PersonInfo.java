package Project;

public class PersonInfo {

private String name ,address ,city ,state ,email;
private int id ,phone ,zip;

//default constructor
public PersonInfo(){
	
	name = "";
	address = "";
	city = "";
	state = "";
	
	id = 0;
	
	
}
public PersonInfo( String name){

	this.name = name;
}

//param constructor with 6 values
public PersonInfo(String name ,int phone ,String address ,String city ,String state ,int zip ,String email ){
	this.name = name;
	this.phone = phone;
	this.address = address;
	this.city = city;
	this.state = state;
	this.zip = zip;
	this.email = email;
	
	
}

//setters
public void setId(int i){
	id = i;
}
public void setName(String n){
	name = n;
}
public void setPhone(int ph){
	phone = ph;
}
public void setAddress(String a){
	address = a;
}
public void setCity(String c){
	city = c;
}
public void setState(String s){
	state = s;
}
public void setZip(int z){
	zip = z;
}
public void setEmail(String e){
	email = e;
}

//getters
public int getId(){
	return id;
}
public String getName(){
	return name;
}
public int getPhone(){
	return phone;
}
public String getAddress(){
	return address;
}
public String getCity(){
	return city;
}
public String getState(){
	return state;
}
public int getZip(){
	return zip;
}
public String getEmail(){
	return email;
}
}
