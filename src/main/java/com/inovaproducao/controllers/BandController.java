package com.inovaproducao.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inovaproducao.exceptions.RestError;
import com.inovaproducao.models.Band;
import com.inovaproducao.models.dto.BandFormDTO;
import com.inovaproducao.services.BandService;

@RestController
@RequestMapping(value = "/bands")
public class BandController {

	@Autowired
	private BandService service;

	@GetMapping()
	public ResponseEntity<List<Band>> findAllOnlyActive() throws RestError {
		return ResponseEntity.ok(this.service.findAllOnlyActive());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Band> findById(@PathVariable Long id) throws RestError {
		return ResponseEntity.ok(this.service.findById(id));
	}

	@PostMapping()
	public ResponseEntity<Void> save(@Valid @RequestBody BandFormDTO bandForm) throws RestError {
		this.service.save(bandForm);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/images")
	public ResponseEntity<Void> saveImages(@PathVariable Long id, @RequestParam("imgFile") MultipartFile imgFile,
			@RequestParam("bannerFile") MultipartFile bannerFile) throws IOException, RestError {
		this.service.saveImagens(id, imgFile, bannerFile);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> save(@PathVariable Long id, @Valid @RequestBody BandFormDTO bandForm) throws RestError {
		this.service.update(id, bandForm);
		return ResponseEntity.noContent().build();
	}
}
