package com.sicredi.voting.controller;

import static com.sicredi.voting.mock.AgendaMock.BASE_AGENDA;
import static com.sicredi.voting.mock.AgendaMock.getAgendaResponseOk;
import static com.sicredi.voting.mock.AgendaMock.getListAgendaResponse;
import static com.sicredi.voting.mock.AgendaMock.getNewAgendaOk;
import static com.sicredi.voting.mock.AgendaMock.getNewVoteSessionOk;
import static com.sicredi.voting.mock.AgendaMock.getNewVoteYesOk;
import static com.sicredi.voting.mock.AgendaMock.getVoteResponseYesOk;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sicredi.voting.service.AgendaService;

@SpringBootTest
@AutoConfigureMockMvc
class AgendaControllerTest {

	private ObjectMapper objectMapper;

	@MockBean
	AgendaService agendaService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Test
	void whenInsertAgendaReturnCreated() throws Exception {
		BDDMockito.when(agendaService.save(Mockito.any())).thenReturn(getAgendaResponseOk());
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_AGENDA.concat("/")).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getNewAgendaOk()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.subject").exists())
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse();
	}

	@Test
	void whenOpenVoteSessionReturnCreated() throws Exception {
		BDDMockito.when(agendaService.openVoteSession(Mockito.any())).thenReturn(getAgendaResponseOk());
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_AGENDA.concat("/open-session"))
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getNewVoteSessionOk()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.subject").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.voteSession").exists())
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse();
	}

	@Test
	void whenVoteRegisterReturnCreated() throws Exception {
		BDDMockito.when(agendaService.registerVote(Mockito.any())).thenReturn(getVoteResponseYesOk());
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_AGENDA.concat("/vote"))
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(getNewVoteYesOk()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.documentNumber").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateVote").exists())
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse();
	}

	@Test
	void whenFindAllReturnOk() throws Exception {
		BDDMockito.when(agendaService.findAll()).thenReturn(getListAgendaResponse());
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_AGENDA.concat("/")).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray()).andDo(MockMvcResultHandlers.print())
				.andReturn().getResponse();
	}

	@Test
	void whenFindByIdReturnOk() throws Exception {
		BDDMockito.when(agendaService.findById(Mockito.anyString())).thenReturn(getAgendaResponseOk());
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_AGENDA.concat("/").concat(UUID.randomUUID().toString()))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()).andDo(MockMvcResultHandlers.print())
				.andReturn().getResponse();
	}

}
