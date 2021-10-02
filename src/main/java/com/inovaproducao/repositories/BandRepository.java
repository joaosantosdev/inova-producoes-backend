package com.inovaproducao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovaproducao.models.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
	
	public Band findByPath(String path);

}
