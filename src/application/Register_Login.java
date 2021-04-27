package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class Register_Login {
	public static int currentId = 0;
	public boolean checkEmail(String email) throws ClassNotFoundException, URISyntaxException{
		
		PreparedStatement st;
		ResultSet rs;
		boolean email_exist = false;
		
		String query = "SELECT * FROM Personne WHERE `email` = ?";
		
		try {
		    st = DBConnection.getConnection().prepareStatement(query);
		    st.setString(1, email);
		    rs = st.executeQuery();
		
		    if(rs.next())
		    {
		        email_exist = true;
		        JOptionPane.showMessageDialog(null, "This Email is Already Taken", email, 2);
		    }
		    else {
		    	return false;
		    }
		    
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
		
		return email_exist;
		}
		public static boolean isValid(String email) 
	    { 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    } 
}
class Login extends Register_Login{
	
	// button login
    public void jButton_LoginActionPerformed(MouseEvent event,String email,String pass) throws ClassNotFoundException, IOException, URISyntaxException { 
        PreparedStatement st;
        ResultSet rs;
        
        //create a select query to check if the email and the password exist in the database
        String query = "SELECT * FROM Personne WHERE `email` = ? AND `password` = ?";
        // show a message if the email or the password fields are empty
        if(email.trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Enter Your Email", "Empty Email", 2);
        }
        else if(pass.trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Enter Your Password", "Empty Password", 2);
        }
        else{
            
            try {
            st = DBConnection.getConnection().prepareStatement(query);
            st.setString(1, email);
            st.setString(2, pass);
            rs = st.executeQuery();
            if(rs.next())
            {
            	String role = rs.getString("role");
            	String name = rs.getString("nom");
                // show a new form
            	if (role.equals("Admin")) {
            		Window window =   ((Node)(event.getSource())).getScene().getWindow(); 
                    if (window instanceof Stage){
                        ((Stage) window).close();
                    }
	            	Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
	                Scene scene = new Scene(root);
	                Stage stage = new Stage();
	                stage.setTitle("ADV");
	                stage.setScene(scene);
	                stage.setMaximized(true);
	                stage.show();
            	}else if (role.equals("User")) {
                    String numbidStringid = "Select id_p from Personne WHERE email='"+email+"' AND password='"+pass+"'";
                    Statement statementid = DBConnection.getConnection().createStatement();
                    ResultSet rsid = statementid.executeQuery(numbidStringid);
                    rsid.next();
                    currentId = rsid.getInt(1);
                    Window window =   ((Node)(event.getSource())).getScene().getWindow(); 
                    if (window instanceof Stage){
                        ((Stage) window).close();
                    }
            	}
                
            }else{
                // error message
                JOptionPane.showMessageDialog(null, "Invalid Email / Password","Login Error",2);
            }
            
        } catch (SQLException ex) {
                ex.printStackTrace();
        }
            
        }
        
    } 
}
class Register extends Register_Login{
    // create a function to verify the empty fields  
    public boolean verifyFields(String fname,String email,String pass) {
    	if(!(fname.matches("[a-zA-Z]+"))){
    		JOptionPane.showMessageDialog(null, "This full name is not valid", email, 2);
    	}
    	if(!(isValid(email))) {
	       	JOptionPane.showMessageDialog(null, "This Email is not valid", email, 2);
	       }
        // check empty fields
        if(fname.trim().equals("") || email.trim().equals("") || pass.trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty","Empty Fields",2);
            return false;
        }
        // if everything is ok
        else{
            return true;
        }
    }
   
	// button register(Sign up)   
	public void jButton_RegisterActionPerformed(MouseEvent event,String fname,String email,String pass) throws ClassNotFoundException, IOException, URISyntaxException {                                                 
	// check if the data are empty=
	         if(verifyFields(fname,email,pass))
	         {// check if the email already exists
	             if(!checkEmail(email))
	             {
	                 PreparedStatement ps;
	                 ResultSet rs;
	                 String registerUserQuery = "INSERT INTO Personne(`id_p`, `nom`, `prenom`, `cin`, `dateofbirth`, `country`,`phone`,`email`,`role`,`password`) VALUES (?,?,?,?,?,?,?,?,?,?)";
	                 
	                 try {
	                     
	                     ps = DBConnection.getConnection().prepareStatement(registerUserQuery);
	                     String numbidString = "Select Max(id_p) from Personne";
	                     Statement statement = DBConnection.getConnection().createStatement();
	                     rs = statement.executeQuery(numbidString);
	                     rs.next();
	                     int myMaxId = rs.getInt(1);
	                     currentId = myMaxId+1;
	                     ps.setString(1, Integer.toString(myMaxId+1));
	                     ps.setString(2, fname);
	                     ps.setString(3, fname);
	                     ps.setString(4, "");
	                     ps.setString(5, "2021-01-01");
	                     ps.setString(6, "");
	                     ps.setString(7, "06");
	                     ps.setString(8, email);
	                     ps.setString(9, "User");
	                     ps.setString(10, pass);
	                    
	                     if(ps.executeUpdate() != 0){
			            	 JOptionPane.showMessageDialog(null, "Your Account Has Been Created");
			            	 Login login = new Login();
			            	 login.jButton_LoginActionPerformed(event, email, pass);
						 }else{
						     JOptionPane.showMessageDialog(null, "Error: Check Your Information");
						 }
	                     
	                 } catch (SQLException ex) {
	                     ex.printStackTrace();
	                 }
	                 
	             }else {
	            	 Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
	         	     Stage stage = new Stage();
	                 Scene scene = new Scene(root);
	                 stage.setTitle("ADV");
	                 stage.setScene(scene);
	                 scene.setFill(Color.TRANSPARENT);
	                 stage.initStyle(StageStyle.TRANSPARENT);
	                 stage.show();
	             }
	         }
	                 
	    }
}
