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
import com.inovaproducao.models.Playlist;
import com.inovaproducao.models.dto.PlaylistFormDTO;
import com.inovaproducao.services.PlaylistService;

@RequestMapping("/playlist")
@RestController
public class PlaylistController {

	@Autowired
	private PlaylistService service;

	@GetMapping("/{id}")
	public ResponseEntity<Playlist> findById(@PathVariable Long id) throws RestError {
		return ResponseEntity.ok(this.service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Playlist>> findAllOnlyActive() {
		return ResponseEntity.ok(this.service.findAllOnlyActive());
	}

	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody PlaylistFormDTO playlistFormDTO) throws RestError {
		this.service.save(playlistFormDTO);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody PlaylistFormDTO playlistFormDTO)
			throws RestError {
		this.service.update(id, playlistFormDTO);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/image")
	public ResponseEntity<Void> uploadImage(@PathVariable Long id, @RequestParam MultipartFile imgFile)
			throws RestError, IOException {
		this.service.saveImage(id, imgFile);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{id}/upload-musics")
	public ResponseEntity<Void> uploadMusics(@PathVariable Long id, @RequestParam MultipartFile [] musicsFile)
			throws RestError, IOException {
		this.service.uploadMusics(id, musicsFile);
		return ResponseEntity.noContent().build();
	}

}
