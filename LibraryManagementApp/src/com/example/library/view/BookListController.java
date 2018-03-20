package com.example.library.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.example.library.Constants;
import com.example.library.LibApp;
import com.example.library.model.Book;
import com.example.library.util.DbUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BookListController {

	private LibApp libApp;
	
	// table binding use observable list
	private ObservableList<Book> books;
	
	@FXML
	private TableView<Book> tableView;
	
	@FXML
	private TableColumn<Book, String> title2;
	
	@FXML
	private TableColumn<Book, Float> price2;

	@FXML
	private Label idlbl;

	@FXML
	private Label titlelb1;

	@FXML
	private Label pageslb1;

	@FXML
	private Label priceslb1;

	@FXML
	private Label authorslb1;

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
		// initialize() method called after the view has been drawn
		books = FXCollections.observableArrayList();// initialize the list
		setBookDetails(null);
		
		Connection conn = DbUtil.getConnection(Constants.DBURL, Constants.USERNAME, Constants.PASSWORD);
		String query = "select * from books";
		PreparedStatement ps = conn.prepareStatement(query);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			long id = rs.getInt("id");

			String title = rs.getString("title");

			float price = rs.getFloat("price");

			int pages = rs.getInt("pages");

			String authors = rs.getString("authors");

			Book b = new Book(id, title, price, pages, authors);
			books.add(b);

		}
		rs.close();
		conn.close();
		// associate the observable list with tableview
		tableView.setItems(books);
		// bind the book properties with tablecolumn
		// getvalue will return you the book object
		// for every row it gets a new book reference

		title2.setCellValueFactory((celldata) -> celldata.getValue().getTitle());

		// for anything apart from string property call asobject() at end
		price2.setCellValueFactory((celldata) -> celldata.getValue().getPrice().asObject());

		// for click on any book(row) on table actionlistener prints the object
		// name
		// newvalue is the latest selected row

		tableView.getSelectionModel().selectedItemProperty().addListener((o, oldvalue, newvalue) -> {

			setBookDetails(newvalue);
		});

	}
	
	
	public void setLibApp(LibApp libApp) {
		this.libApp = libApp;
	}

	public void setBookDetails(Book book){
		
		if(book != null){
		
			idlbl.setText(book.getId().get() + "");
			// .get() to convert in java primitives
			titlelb1.setText(book.getTitle().get() + "");
			priceslb1.setText(book.getPrice().get() + "");
			pageslb1.setText(book.getPages().get() + "");
			authorslb1.setText(book.getAuthors().get() + "");
			System.out.println(book.getTitle());

		}else{
			
			idlbl.setText("");
			titlelb1.setText("");
			priceslb1.setText("");
			pageslb1.setText("");
			authorslb1.setText("");
		}
		
	}
	
	@FXML
	private void onDelete(){
		//confirm the user before deleting the book
		Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
		confirmAlert.setTitle("Confirm Please!");
		confirmAlert.setHeaderText("Are you sure?");
		confirmAlert.setContentText("You want to go ahead and Delete the book?");
		Optional<ButtonType> buttonClicked = confirmAlert.showAndWait();
		
		if(buttonClicked.get() == ButtonType.OK){

			Book bookToDelete = this.tableView.getSelectionModel().getSelectedItem();
			
			if(bookToDelete != null){
				String query = "Delete from  books where id = ?";
				try {
					Connection conn = DbUtil.getConnection(Constants.DBURL, Constants.USERNAME, Constants.PASSWORD);
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setLong(1, bookToDelete.getId().get());
					int rowsDeleted = ps.executeUpdate();
					
					if(rowsDeleted <= 0){
						//TODO: show an alert dialog with error
					}else{
						int index = this.tableView.getSelectionModel().getSelectedIndex();
						this.books.remove(index);
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					//show an alert dialog with error
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@FXML
	public void onNew(){
		this.libApp.showNewBookStage(this);
	}
	
	public ObservableList<Book> getBooks(){
		return books;
	}
	
}
