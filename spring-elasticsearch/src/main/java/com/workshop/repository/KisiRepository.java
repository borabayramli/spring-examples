package com.workshop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.workshop.entity.Kisi;

@Repository
public interface KisiRepository extends ElasticsearchRepository<Kisi, String>{

	@Query("{\"bool\": {\"must\": [ {\"match\": {\"ad\": \"%?0\"}}]}} ")
	List<Kisi> getByCustomQuery(String search);
	
	List<Kisi> findByAdLikeOrSoyadLike(String ad, String soyad);
	
	List<Kisi> findByAdIsLikeOrSoyadIsLike(String ad, String soyad);
	
	List<Kisi> findByDogumTarihi(Date dogumTarihi);

}