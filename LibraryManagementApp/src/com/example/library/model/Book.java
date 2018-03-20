package com.example.library.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

	
	
	private LongProperty id;
	
	
	private StringProperty title;
	
	
	private FloatProperty price;
	
	
	private IntegerProperty pages;
	
	
	private StringProperty authors;


public Book(long id,String title,float price,int pages,String authors ){
	
	
	this.id=new SimpleLongProperty(id);

	
	//SimpleLongProperty converts normal variables to fx variables
	this.title=new SimpleStringProperty(title);
	
	this.price=new SimpleFloatProperty(price);
	
	this.pages=new SimpleIntegerProperty(pages);
	
	this.authors=new SimpleStringProperty(authors);
}
	
	public LongProperty getId() {
		return id;
	}


	public void setId(LongProperty id) {
		this.id = id;
	}


	public StringProperty getTitle() {
		return title;
	}


	public void setTitle(StringProperty title) {
		this.title = title;
	}


	public FloatProperty getPrice() {
		return price;
	}


	public void setPrice(FloatProperty price) {
		this.price = price;
	}


	public IntegerProperty getPages() {
		return pages;
	}


	public void setPages(IntegerProperty pages) {
		this.pages = pages;
	}


	public StringProperty getAuthors() {
		return authors;
	}


	public void setAuthors(StringProperty authors) {
		this.authors = authors;
	}
}
