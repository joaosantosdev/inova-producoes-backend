package com.inovaproducao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovaproducao.models.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

	public List<Music> findByStatus(Integer status);

	public Music findByPath(String path);
	
	@Query(nativeQuery = true, value = "DELETE FROM music m WHERE m.playlist_id = :playlistId")
	public void deleteByPlaylistId(Long playlistId);

}
