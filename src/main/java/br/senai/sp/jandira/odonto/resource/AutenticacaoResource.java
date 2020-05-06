package br.senai.sp.jandira.odonto.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.jandira.odonto.resource.dto.TokenDto;
import br.senai.sp.jandira.odonto.resource.form.FormLogin;
import br.senai.sp.jandira.odonto.security.TokenService;;

@RestController
@RequestMapping("/auth")
public class AutenticacaoResource {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid FormLogin form){
		
		// Representa um username e uma senha (dados que foram enviados)
		UsernamePasswordAuthenticationToken dadosLogin = 
				new UsernamePasswordAuthenticationToken(form.getEmail(), form.getSenha());
		
		try {
			// Representa o token para uma requisição de autenticação
			Authentication authentication = authManager.authenticate(dadosLogin);
			
			String token = tokenService.gerarToken(authentication);
	
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}


