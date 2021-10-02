package com.inovaproducao.utils;

import com.inovaproducao.models.enums.Status;

public class Utils {
	
	public static boolean isStatusInvalid(Integer status) {	
		return !(Status.ACTIVE.getValue().equals(status) || Status.INACTIVE.getValue().equals(status));
	}

}
