package com.inovaproducao.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class StorageService {

	private final Path root = Paths.get("uploads");

	@PostConstruct
	public void initialize() {
		System.out.println("aqui");
		try {
			if(!this.root.toFile().exists()) {
				Files.createDirectory(this.root);
			}
			if(!this.root.resolve("bands").toFile().exists()) {
				Files.createDirectory(this.root.resolve("bands"));
			}
		} catch (Exception e) {

		}
	}
	
	public StorageService() {
		
	}

	public String saveImage(String name, InputStream is, String subpath) throws IOException {
		String ext = "jpg";
		String fileName = name+"."+ext;
		
		Path fileDir = this.root.resolve(subpath).resolve(fileName);
		if(fileDir.toFile().exists()){
			Files.delete(fileDir);
		}
		
		Files.copy(is, fileDir);
		return fileName;
	}

}
