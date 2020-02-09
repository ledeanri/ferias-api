package com.ferias.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ferias.api.entity.FeriasFuncionario;
import com.ferias.api.repository.FeriasFuncionarioRepository;
import com.ferias.api.services.FeriasFuncionarioService;

public class FeriasFuncionarioServiceImpl implements FeriasFuncionarioService {
	
	private static final Logger log = LoggerFactory.getLogger(FeriasFuncionarioServiceImpl.class);

	@Autowired
	private FeriasFuncionarioRepository feriasFuncionarioRepository;

	public Page<FeriasFuncionario> buscarPorFuncionarioMatricula(Long funcionarioMatricula, PageRequest pageRequest) {
		log.info("Buscando férias para o funcionário matricula {}", funcionarioMatricula);
		return this.feriasFuncionarioRepository.findByFuncionarioMatricula(funcionarioMatricula, pageRequest);
	}

	public Optional<FeriasFuncionario> buscarPorId(Long id) {
		log.info("Buscando ferias funcionario pelo ID {}", id);
		return Optional.ofNullable(this.feriasFuncionarioRepository.findById(id).orElse(null));
	}

	public FeriasFuncionario persistir(FeriasFuncionario feriasFuncionario) {
		log.info("Persistindo o lançamento: {}", feriasFuncionario);
		return this.feriasFuncionarioRepository.save(feriasFuncionario);
	}

	public void remover(Long id) {
		log.info("Removendo o lançamento ID {}", id);
		this.feriasFuncionarioRepository.deleteById(id);
	}

}
