package com.example.library.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.library.Constants;
import com.example.library.LibApp;
import com.example.library.util.DbUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {

	private LibApp app;
	
	@FXML
	private ComboBox<String> countries;
	
	@FXML
	private RadioButton mRBMale;
	
	@FXML
	private RadioButton mRBFemale;
	
	@FXML
	private CheckBox mCBTravel; 
	
	@FXML
	private CheckBox mCBShopping; 
	
	@FXML
	private CheckBox mCBSports;
	
	@FXML
	private TextField mTFUsername;
	
	@FXML
	private PasswordField mPasswordField;
	
	public void setApp(LibApp app){
		this.app = app;
	}
	
	@FXML
	private void onLoginClick(){
		this.app.showLoginScene();
	}

	@FXML
	private void onRegisterClick(){
		
		String gender = mRBMale.isSelected() ? mRBMale.getText() : mRBFemale.getText();
		String username = mTFUsername.getText();
		String password = mPasswordField.getText();
		String preferences = mCBShopping.isSelected() ? mCBShopping.getText() +" ": "";
		
		preferences+= mCBSports.isSelected()? mCBSports.getText() +" ": "";
		preferences+= mCBTravel.isSelected() ? mCBTravel.getText():"";
		
		boolean lovesTravel = mCBTravel.isSelected(),
				lovesShopping = mCBShopping.isSelected(),
				lovesSports = mCBSports.isSelected();
		
		//String country = countries.getValue();
		
		String country = countries.getSelectionModel().getSelectedItem();
		
		System.out.println("Username: "+username +"\nPassword: "+password
				+"\nGender: "+gender+"\nCountry: "+country+"\nPreferences: "+preferences);
		
		System.out.println("Travel: "+lovesTravel+"\nShopping: "+lovesShopping+"\nSports: "+lovesSports);
		
		try {
			Connection conn = DbUtil.getConnection(Constants.DBURL, Constants.USERNAME, Constants.PASSWORD);
			String insertUser = "INSERT INTO users(username, password, gender, country) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = conn.prepareStatement(insertUser);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, gender);
			preparedStatement.setString(4, country);
			int res = preparedStatement.executeUpdate();//Insert is a DML statement
			
			if(res == 1 ){
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Successfully entered a record");
				alert.initOwner(this.app.getPrimaryStage());
				alert.showAndWait();
				
				
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Error in insertion of the record");
				alert.initOwner(this.app.getPrimaryStage());
				alert.showAndWait();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setContentText("Error in connecting with the db");
			//set the stage it is associated with
			alert.initOwner(this.app.getPrimaryStage());
			alert.showAndWait();
		}
		
		
	}
	
	/**
	 * Called after the corresponding FXML with all the view elements is loaded
	 */
	@FXML
	private void initialize(){
		countries.getItems().addAll("India", "USA", "UK", "Canada");//takes a variance of items
	}
	
}
