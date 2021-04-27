package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class personController implements Initializable {

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_surname;

    @FXML
    private TextField text_cin;

    @FXML
    private TextField text_phone;

    @FXML
    private TextField text_country;

    @FXML
    private TextField text_email;

    @FXML
    private PasswordField text_psd;

    @FXML
    private PasswordField text_rnpsd;

    @FXML
    private PasswordField text_npsd;

    @FXML
    private DatePicker text_dob;

    @FXML
    private Label nbrTravels;

    @FXML
    private Label nbrCity;
    @FXML
    private ImageView profilimg;

    @FXML
    private Button btnsav;
    @FXML
    private Button btndelete;
    
	static Connection con =null;
	static PreparedStatement PS=null;
	static String streamglo ="C:\\Users\\PC\\eclipse-workspace\\ADV\\src\\Images\\male_user_64px.png";
	String c=Integer.toString(Register_Login.currentId);
    
    
	void functsetter() throws SQLException {
    	PS.setString(1, text_name.getText());
		PS.setString(2, text_surname.getText());
		PS.setString(3, text_cin.getText());
		PS.setString(4,text_dob.getValue().toString());
		PS.setString(5, text_phone.getText());
		PS.setString(7, text_country.getText());
		PS.setString(6, text_email.getText());
		PS.setString(8, streamglo);
    }
	@FXML
    void savebt(ActionEvent event) {
    	con=DBConnection.getConnection();
		try {
			String input="";
			if(text_name.getText().isEmpty()) { 
				input+="Please enter the name\n";
				JOptionPane.showMessageDialog(null, input);
				return;
				}
			if(text_email.getText().isEmpty()){   
				input+="Please enter the email\n";
				JOptionPane.showMessageDialog(null, input);
				return;
				}
			boolean v =text_npsd.getText().isEmpty();
			if (v==false) {
				if (verification_changepassword()) {
					PS = con.prepareStatement("UPDATE Personne SET nom= ? , prenom=?,cin =?,dateofbirth=?,phone=?,email=?,country=?,urlImg=?, password=? WHERE id_P ="+c+"");
					functsetter();
					PS.setString(9, text_npsd.getText());
					PS.execute();
					JOptionPane.showMessageDialog(null, "Modification saved successefully");
				} else {
					JOptionPane.showMessageDialog(null, erreurPassword);
				}
			} else {
					PS = con.prepareStatement("UPDATE Personne SET nom= ? , prenom=?,cin =?,dateofbirth=?,phone=?,email=?,country=?,urlImg=? WHERE id_P ="+c+"");
					functsetter();
					PS.execute();
				JOptionPane.showMessageDialog(null, "Modification saved successefully");
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    String erreurPassword=null;
    
    boolean verification_changepassword() {
    	con=DBConnection.getConnection();
    	try {
				PS = con.prepareStatement("Select password from Personne WHERE id_p="+c+"");
				ResultSet rs = PS.executeQuery();
				rs.next();
				if(!text_psd.getText().equals(rs.getString("password"))) { 
					erreurPassword="the current password is not correct";
					return false;
				}	
				
				if(!text_npsd.getText().equals(text_rnpsd.getText())) {
					erreurPassword="the retype of the new password is not correct";
					return false;
				}
				return true;
				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
    	return false;	
    }
	 
	 //delet the account.
	    @FXML
	    void deletebt(ActionEvent event) {
	    	con=DBConnection.getConnection();
			try {
				PS = con.prepareStatement("DELETE FROM Personne WHERE id_P ="+c+"");
				PS.execute();
				JOptionPane.showMessageDialog(null, "Very sad to see you go",null, JOptionPane.INFORMATION_MESSAGE);
				Register_Login.currentId= 0;
				closecurr(event);
				Parent root = FXMLLoader.load(getClass().getResource("Interface.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("ADV");
                stage.setScene(scene);
                stage.show();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
	    	
	    	
	    	
	    }
	    
	    @FXML
	    private Circle thecircle;
	    
	    @FXML
	    void uplimg(ActionEvent event) throws FileNotFoundException {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Choose Image");
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			InputStream stream = new FileInputStream(selectedFile.toString());
			streamglo = selectedFile.toString();
		    Image image = new Image(stream);
		    thecircle.setFill(new ImagePattern(image));
		    thecircle.setEffect(new DropShadow(+25d,0d,+2d,Color.DARKORANGE));
	    }
	    
	    
	    public void getUserInformations() {
	    	Connection con=DBConnection.getConnection();
			try {
				PreparedStatement PS = con.prepareStatement("Select * FROM Personne WHERE id_P="+c+"");
				//PS.setString(1,); l'id li ghanakhed
				ResultSet rs = PS.executeQuery();
				rs.next();
			    text_name.setText(rs.getString("nom"));
			    text_surname.setText(rs.getString("prenom"));
			    text_cin.setText(rs.getString("cin"));
			    //je suis pas sur !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			    text_dob.setValue(rs.getDate("dateofbirth").toLocalDate());
			    //text_dob.setValue(dateFormat.format(rs.getDate("date of birth")));
			    text_country.setText(rs.getString("country"));
			    //convert int to String
			    text_phone.setText(String.valueOf(rs.getInt("phone")));
			    text_email.setText(rs.getString("email"));
			    //url de l'image !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			    InputStream stream = new FileInputStream(rs.getString("urlImg"));
			    Image image = new Image(stream);
			    thecircle.setFill(new ImagePattern(image));
			    thecircle.setEffect(new DropShadow(+25d,0d,+2d,Color.DARKORANGE));
				// A voir apres
				/*PS = con.prepareStatement("////////////");
				rs = PS.executeQuery();
				nbrTravels.setText(Integer.toString(rs.getInt(1)));
				PS = con.prepareStatement("////////");
				rs = PS.executeQuery();
				nbrCity.setText(Integer.toString(rs.getInt(1)));*/
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}

		}
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			getUserInformations();
			
		}
		
		@FXML
	    public void closecurr(ActionEvent event) {
	    	Stage stage = (Stage) btndelete.getScene().getWindow(); 
	    	stage.close(); 
	    }
	    


}