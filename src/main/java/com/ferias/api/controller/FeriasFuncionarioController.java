package com.ferias.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferias.api.dto.FeriasFuncionarioDto;
import com.ferias.api.entity.FeriasFuncionario;
import com.ferias.api.entity.Funcionario;
import com.ferias.api.response.Response;
import com.ferias.api.services.FeriasFuncionarioService;
import com.ferias.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/ferias-funcionario")
@CrossOrigin(origins = "*")
public class FeriasFuncionarioController {
	private static final Logger log = LoggerFactory.getLogger(FeriasFuncionarioController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private FeriasFuncionarioService feriasFuncionarioService;

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<FeriasFuncionarioDto>>> listarPorFuncionarioId(
			@PathVariable("funcionarioMatricula") Long funcionarioMatricula,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando férias por matricula do funcionário: {}, página: {}", funcionarioMatricula, pag);
		Response<Page<FeriasFuncionarioDto>> response = new Response<Page<FeriasFuncionarioDto>>();

		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<FeriasFuncionario> feriasFuncionarios = this.feriasFuncionarioService.buscarPorFuncionarioMatricula(funcionarioMatricula, pageRequest);
		Page<FeriasFuncionarioDto> feriasFuncionarioDto = feriasFuncionarios.map(ferias -> this.converterFeriasFuncionarioDto(ferias));

		response.setData(feriasFuncionarioDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<FeriasFuncionarioDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando ferias funcionario por id: {}", id);
		Response<FeriasFuncionarioDto> response = new Response<FeriasFuncionarioDto>();
		Optional<FeriasFuncionario> feriasFuncionario = this.feriasFuncionarioService.buscarPorId(id);

		if (!feriasFuncionario.isPresent()) {
			log.info("Férias funcionario não encontrada para o id: {}", id);
			response.getErrors().add("Férias funcionário não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterFeriasFuncionarioDto(feriasFuncionario.get()));
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<FeriasFuncionarioDto>> adicionar(@Valid @RequestBody FeriasFuncionarioDto feriasFuncionarioDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando ferias funcionario: {}", feriasFuncionarioDto.toString());
		Response<FeriasFuncionarioDto> response = new Response<FeriasFuncionarioDto>();
		validarFuncionario(feriasFuncionarioDto, result);
		FeriasFuncionario feriasFuncionario = this.converterDtoParaFeriasFuncionario(feriasFuncionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando ferias funcionario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		feriasFuncionario = this.feriasFuncionarioService.persistir(feriasFuncionario);
		response.setData(this.converterFeriasFuncionarioDto(feriasFuncionario));
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FeriasFuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FeriasFuncionarioDto feriasFuncionarioDto, BindingResult result) throws ParseException {
		log.info("Atualizando lançamento: {}", feriasFuncionarioDto.toString());
		Response<FeriasFuncionarioDto> response = new Response<FeriasFuncionarioDto>();
		validarFuncionario(feriasFuncionarioDto, result);
		feriasFuncionarioDto.setId(Optional.of(id));
		FeriasFuncionario feriasFuncionario = this.converterDtoParaFeriasFuncionario(feriasFuncionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando ferias funcionario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		feriasFuncionario = this.feriasFuncionarioService.persistir(feriasFuncionario);
		response.setData(this.converterFeriasFuncionarioDto(feriasFuncionario));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo férias de funcionário: {}", id);
		Response<String> response = new Response<String>();
		Optional<FeriasFuncionario> feriasFuncionario = this.feriasFuncionarioService.buscarPorId(id);

		if (!feriasFuncionario.isPresent()) {
			log.info("Erro ao remover devido a ferias funcionário id: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover férias funcionário. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.feriasFuncionarioService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	private void validarFuncionario(FeriasFuncionarioDto feriasFuncionarioDto, BindingResult result) {
		if (feriasFuncionarioDto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Funcionário não informado."));
			return;
		}

		log.info("Validando funcionário id {}: ", feriasFuncionarioDto.getFuncionarioId());
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorMatricula(feriasFuncionarioDto.getFuncionarioId());
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
		} /*else if(dataMatriculaComAnoMais(funcionario).before(new Date())) {
			result.addError(new ObjectError("funcionario", "Funcionário ainda não pode marcar férias."));
		}*/
		
	}

	private Date dataMatriculaComAnoMais(Optional<Funcionario> funcionario) {
		return getDate(funcionario.get().getDataContratacao().getYear() + 1, funcionario.get().getDataContratacao().getMonth(), funcionario.get().getDataContratacao().getDay());
	}

	private FeriasFuncionarioDto converterFeriasFuncionarioDto(FeriasFuncionario feriasFuncionario) {
		FeriasFuncionarioDto feriasFuncionarioDto = new FeriasFuncionarioDto();
		feriasFuncionarioDto.setId(Optional.of(feriasFuncionario.getId()));
		feriasFuncionarioDto.setDataFerias(this.dateFormat.format(feriasFuncionario.getDataFerias()));
		
		feriasFuncionarioDto.setFuncionarioId(feriasFuncionario.getFuncionario().getMatricula());

		return feriasFuncionarioDto;
	}

	private FeriasFuncionario converterDtoParaFeriasFuncionario(FeriasFuncionarioDto feriasFuncionarioDto, BindingResult result) throws ParseException {
		FeriasFuncionario feriasFuncionario = new FeriasFuncionario();

		if (feriasFuncionarioDto.getId().isPresent()) {
			Optional<FeriasFuncionario> ferias = this.feriasFuncionarioService.buscarPorId(feriasFuncionarioDto.getId().get());
			if (ferias.isPresent()) {
				feriasFuncionario = ferias.get();
			} else {
				result.addError(new ObjectError("feriasFuncionario", "Ferias funcionario não encontrada."));
			}
		} else {
			feriasFuncionario.setFuncionario(new Funcionario());
			feriasFuncionario.getFuncionario().setMatricula(feriasFuncionarioDto.getFuncionarioId());
		}

		feriasFuncionario.setDataFerias(this.dateFormat.parse(feriasFuncionarioDto.getDataFerias()));

		
		return feriasFuncionario;
	}

	private static Date getDate(int year, int month, int day) {
		Calendar myCalendar = new GregorianCalendar(year, month, day);
		return myCalendar.getTime();
	}

}
