package com.ferias.api.services;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ferias.api.entity.Equipe;
import com.ferias.api.repository.EquipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EquipeServiceTest {
	@MockBean
	private EquipeRepository equipeRepository;
	
	@Autowired
	private EquipeService equipeService;
	
	private static final String NOME = "Empresa teste";
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.equipeRepository.findByNome(Mockito.anyString())).willReturn(new Equipe());
		BDDMockito.given(this.equipeRepository.save(Mockito.any(Equipe.class))).willReturn(new Equipe());
	}
	
	@Test
	public void testBuscarEquipePorNome() {
		Optional<Equipe> equipe = this.equipeService.buscarPorNome(NOME);
		
		assertNotNull(equipe);
	}
	
	@Test
	public void testPersistirEquipe() {
		Equipe equipe = this.equipeService.persistir(new Equipe());
		
		assertNotNull(equipe);
	}
	
}
