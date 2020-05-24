package com.spring.bora.booksservice;

import lombok.Getter;
import lombok.Setter;

public class Book {
	
	public Book(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@Getter
	@Setter
	int id;

	@Getter
	@Setter
	String name;

}
