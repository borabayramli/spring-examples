package com.workshop.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "shirts", type = "shirt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shirt {

	@Id
	String name;
	
	@Field(name = "size", type = FieldType.Keyword)
	String size;
	
	@Field(name = "color", type = FieldType.Keyword)
	String color;
	
	@Field(name = "fabric", type = FieldType.Text)
	String fabric;

	@Field(name = "price", type = FieldType.Double)
	Double price;
}
