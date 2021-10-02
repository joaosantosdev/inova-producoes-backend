package com.inovaproducao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovaproducao.models.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
	
	public Band findByPath(String path);
	public List<Band> findByStatus(Integer status);

}
