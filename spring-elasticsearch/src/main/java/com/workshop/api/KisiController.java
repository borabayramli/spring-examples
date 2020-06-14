package com.workshop.api;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.entity.Kisi;
import com.workshop.repository.KisiRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kisi")
public class KisiController {

	private final KisiRepository kisiRepository;


	@PostConstruct
	public void init() {
		Kisi kisi = new Kisi();
		kisi.setAd("Elastic");
		kisi.setSoyad("Search");
		kisi.setAdres("ev");
		kisi.setDogumTarihi(Calendar.getInstance().getTime());
		kisi.setId("K0001");
		kisiRepository.save(kisi);

		kisi.setAd("this is Elastic search test");
		kisi.setSoyad("Search");
		kisi.setAdres("ev");
		kisi.setDogumTarihi(Calendar.getInstance().getTime());
		kisi.setId("K0002");
		kisiRepository.save(kisi);

	}
	
	@GetMapping("/{search}")
	public ResponseEntity<List<Kisi>> getKisiEntity(@PathVariable String search) {
		List<Kisi> kisiler = kisiRepository.getByCustomQuery(search);
		return ResponseEntity.ok(kisiler);
	}

	/*
	 * @RequiredArgsConstructor notasyonu constructor oluşturuyor. public
	 * KisiController(KisiRepository kisiRepository) { this.kisiRepository =
	 * kisiRepository; }
	 */

}
