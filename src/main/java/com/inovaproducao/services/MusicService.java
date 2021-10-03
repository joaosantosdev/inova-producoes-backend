package com.inovaproducao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovaproducao.exceptions.RestError;
import com.inovaproducao.models.Music;
import com.inovaproducao.models.Playlist;
import com.inovaproducao.repositories.MusicRepository;
import com.inovaproducao.utils.Utils;

@Service
public class MusicService {
	@Autowired
	private MusicRepository repository;

	public void saveAll(Playlist playlist, List<Music> musics) throws RestError {
		for (Music music : musics) {
			if (Utils.isStatusInvalid(music.getStatus())) {
				throw RestError.badRequest("Status inválido");
			}
			Music musicPath = this.repository.findByPath(music.getPath());

			if (musicPath != null) {
				throw RestError.badRequest("Path da playlist já cadastrado");
			}
			music.setPlaylist(playlist);
		}
		this.repository.saveAll(musics);
	}

	public void deleteByPlaylistId(Long playlistId) {
		this.repository.deleteByPlaylistId(playlistId);
	}

}
