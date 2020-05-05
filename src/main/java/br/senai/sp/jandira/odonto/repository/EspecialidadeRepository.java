package br.senai.sp.jandira.odonto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sp.jandira.odonto.model.Especialidade;

public interface EspecialidadeRepository
	extends JpaRepository<Especialidade, Long>{

}
