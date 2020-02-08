package com.ferias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferias.api.entity.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe, Long>{
	Equipe findByNome(String nome);

}
