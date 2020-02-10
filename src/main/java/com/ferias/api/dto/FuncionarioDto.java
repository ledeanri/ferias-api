package com.ferias.api.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.ferias.api.entity.Endereco;

public class FuncionarioDto {
	private Long matricula;
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	@NotEmpty(message = "Data de nascimento não pode ser vazia.")
	private Date dataNascimento;
	@NotEmpty(message = "Data de contratação não pode ser vazia.")
	private Date dataContratacao;
	
	private Long equipeId;
	
	private Endereco endereco;

	public Long getEquipeId() {
		return equipeId;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setEquipeId(Long equipeId) {
		this.equipeId = equipeId;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public FuncionarioDto() {
	}
}
