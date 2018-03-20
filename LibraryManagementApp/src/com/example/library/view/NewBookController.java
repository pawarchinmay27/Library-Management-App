package com.example.library.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.library.Constants;
import com.example.library.model.Book;
import com.example.library.util.DbUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewBookController {

	private Stage stage;
	
	private BookListController bookListController;
	
	@FXML
	private TextField mTFTitle;
	
	@FXML
	private TextField mTFPages;
	
	@FXML
	private TextField mTFPrice;
	
	@FXML
	private TextField mTFAuthors;
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public void setBookListController(BookListController bookListController) {
		this.bookListController = bookListController;
	}
	
	@FXML
	private void onCancel(){
		this.stage.close();
	}
	
	@FXML
	private void onSave(){
		
		String title = mTFTitle.getText(),
				authors = mTFAuthors.getText();
		
		int pages = Integer.parseInt(mTFPages.getText());
		float price = Float.parseFloat(mTFPrice.getText());
		
		try {
			Connection conn = DbUtil.getConnection(Constants.DBURL, Constants.USERNAME, Constants.PASSWORD);
			String insertBook = "INSERT INTO books(title, price, pages, authors) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = conn.prepareStatement(insertBook);
			preparedStatement.setString(1, title);
			preparedStatement.setFloat(2, price);
			preparedStatement.setInt(3, pages);
			preparedStatement.setString(4, authors);
			int res = preparedStatement.executeUpdate();//Insert is a DML 
			
			
			
			if(res == 1 ){
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Successfully entered a book");
				alert.initOwner(stage);
				alert.showAndWait();
			
				//to get the last inserted id
				String query = "SELECT MAX(id) as MAX_ID from books";
				preparedStatement = conn.prepareStatement(query);
				ResultSet rs = preparedStatement.executeQuery();
				rs.next();
				long id = rs.getLong("MAX_ID");
				Book book = new Book(id, title, price, pages, authors);
				this.bookListController.getBooks().add(book);
				
				
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Error in insertion of the record");
				alert.initOwner(stage);
				alert.showAndWait();
			}
			
			this.stage.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setContentText("Error in connecting with the db");
			//set the stage it is associated with
			alert.initOwner(stage);
			alert.showAndWait();
		}
		
	}
	
	
}
