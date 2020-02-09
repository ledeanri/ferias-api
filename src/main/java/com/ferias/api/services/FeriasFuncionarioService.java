package com.ferias.api.services;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;


import com.ferias.api.entity.FeriasFuncionario;

public interface FeriasFuncionarioService {
	Page<FeriasFuncionario> buscarPorFuncionarioMatricula(Long funcionarioMatricula, PageRequest pageRequest);
	
	Optional<FeriasFuncionario> buscarPorId(Long id);
	
	FeriasFuncionario persistir(FeriasFuncionario feriasFuncionario);
	
	void remover(Long id);

}
