package application;


public class Person {
	
	public int id_p = 1;
	public String nom;
	public String prenom;
	public String cin;
	public String dateofbirth;
	public String country;
	public String phone;
	public String role;
	public static  String email;
	private static String password;
	public int count = 1;
	
	public Person() {
		super();
	}
	public Person(String pseudouser, String mdp) {
	
	    this.id_p = count++;
	    email = pseudouser;
	    password = mdp;
	    count++;
	    System.out.printf("User %s has been crated \n", pseudouser);
	    System.out.printf("Enter 'login' to log in or 'register' to open another account");
	}
	
	public int getId_p() {
		return id_p;
	}
	public void setId_p(int id_p) {
		this.id_p = id_p;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public static String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		Person.password = password;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		Person.email = email;
	}

	
}