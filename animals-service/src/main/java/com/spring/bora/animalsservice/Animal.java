package com.spring.bora.animalsservice;

import lombok.Getter;
import lombok.Setter;

public class Animal {

	public Animal(int id, String name) {
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
