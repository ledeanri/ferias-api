package com.ferias.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<EquipeDto>> buscarPorNome(@PathVariable("nome") String nome) {
		log.info("Buscando equipe por nome: {}", nome);
		Response<EquipeDto> response = new Response<EquipeDto>();
		Optional<Equipe> empresa = equipeService.buscarPorNome(nome);

		if (!empresa.isPresent()) {
			log.info("Equipe não encontrada para o nome: {}", nome);
			response.getErrors().add("Equipe não encontrada para o nome " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}

	private EquipeDto converterEmpresaDto(Equipe equipe) {
		EquipeDto equipeDto = new EquipeDto();
		equipeDto.setId(equipe.getId());
		equipeDto.setNome(equipe.getNome());

		return equipeDto;
	}
}
