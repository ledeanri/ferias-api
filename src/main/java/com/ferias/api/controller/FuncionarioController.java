package com.ferias.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferias.api.dto.FuncionarioDto;
import com.ferias.api.entity.Equipe;
import com.ferias.api.entity.Funcionario;
import com.ferias.api.response.Response;
import com.ferias.api.services.EquipeService;
import com.ferias.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EquipeService equipeService;

	public FuncionarioController() {
	}
	
	@PostMapping
	public ResponseEntity<Response<FuncionarioDto>> cadastrar(@Valid @RequestBody FuncionarioDto funcionarioDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando funcionário: {}", funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();

		validarDadosExistentes(funcionarioDto, result);
		Funcionario funcionario = this.converterDtoParaFuncionario(funcionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Equipe> equipe = this.equipeService.findById(funcionarioDto.getEquipeId());
		equipe.ifPresent(eqp -> funcionario.setEquipe(eqp));
		this.funcionarioService.persistirFuncionario(funcionario);

		response.setData(this.converterFuncionarioDto(funcionario));
		return ResponseEntity.ok(response);
	}

	private void validarDadosExistentes(FuncionarioDto funcionarioDto, BindingResult result) {
		Optional<Equipe> equipe = this.equipeService.findById(funcionarioDto.getEquipeId());
		
		if (!equipe.isPresent()) {
			result.addError(new ObjectError("equipe", "Equipe não cadastrada."));
		}
		
		this.funcionarioService.buscarPorNome(funcionarioDto.getNome())
			.ifPresent(func -> result.addError(new ObjectError("funcionario", "Nome já existente.")));

	}

	private Funcionario converterDtoParaFuncionario(FuncionarioDto funcionarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		
		funcionario.setNome(funcionarioDto.getNome());
		funcionario.setDataNascimento(funcionarioDto.getDataNascimento());
		funcionario.setDataContratacao(funcionarioDto.getDataContratacao());
		
		funcionario.setEndereco(funcionarioDto.getEndereco());
		Optional<Equipe> equipe = this.equipeService.findById(funcionarioDto.getEquipeId());
		equipe.ifPresent(eqp -> funcionario.setEquipe(eqp));

		return funcionario;
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando funcionário: {}", funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();

		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorMatricula(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
		}

		this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.funcionarioService.persistirFuncionario(funcionario.get());
		response.setData(this.converterFuncionarioDto(funcionario.get()));

		return ResponseEntity.ok(response);
	}

	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		funcionario.setNome(funcionarioDto.getNome());

		if (!funcionario.getNome().equals(funcionarioDto.getNome())) {
			this.funcionarioService.buscarPorNome(funcionarioDto.getNome())
					.ifPresent(func -> result.addError(new ObjectError("nome", "Nome já existente.")));
			funcionario.setNome(funcionarioDto.getNome());
		}

		funcionario.setDataNascimento(funcionarioDto.getDataNascimento());
		funcionario.setDataContratacao(funcionarioDto.getDataContratacao());

	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setMatricula(funcionario.getMatricula());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setDataNascimento(funcionario.getDataNascimento());
		funcionarioDto.setDataContratacao(funcionario.getDataContratacao());
		
		funcionarioDto.setEquipeId(funcionario.getEquipe().getId());
		
		funcionarioDto.setEndereco(funcionario.getEndereco());

		return funcionarioDto;
	}

}
