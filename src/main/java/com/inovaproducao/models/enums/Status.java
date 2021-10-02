package com.inovaproducao.models.enums;

public enum Status {
	ACTIVE(1),
	INACTIVE(2);
	
	private Integer id;
	
	Status(Integer id) {
		this.id = id;
	}
	
	public Integer getValue() {
		return this.id;
	}
}
