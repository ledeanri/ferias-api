package com.ferias.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferias.api.entity.Equipe;
import com.ferias.api.repository.EquipeRepository;
import com.ferias.api.services.EquipeService;

@Service
public class EquipeServiceImpl implements EquipeService {
	
	private static final Logger log = LoggerFactory.getLogger(Equipe.class);
	
	@Autowired
	private EquipeRepository equipeRepository;

	@Override
	public Optional<Equipe> buscarPorNome(String nome) {
		log.info("Buscando equipe para nome {}", nome);
		return Optional.ofNullable(equipeRepository.findByNome(nome));
	}

	@Override
	public Equipe persistir(Equipe equipe) {
		log.info("Persistindo equipe: {}", equipe);
		return this.equipeRepository.save(equipe);
	}

	@Override
	public Optional<Equipe> findById(Long id) {
		log.info("Buscando equipe para id {}", id);
		return this.equipeRepository.findById(id);
	}

}
