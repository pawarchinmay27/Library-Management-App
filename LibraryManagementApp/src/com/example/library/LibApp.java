package com.example.library;

import java.io.IOException;

import com.example.library.view.BookListController;
import com.example.library.view.LoginController;
import com.example.library.view.NewBookController;
import com.example.library.view.RegisterController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LibApp extends Application {

	private Stage primaryStage;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		System.out.println("Init called");
		System.out.println(Thread.currentThread().getName());
		//called before start on JavaFX-Launcher thread
		//good place to initialize non-gui objects required in your application
	}
	
	@Override
	public void start(Stage primaryStage) {
		//runs on the JavaFX application thread after init
		System.out.println("Start called");
		System.out.println(Thread.currentThread().getName());
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Library Management App");
		primaryStage.setWidth(600);
		primaryStage.setHeight(400);
		primaryStage.show();//makes the stage visible
		
		showLoginScene();
	}

	public void showLoginScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LibApp.class.getResource("view/Login.fxml"));
		GridPane gridPane;
		try {
	
			gridPane = loader.load();
			
			//get the controller associated with the view
			LoginController controller = loader.getController();
			//set the app instance
			controller.setApp(this);
			
			//create the Scene with the GridPane as the parent
			Scene scene = new Scene(gridPane);
			
			//set the scene on the stage
			this.primaryStage.setScene(scene);
			
		} catch (IOException e) {
			System.out.println("Loader failed!");
			e.printStackTrace();
			return;
		}
	
	}

	public void showRegisterScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LibApp.class.getResource("view/Register.fxml"));
		GridPane gridPane;
		try {
	
			gridPane = loader.load();
			
			//ask for the controller after loading the FXML file
			//get the controller associated with the view
			RegisterController controller = loader.getController();
			//set the LibApp reference
			controller.setApp(this);
			
			//create the Scene with the GridPane as the parent
			Scene scene = new Scene(gridPane);
			
			//set the scene on the stage
			this.primaryStage.setScene(scene);
			
		} catch (IOException e) {
			System.out.println("Loader failed!");
			e.printStackTrace();
			return;
		}
	
	}

	public void showBookListScene(){
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LibApp.class.getResource("view/BookList.fxml"));
		AnchorPane anchorPane;
		try {
	
			anchorPane = loader.load();
			
			//ask for the controller after loading the FXML file
			//get the controller associated with the view
			//RegisterController controller = loader.getController();
			//set the LibApp reference
			//controller.setApp(this);
			
			BookListController controller = loader.getController();
			controller.setLibApp(this);
			
			Scene scene = new Scene(anchorPane);
			
			//set the scene on the stage
			this.primaryStage.setScene(scene);
			
		} catch (IOException e) {
			System.out.println("Loader failed!");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * This function will show a scene related to adding new books but in an entirely different stage
	 */
	public void showNewBookStage(BookListController bookListController){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LibApp.class.getResource("view/NewBook.fxml"));
		AnchorPane anchorPane;
		try {
	
			anchorPane = loader.load();
			
			NewBookController controller = loader.getController();
			
			Scene scene = new Scene(anchorPane);
			
			Stage stage = new Stage();
			stage.setTitle("New Book");
			stage.setScene(scene);
			stage.initOwner(primaryStage);
			//set the new stage associated with newBook scene
			controller.setStage(stage);
			controller.setBookListController(bookListController);
			
			stage.show();
			
			
		} catch (IOException e) {
			System.out.println("Loader failed!");
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		//just before destroying the JavaFx Application
		//good place to release the resources that were initialized during init
		System.out.println("Stop called");
		System.out.println(Thread.currentThread().getName());//JavaFX Application Thread
	}
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());//main thread
		launch(args);//returns only when stage is closed
		System.out.println("Program ends!");
	}
	
	
}
