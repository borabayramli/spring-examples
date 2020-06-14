package com.workshop.api;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.message.ReusableMessage;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.entity.Shirt;
import com.workshop.repository.ShirtRepository;
import com.workshop.service.ISearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shirt")
public class ShirtController {

	//@Autowired
	private final ShirtRepository shirtRepository;
	
	@Autowired
	private ISearchService searchService;
	
	@PostConstruct
	public void init() {
		Shirt shirt = new Shirt();
		shirt.setName("tshirt1");
		shirt.setSize("M");
		shirt.setColor("black");
		shirt.setFabric("cotton");
		shirt.setPrice(10d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt2");
		shirt.setSize("M");
		shirt.setColor("yellow");
		shirt.setFabric("cotton");
		shirt.setPrice(10d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt3");
		shirt.setSize("M");
		shirt.setColor("red");
		shirt.setFabric("cotton");
		shirt.setPrice(30d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt4");
		shirt.setSize("M");
		shirt.setColor("white");
		shirt.setFabric("cotton");
		shirt.setPrice(50d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt5");
		shirt.setSize("S");
		shirt.setColor("black");
		shirt.setFabric("cotton");
		shirt.setPrice(40d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt6");
		shirt.setSize("S");
		shirt.setColor("red");
		shirt.setFabric("cotton");
		shirt.setPrice(80d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt7");
		shirt.setSize("S");
		shirt.setColor("brown");
		shirt.setFabric("cotton");
		shirt.setPrice(120d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt8");
		shirt.setSize("L");
		shirt.setColor("white");
		shirt.setFabric("cotton");
		shirt.setPrice(70d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt9");
		shirt.setSize("L");
		shirt.setColor("brown");
		shirt.setFabric("cotton");
		shirt.setPrice(150d);
		shirtRepository.save(shirt);
		
		shirt.setName("tshirt10");
		shirt.setSize("L");
		shirt.setColor("yellow");
		shirt.setFabric("cotton");
		shirt.setPrice(20d);
		shirtRepository.save(shirt);
	}
	
	@GetMapping("/{search}")
	public ResponseEntity<List<Shirt>> getShirtEntity(@PathVariable String search) {
		//List<Shirt> shirts = shirtRepository.findByNameLike(search);
		List<Shirt> shirts = searchService.getShirtByNameLike(search);
		return ResponseEntity.ok(shirts);
	}
	
	@GetMapping("/price/{priceLow}/{priceUp}")
	public ResponseEntity<List<Shirt>> getShirtsLowerThanPrice(@PathVariable Double priceLow, @PathVariable Double priceUp) {
		List<Shirt> shirts = searchService.getShirtListBetweenPrice(priceLow, priceUp);
		return ResponseEntity.ok(shirts);
	}
	
	@GetMapping("/custom/{search}")
	public ResponseEntity<List<Shirt>> getShirtsCustomerSearch(@PathVariable String search) {
		List<Shirt> shirts = searchService.getShirtsCustomSearch();
		return ResponseEntity.ok(shirts);
	}
	
	@GetMapping("/agg/{search}")
	public SearchResponse getShirtsAggSearch() {
		return searchService.getShirtsAggregationSearch();
	}
	
}
