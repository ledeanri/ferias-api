package com.ferias.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferias.api.dto.FeriasFuncionarioDto;
import com.ferias.api.entity.FeriasFuncionario;
import com.ferias.api.entity.Funcionario;
import com.ferias.api.services.FeriasFuncionarioService;
import com.ferias.api.services.FuncionarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FeriasFuncionarioTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;

	@MockBean
	private FeriasFuncionarioService feriasFuncionarioService;

	@MockBean
	private FuncionarioService funcionarioService;

	private static final String URL_BASE = "/api/ferias-funcionario/";
	private static final Long MATRICULA_FUNCIONARIO = 1L;
	private static final Long ID_FERIAS_FUNCIONARIO = 1L;
	private static final Date DATA_FERIAS_FUNCIONARIO = new Date();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testCadastrarFeriasFuncionario() throws Exception {
		FeriasFuncionario feriasFuncionario = obterDadosFeriasFuncionario();
		BDDMockito.given(this.funcionarioService.buscarPorMatricula(Mockito.anyLong()))
				.willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(this.feriasFuncionarioService.persistir(Mockito.any(FeriasFuncionario.class)))
				.willReturn(feriasFuncionario);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE).content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_FERIAS_FUNCIONARIO))
				.andExpect(
						jsonPath("$.data.dataFerias").value(this.dateFormat.format(DATA_FERIAS_FUNCIONARIO)))
				.andExpect(jsonPath("$.data.funcionarioId").value(MATRICULA_FUNCIONARIO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	public void testCadastrarFeriasFuncionarioIdInvalido() throws Exception {
		BDDMockito.given(this.funcionarioService.buscarPorMatricula(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE).content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		FeriasFuncionarioDto feriasFuncionarioDto = new FeriasFuncionarioDto();
		feriasFuncionarioDto.setId(null);
		feriasFuncionarioDto.setDataFerias(this.dateFormat.format(DATA_FERIAS_FUNCIONARIO));
		feriasFuncionarioDto.setFuncionarioId(MATRICULA_FUNCIONARIO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(feriasFuncionarioDto);
	}

	private FeriasFuncionario obterDadosFeriasFuncionario() {
		FeriasFuncionario feriasFuncionario = new FeriasFuncionario();
		feriasFuncionario.setId(ID_FERIAS_FUNCIONARIO);
		feriasFuncionario.setDataFerias(DATA_FERIAS_FUNCIONARIO);
		feriasFuncionario.setFuncionario(new Funcionario());
		feriasFuncionario.getFuncionario().setMatricula(MATRICULA_FUNCIONARIO);
		return feriasFuncionario;
	}

}
