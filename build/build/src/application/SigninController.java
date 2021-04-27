package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SigninController {
	@FXML
	private  TextField email;
	@FXML
	private  PasswordField password;
	@FXML
	private Button btnsignin;
	@FXML
    private CheckBox checkbox;
	
	public  String getEmail() {
		String mailString = email.getText();
		return mailString;
	}
	public  String getPassword() {
		return password.getText();
	}
	public void HandleEvents(MouseEvent event) throws ClassNotFoundException, IOException {
		Login nLogin = new Login();
		nLogin.jButton_LoginActionPerformed(event,getEmail(),getPassword());
	}
	String tempText ;
	@FXML
    void showpassword(ActionEvent event) {
	    if (checkbox.isSelected()){
			  password.setPromptText(password.getText());
			  password.setText(""); 
			  password.setDisable(false);

		  }else {
			  password.setText(password.getPromptText());
			  password.setPromptText("");
			  password.setDisable(false);
		 }
    }
	
}
