package com.inovaproducao.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Band {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 100)
	private String path;

	@Column(length = 200)
	private String imgPath;

	@Column(length = 200)
	private String bannerPath;

	@Column(nullable = false)
	private Integer status;

	@Column(nullable = false)
	private LocalDateTime dateCreated;

	@Column(nullable = true)
	private LocalDateTime dateUpdated;

}
