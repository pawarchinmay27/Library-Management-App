package com.example.library.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.library.Constants;
import com.example.library.LibApp;
import com.example.library.util.DbUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
	
	private LibApp app;
	
	@FXML
	private TextField textFieldUserName;
	
	@FXML
	private PasswordField passwordField;
	
	public void setApp(LibApp app){
		this.app = app;
	}
	
	@FXML
	private void onRegisterClick(){
		this.app.showRegisterScene();
	}
	
	@FXML
	private void onResetButtonClick(){
		this.textFieldUserName.setText("");
		this.passwordField.setText("");
	}
	
	@FXML
	private void onLoginClick(){
		String username  = this.textFieldUserName.getText();
		String password = this.passwordField.getText();
		
		try {
			Connection connection = DbUtil.getConnection(Constants.DBURL, Constants.USERNAME, Constants.PASSWORD);
			String query = "Select username from users where username = ? and password = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				
				this.app.showBookListScene();
				
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Invalid user");
				alert.initOwner(this.app.getPrimaryStage());
				alert.showAndWait();
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
