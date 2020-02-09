package com.ferias.api.services;

import java.util.Optional;

import com.ferias.api.entity.Funcionario;

public interface FuncionarioService {
	Funcionario persistirFuncionario(Funcionario funcionario);
	
	Optional<Funcionario> buscarPorNome(String nome);
	
	Optional<Funcionario> buscarPorMatricula(Integer matricula);
}
