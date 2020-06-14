package com.workshop.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.workshop.entity.Shirt;

@Repository
public interface ShirtRepository extends ElasticsearchRepository<Shirt, String> {

	List<Shirt> findByNameLike(String name);
	
	List<Shirt> findByPriceBetween(Double priceLow, Double priceUp);
	
}
