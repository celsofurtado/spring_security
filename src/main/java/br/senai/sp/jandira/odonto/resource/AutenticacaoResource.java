package br.senai.sp.jandira.odonto.resource;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.jandira.odonto.resource.form.FormLogin;;

@RestController
@RequestMapping("/auth")
public class AutenticacaoResource {
	
	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid FormLogin form){
		
		System.out.println(form.getEmail());
		System.out.println(form.getSenha());
		
		return ResponseEntity.ok().build();
		
	}

}
