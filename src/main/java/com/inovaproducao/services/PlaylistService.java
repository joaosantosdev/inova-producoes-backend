package com.inovaproducao.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inovaproducao.exceptions.RestError;
import com.inovaproducao.models.Band;
import com.inovaproducao.models.Music;
import com.inovaproducao.models.Playlist;
import com.inovaproducao.models.dto.MusicFormDTO;
import com.inovaproducao.models.dto.PlaylistFormDTO;
import com.inovaproducao.models.enums.Status;
import com.inovaproducao.repositories.PlaylistRepository;
import com.inovaproducao.utils.ImageBuilder;
import com.inovaproducao.utils.Utils;

@Service
public class PlaylistService {

	@Autowired
	private BandService bandService;

	@Autowired
	private PlaylistRepository repository;

	@Autowired
	private StorageService storageService;

	@Autowired
	private MusicService musicService;

	@Value("${uploads.playlist_folder}")
	private String playlistImagesFolder;

	public List<Playlist> findAllOnlyActive() {
		return this.repository.findByStatus(Status.ACTIVE.getValue());
	}

	public Playlist findById(Long id) throws RestError {
		return this.repository.findById(id).orElseThrow(() -> RestError.notFound("Playlist não encontrada"));
	}

	public void update(Long id, PlaylistFormDTO playlistFormDTO) throws RestError {

		if (Utils.isStatusInvalid(playlistFormDTO.getStatus())) {
			throw RestError.badRequest("Status inválido");
		}

		Playlist playlistPath = this.repository.findByPath(playlistFormDTO.getPath());

		if (playlistPath != null && !playlistPath.getId().equals(id)) {
			throw RestError.badRequest("Path da playlist já cadastrado");
		}

		Playlist playlist = this.findById(id);
		this.musicService.deleteByPlaylistId(id);

		List<Music> musics = playlistFormDTO.getMusics().stream().map(MusicFormDTO::toEntity)
				.collect(Collectors.toList());


		if (!playlist.getBand().getId().equals(playlistFormDTO.getBandId())) {
			Band band = this.bandService.findById(playlistFormDTO.getBandId());
			playlist.setBand(band);
		}

		playlist.setName(playlistFormDTO.getName());
		playlist.setPath(playlistFormDTO.getPath());
		playlist.setDateUpdated(LocalDateTime.now());
		playlist.setStatus(playlistFormDTO.getStatus());
		this.repository.save(playlist);

		this.musicService.saveAll(playlist, musics);

	}

	public void save(PlaylistFormDTO playlistFormDTO) throws RestError {

		if (Utils.isStatusInvalid(playlistFormDTO.getStatus())) {
			throw RestError.badRequest("Status inválido");
		}

		if (this.repository.findByPath(playlistFormDTO.getPath()) != null) {
			throw RestError.badRequest("Path da banda já cadastrado");
		}

		List<Music> musics = playlistFormDTO.getMusics().stream().map(MusicFormDTO::toEntity)
				.collect(Collectors.toList());


		Band band = this.bandService.findById(playlistFormDTO.getBandId());
		Playlist playlist = playlistFormDTO.toEntity();
		playlist.setBand(band);
		playlist.setDateCreated(LocalDateTime.now());
		playlist.setMusics(musics);
		this.repository.save(playlist);
		this.musicService.saveAll(playlist, musics);

	}

	public void saveImage(Long playlistId, MultipartFile imgFile) throws IOException, RestError {

		Playlist playlist = this.findById(playlistId);

		InputStream imgStream = ImageBuilder.load(imgFile).pngToJpg().toInputStream("jpg");

		String imgPath = "img_" + playlist.getId();

		this.storageService.saveImage(imgPath, imgStream, this.playlistImagesFolder);

		playlist.setImgPath(imgPath);

		this.repository.save(playlist);
	}
	
	public void uploadMusics(Long playlistId, MultipartFile[] musicsFile) throws RestError, IOException, IllegalStateException {
		Playlist playlist = this.findById(playlistId);
		if(playlist.getMusics().size() != musicsFile.length) {
			throw RestError.badRequest("File não corresponde ao tamanho da playlist");
		}
		
		Path path = Path.of("uploads").resolve(this.playlistImagesFolder).resolve(playlistId.toString());
		this.storageService.deleteDirectory(path);
		this.storageService.createDirectory(path);
		
		for(int i = 0; i < musicsFile.length; i++) {
			MultipartFile musicFile = musicsFile[i];
			Music music = playlist.getMusics().get(i);
			Path pathMusic = path.resolve(music.getPath()+".mp3");
			InputStream inputStream = new ByteArrayInputStream(musicFile.getBytes());
			this.storageService.saveFile(inputStream, pathMusic);
		}
	}

}
