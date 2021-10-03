package com.inovaproducao.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inovaproducao.exceptions.RestError;
import com.inovaproducao.models.Band;
import com.inovaproducao.models.dto.BandFormDTO;
import com.inovaproducao.models.enums.Status;
import com.inovaproducao.repositories.BandRepository;
import com.inovaproducao.utils.ImageBuilder;
import com.inovaproducao.utils.Utils;

@Service
public class BandService {
	
	@Value("${uploads.bands_folder}")
	private String bandsImagesFolder;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private BandRepository repository;
	
	public List<Band> findAllOnlyActive(){
		return this.repository.findByStatus(Status.ACTIVE.getValue());
	}

	public Band findById(Long id) throws RestError {
		return this.repository.findById(id)
				.orElseThrow(() -> RestError.notFound("Banda não encontrada"));
	}
	
	public Band save(BandFormDTO bandForm) throws RestError {
		Band band = bandForm.toEntity();

		if (Utils.isStatusInvalid(bandForm.getStatus())) {
			throw RestError.badRequest("Status inválido");
		}
		
		if(this.repository.findByPath(band.getPath()) != null) {
			throw RestError.badRequest("Path da banda já cadastrado");
		}
		
		band.setDateCreated(LocalDateTime.now());
		this.repository.save(band);
		
		return band;
	}
	
	public void update(Long id, BandFormDTO bandForm) throws RestError {
		
		if (Utils.isStatusInvalid(bandForm.getStatus())) {
			throw RestError.badRequest("Status inválido");
		}
		
		Band bandPath = this.repository.findByPath(bandForm.getPath());
		
		if(bandPath != null && !bandPath.getId().equals(id)) {
			throw RestError.badRequest("Path da banda já cadastrado");
		}
		
		Band band = this.findById(id);
		band.setName(bandForm.getName());
		band.setPath(bandForm.getPath());
		band.setDateUpdated(LocalDateTime.now());
		band.setStatus(bandForm.getStatus());
		this.repository.save(band);
	}
	
	public void saveImagens(Long bandId, MultipartFile imgFile, MultipartFile bannerFile) throws IOException, RestError {
		
		Band band = this.findById(bandId);
		
		InputStream imgStream = ImageBuilder.load(imgFile)
		.pngToJpg()
		.toInputStream("jpg");
		
		InputStream bannerStream = ImageBuilder.load(bannerFile)
				.pngToJpg()
				.toInputStream("jpg");
		
		String bannerPath = "banner_"+band.getId();
		String imgPath = "img_"+band.getId();

		this.storageService.saveImage(bannerPath, bannerStream, this.bandsImagesFolder);
		this.storageService.saveImage(imgPath, imgStream, this.bandsImagesFolder);
		
		band.setBannerPath(bannerPath);
		band.setImgPath(bannerPath);

		this.repository.save(band);
	}

}
