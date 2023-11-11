package com.crud.CRUD.java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //Clase Controlador REST, puede interceptar peticiones al servidor.
public class HelloWorldController {

	@RequestMapping("hello") //Metodo interceptar una llamada al servidor, en este caso, a /hello.
	public String helloWorld(@RequestParam(value="name", defaultValue="World") //habilitar este argumento como parámetro del servicio
		String name) { 
		return "Hello "+name+"!!";
	}
}
