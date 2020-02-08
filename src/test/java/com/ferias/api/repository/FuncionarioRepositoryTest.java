package com.ferias.api.repository;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ferias.api.entity.Endereco;
import com.ferias.api.entity.Equipe;
import com.ferias.api.entity.Funcionario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	private static final String NOME = "Funcionario teste";
	private static final Date DATANASCIMENTO = getDate(1980, 11, 20);
	private static final Date DATACONTRATACAO = getDate(2019, 8, 15);
	
	private static final String RUA = "Rua teste";
	private static final Integer NUMERO = 10;
	private static final String COMPLEMENTO = "B";
	private static final String BAIRRO = "Nova Suiça";
	private static final String CIDADE = "Belo horizonte";
	private static final String ESTADO = "MG";
	
	@Before
	public void setUp() throws Exception {
		Equipe equipe = this.equipeRepository.save(obterDadosEquipe());
		this.funcionarioRepository.save(obterDadosFuncionario(equipe));
	}
	
	@After
	public final void tearDown() {
		this.equipeRepository.deleteAll();
	}
	
	@Test
	public void testBuscarFuncionarioPorNome() {
		Funcionario funcionario = this.funcionarioRepository.findByNome(NOME);

		assertEquals(NOME, funcionario.getNome());
	}


	private static Date getDate(int year, int month, int day) {
		Calendar myCalendar = new GregorianCalendar(year, month, day);
		return myCalendar.getTime();
	}

	private Funcionario obterDadosFuncionario(Equipe equipe) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(NOME);
		funcionario.setDataNascimento(DATANASCIMENTO);
		funcionario.setDataContratacao(DATACONTRATACAO);
		
		funcionario.setEndereco(obterEnderecoFuncionario());
		funcionario.setEquipe(equipe);
		
		return funcionario;
	}

	private Endereco obterEnderecoFuncionario() {
		Endereco endereco = new Endereco();
		endereco.setRua(RUA);
		endereco.setNumero(NUMERO);
		endereco.setComplemento(COMPLEMENTO);
		endereco.setBairro(BAIRRO);
		endereco.setCidade(CIDADE);
		endereco.setEstado(ESTADO);
		return endereco;
	}

	private Equipe obterDadosEquipe() throws Exception {
		Equipe equipe = new Equipe();
		equipe.setNome("Equipe teste");
		return equipe;
	}

	
}
