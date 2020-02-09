package com.ferias.api.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ferias.api.entity.FeriasFuncionario;

@NamedQueries({
		@NamedQuery(name = "FeriasFuncionarioRepository.findByFuncionarioId", 
				query = "SELECT ferias FROM FeriasFuncionario ferias WHERE ferias.funcionario.id = :funcionarioId") })
public interface FeriasFuncionarioRepository extends JpaRepository<FeriasFuncionario, Long> {
	List<FeriasFuncionario> findByFuncionarioMatricula(@Param("funcionarioMatricula") Long funcionarioMatricula);

	Page<FeriasFuncionario> findByFuncionarioMatricula(@Param("funcionarioMatricula") Long funcionarioMatricula, Pageable pageable);
	
	Optional<FeriasFuncionario> findById(Long id); 
}
