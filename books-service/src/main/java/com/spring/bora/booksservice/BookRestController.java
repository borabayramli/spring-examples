package com.spring.bora.booksservice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BookRestController {
	
	private Map<Integer, Book> myBooksMap = new HashMap<Integer, Book>();
	
	@PostConstruct
	public void init() {
		myBooksMap.put(1, new Book(1, "Benim adım kırmızı"));
		myBooksMap.put(2, new Book(2, "Satranç"));
		myBooksMap.put(3, new Book(3, "Başlangıç"));
	}
	
	@RequestMapping(value = "/all")
	public Collection<Book> getAll(){
		log.info("/all running");
		
		return myBooksMap.values();
	}
	
	@RequestMapping(value = "{id}")
	public Book checkedOut(@PathVariable("id") Integer id) {
		log.info("/checkedOut running");
		
		return myBooksMap.get(id);
	}

}
