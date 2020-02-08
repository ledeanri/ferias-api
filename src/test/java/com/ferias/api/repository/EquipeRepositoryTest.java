package com.ferias.api.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ferias.api.entity.Equipe;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EquipeRepositoryTest {
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	private static final String NOME = "Equipe exemplo";
	
	@Before
	public void setUp() throws Exception {
		Equipe equipe = new Equipe();
		equipe.setNome(NOME);
		this.equipeRepository.save(equipe);
	}
	
	@After
	public final void tearDown() {
		this.equipeRepository.deleteAll();
	}
	
	@Test
	public void testBuscarPorNome() {

		Equipe equipe = this.equipeRepository.findByNome(NOME);
		
		assertEquals(NOME, equipe.getNome());
	}
}
