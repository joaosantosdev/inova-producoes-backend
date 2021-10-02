package com.inovaproducao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovaproducao.models.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	
	public Playlist findByPath(String path);
	public List<Playlist> findByStatus(Integer status);
}
