package br.senai.sp.jandira.odonto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sp.jandira.odonto.model.Dentista;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {

	//* teste
	Dentista findByNome(String nome);
	Dentista findByCro(String cro);
	
}
