package com.ferias.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferias.api.entity.Equipe;
import com.ferias.api.entity.Funcionario;
import com.ferias.api.repository.FuncionarioRepository;
import com.ferias.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	private static final Logger log = LoggerFactory.getLogger(Equipe.class);

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Funcionario persistirFuncionario(Funcionario funcionario) {
		log.info("Persistindo funcionario: {}", funcionario);

		return this.funcionarioRepository.save(funcionario);

	}

	@Override
	public Optional<Funcionario> buscarPorNome(String nome) {
		log.info("Buscando funcionario pelo nome {}", nome);

		return Optional.ofNullable(this.funcionarioRepository.findByNome(nome));
	}

	@Override
	public Optional<Funcionario> buscarPorMatricula(Integer matricula) {
		log.info("Buscando funcionario pela matricula {}", matricula);

		return Optional.ofNullable(this.funcionarioRepository.findByMatricula(matricula));

	}
}
