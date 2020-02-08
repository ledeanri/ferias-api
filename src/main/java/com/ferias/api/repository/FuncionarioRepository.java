package com.ferias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferias.api.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	Funcionario findByMatricula(Integer matricula);
	
	Funcionario findByNome(String Nome);
}
