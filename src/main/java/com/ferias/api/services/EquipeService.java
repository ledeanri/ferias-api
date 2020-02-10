package com.ferias.api.services;

import java.util.Optional;

import com.ferias.api.entity.Equipe;

public interface EquipeService {
	Optional<Equipe> buscarPorNome(String nome);
	
	Optional<Equipe> findById(Long id);
	
	Equipe persistir(Equipe equipe);
}
