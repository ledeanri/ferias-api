package com.ferias.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

public class FeriasFuncionarioDto {
	private Optional<Long> id = Optional.empty();
	@NotEmpty(message = "Data não pode ser vazia.")
	private String dataFerias;
	
	private Long funcionarioId;
	
	public FeriasFuncionarioDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public String getDataFerias() {
		return dataFerias;
	}

	public void setDataFerias(String dataFerias) {
		this.dataFerias = dataFerias;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

}
