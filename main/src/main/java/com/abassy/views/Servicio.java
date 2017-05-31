package com.abassy.views;

import org.springframework.stereotype.Component;

@Component
public class Servicio {

	public String diHola(String name) {
		return "¡Muy buenas "+ name + "!";
	}
	
}
