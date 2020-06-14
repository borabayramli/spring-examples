package com.workshop.service;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import com.workshop.entity.Kisi;
import com.workshop.entity.Shirt;

public interface ISearchService {

	public List<Kisi> customSearchKisi(String search);
	
	public List<Shirt> getShirtByNameLike (String nameSearch);
	
	public List<Shirt> getShirtListBetweenPrice(Double priceLow, Double priceUp);
	
	public List<Shirt> getShirtsCustomSearch();
	
	public SearchResponse getShirtsAggregationSearch();

}
