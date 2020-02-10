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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferias.api.dto.EquipeDto;
import com.ferias.api.entity.Equipe;
import com.ferias.api.response.Response;
import com.ferias.api.services.EquipeService;

@RestController
@RequestMapping("/api/equipes")
@CrossOrigin(origins = "*")
public class EquipeController {
	private static final Logger log = LoggerFactory.getLogger(EquipeController.class);

	@Autowired
	private EquipeService equipeService;

	public EquipeController() {
	}
	
	@PostMapping
	public ResponseEntity<Response<EquipeDto>> cadastrar(@Valid @RequestBody EquipeDto equipeDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando equipe: {}", equipeDto.toString());
		Response<EquipeDto> response = new Response<EquipeDto>();

		validarDadosExistentes(equipeDto, result);
		Equipe equipe = this.converterDtoParaEquipe(equipeDto);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro equipe: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.equipeService.persistir(equipe);
		
		response.setData(this.converterEquipeDto(equipe));
		return ResponseEntity.ok(response);
	}

	private void validarDadosExistentes(EquipeDto equipeDto, BindingResult result) {
		this.equipeService.buscarPorNome(equipeDto.getNome())
				.ifPresent(emp -> result.addError(new ObjectError("equipe", "Equipe já existente.")));

	}

	private Equipe converterDtoParaEquipe(EquipeDto equipeDto) {
		Equipe equipe = new Equipe();
		equipe.setNome(equipeDto.getNome());

		return equipe;
	}

	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<EquipeDto>> buscarPorNome(@PathVariable("nome") String nome) {
		log.info("Buscando equipe por nome: {}", nome);
		Response<EquipeDto> response = new Response<EquipeDto>();
		Optional<Equipe> equipe = equipeService.buscarPorNome(nome);

		if (!equipe.isPresent()) {
			log.info("Equipe não encontrada para o nome: {}", nome);
			response.getErrors().add("Equipe não encontrada para o nome " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterEquipeDto(equipe.get()));
		return ResponseEntity.ok(response);
	}

	private EquipeDto converterEquipeDto(Equipe equipe) {
		EquipeDto equipeDto = new EquipeDto();
		equipeDto.setId(equipe.getId());
		equipeDto.setNome(equipe.getNome());

		return equipeDto;
	}
}
