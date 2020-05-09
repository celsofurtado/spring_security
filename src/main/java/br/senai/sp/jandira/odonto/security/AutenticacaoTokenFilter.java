package br.senai.sp.jandira.odonto.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.senai.sp.jandira.odonto.model.Usuario;
import br.senai.sp.jandira.odonto.repository.UsuarioRepository;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Recuperar o token da requisição
		String token = recuperarToken(request);
		
		// Verificar se o token está correto
		boolean valido = tokenService.isTokenValido(token);
		
		if (valido) {
			autenticarCliente(token);
		}
		
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		
		Long idUsuario = tokenService.getIdUsuario(token);
		
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		} else {
			return token.substring(7, token.length());
		}
		
	}


}
