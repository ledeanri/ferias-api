package com.ferias.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = -4207085550422547247L;
	
	@Column(name = "rua", nullable = false)
	private String rua;
	@Column(name = "numero", nullable = false)
	private Integer numero;
	@Column(name = "complemento")
	private String complemento;
	@Column(name = "bairro", nullable = false)
	private String bairro;
	@Column(name = "cidade", nullable = false)
	private String cidade;
	@Column(name = "estudo", nullable = false)
	private String estado;
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
