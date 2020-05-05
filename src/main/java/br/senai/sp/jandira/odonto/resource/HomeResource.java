package br.senai.sp.jandira.odonto.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeResource {

	@GetMapping
	public String index() {
		return "<h3>Hello World!</h3>";
	}
	
}
