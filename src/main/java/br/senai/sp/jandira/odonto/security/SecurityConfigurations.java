package br.senai.sp.jandira.odonto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.senai.sp.jandira.odonto.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(autenticacaoService).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		System.out.println("Verificando paths...");
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/odonto/dentistas").permitAll()
			.antMatchers(HttpMethod.GET, "/odonto/dentistas/*").hasAnyRole("USER", "ADMIN", "RH")
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.antMatchers(HttpMethod.DELETE, "/odonto/dentistas/*").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}

}
