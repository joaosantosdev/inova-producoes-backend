package com.inovaproducao.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 100, nullable = false)
	private String path;
	
	@Column(length = 200)
	private String imgPath;

	@ManyToOne
	@JsonIgnore
	private Band band;

	@Column(nullable = false)
	private Integer status = 1;

	@Column(nullable = false)
	private LocalDateTime dateCreated;

	@Column(nullable = true)
	private LocalDateTime dateUpdated;

	@OneToMany(mappedBy="playlist")
	private List<Music> musics = new ArrayList<Music>();
}
