package com.ferias.api.services;

import java.util.Optional;

import com.ferias.api.entity.Equipe;

public interface EquipeService {
	Optional<Equipe> buscarPorNome(String nome);
	
	Equipe persistir(Equipe equipe);
}
