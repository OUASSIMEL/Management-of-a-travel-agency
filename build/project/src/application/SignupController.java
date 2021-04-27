package application;

import java.io.IOException;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignupController {
	@FXML
	private TextField fullname;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private Button btnsignup;
	
	public String getFullName() {
		return fullname.getText();
	}
	public String getEmail() {
		return email.getText();
	}
	public String getPassword() {
		return password.getText();
	}
	public void HandleEvents(MouseEvent event) throws ClassNotFoundException, IOException {
		Register nRegister = new Register();
		nRegister.jButton_RegisterActionPerformed(event,getFullName(),getEmail(),getPassword());
	}
	
}

