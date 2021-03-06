package com.inovaproducao.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.inovaproducao.models.Music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicFormDTO {
	@NotEmpty(message = "Campo obrigatório.")
	@Length(min = 1, max = 100, message = "Campo deve ter no mínimo 5 caracteres e no máximo 100.")
	private String name;

	@NotEmpty(message = "Campo obrigatório.")
	@Length(min = 1, max = 100, message = "Campo deve ter no mínimo 5 caracteres e no máximo 100.")
	private String path;

	@NotNull(message = "Campo obrigatório.")
	private Integer status;

	public Music toEntity() {
		Music music = new Music();
		music.setPath(this.path);
		music.setName(this.name);
		music.setStatus(this.status);
		return music;
	}
}
