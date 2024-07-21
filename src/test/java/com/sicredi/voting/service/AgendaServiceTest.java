package com.sicredi.voting.service;

import static com.sicredi.voting.mock.AgendaMock.getAgendaOk;
import static com.sicredi.voting.mock.AgendaMock.getAgendaWithClosedVoteSessionOk;
import static com.sicredi.voting.mock.AgendaMock.getAgendaWithOutSession;
import static com.sicredi.voting.mock.AgendaMock.getAgendaWithoutVotes;
import static com.sicredi.voting.mock.AgendaMock.getList;
import static com.sicredi.voting.mock.AgendaMock.getNewAgendaOk;
import static com.sicredi.voting.mock.AgendaMock.getNewVoteNoOk;
import static com.sicredi.voting.mock.AgendaMock.getNewVoteSessionOk;
import static com.sicredi.voting.mock.AgendaMock.getNewVoteSessionWithoutCloseDateOk;
import static com.sicredi.voting.mock.AgendaMock.getNewVoteYesOk;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sicredi.voting.document.Agenda;
import com.sicredi.voting.document.Vote;
import com.sicredi.voting.document.VoteSession;
import com.sicredi.voting.dto.AgendaRequestDTO;
import com.sicredi.voting.dto.AgendaResponseDTO;
import com.sicredi.voting.dto.VoteRequestDTO;
import com.sicredi.voting.dto.VoteResponseDTO;
import com.sicredi.voting.dto.VoteSessionRequestDTO;
import com.sicredi.voting.dto.VoteSessionResponseDTO;
import com.sicredi.voting.exceptions.NotFoundException;
import com.sicredi.voting.exceptions.UnauthorizedException;
import com.sicredi.voting.repository.AgendaRepository;

@SpringBootTest
class AgendaServiceTest {

	@Autowired
	AgendaService agendaService;

	@MockBean
	AgendaRepository agendaRepository;

	@MockBean
	UserIntegrateClientService userIntegrateClientService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void initialTestBasics() {

		final Agenda agenda = new Agenda();
		final AgendaRequestDTO agendaRequestDTO = new AgendaRequestDTO();
		final AgendaResponseDTO agendaResponseDTO = new AgendaResponseDTO();
		final VoteSession voteSession = new VoteSession();
		final VoteSessionRequestDTO voteSessionRequestDTO = new VoteSessionRequestDTO();
		final VoteSessionResponseDTO voteSessionResponseDTO = new VoteSessionResponseDTO();
		final Vote vote = new Vote();
		final VoteRequestDTO voteRequestDTO = new VoteRequestDTO();
		final VoteResponseDTO voteResponseDTO = new VoteResponseDTO();

		assertNotNull(agenda);
		assertNotNull(agendaRequestDTO);
		assertNotNull(agendaResponseDTO);
		assertNotNull(voteSession);
		assertNotNull(voteSessionRequestDTO);
		assertNotNull(voteSessionResponseDTO);
		assertNotNull(vote);
		assertNotNull(voteRequestDTO);
		assertNotNull(voteResponseDTO);

		assertNotNull(agenda.toString());
		assertNotNull(agendaRequestDTO.toString());
		assertNotNull(agendaResponseDTO.toString());
		assertNotNull(voteSession.toString());
		assertNotNull(voteSessionRequestDTO.toString());
		assertNotNull(voteSessionResponseDTO.toString());
		assertNotNull(vote.toString());
		assertNotNull(voteRequestDTO.toString());
		assertNotNull(voteResponseDTO.toString());

	}

	@Test
	void whenFindAllReturnItens() {
		when(agendaRepository.findAll()).thenReturn(getList());
		assertEquals(false, agendaService.findAll().isEmpty());
	}

	@Test
	void whenFindAllReturnEmpty() {
		when(agendaRepository.findAll()).thenReturn(Collections.emptyList());
		final List<AgendaResponseDTO> list = agendaService.findAll();
		assertEquals(Collections.emptyList(), list);
	}
	
	@Test
	void whenFindByIdReturnItem() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaOk()));
		assertNotNull(agendaService.findById(UUID.randomUUID().toString()));
	}
	
	@Test
	void whenFindByIdReturnNull() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> agendaService.findById(UUID.randomUUID().toString()));
	}

	@Test
	void whenSaveReturnItem() {
		when(agendaRepository.save(Mockito.any())).thenReturn(getAgendaOk());
		assertNotNull(agendaService.save(getNewAgendaOk()));
	}

	@Test
	void whenOpenVoteSessionReturnItem() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaWithOutSession()));
		when(agendaRepository.save(Mockito.any())).thenReturn(getAgendaOk());
		assertNotNull(agendaService.openVoteSession(getNewVoteSessionOk()));
	}

	@Test
	void whenOpenVoteSessionWithoutClosedDate() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaWithOutSession()));
		when(agendaRepository.save(Mockito.any())).thenReturn(getAgendaOk());
		assertNotNull(agendaService.openVoteSession(getNewVoteSessionWithoutCloseDateOk()));
	}

	@Test
	void whenOpenVoteSessionErrorNotFoundAgenda() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> agendaService.openVoteSession(getNewVoteSessionOk()));
	}

	@Test
	void whenOpenVoteSessionErrorExistsVoteSession() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaOk()));
		assertThrows(UnauthorizedException.class, () -> agendaService.openVoteSession(getNewVoteSessionOk()));
	}

	@Test
	void whenRegisterVoteYesReturnItem() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaWithoutVotes()));
		when(userIntegrateClientService.checkCPF(Mockito.anyString())).thenReturn(Boolean.TRUE);
		assertNotNull(agendaService.registerVote(getNewVoteYesOk()));
	}

	@Test
	void whenRegisterVoteNoReturnItem() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaWithoutVotes()));
		when(userIntegrateClientService.checkCPF(Mockito.anyString())).thenReturn(Boolean.TRUE);
		assertNotNull(agendaService.registerVote(getNewVoteNoOk()));
	}

	@Test
	void whenRegisterVoteErrorExistVoteEqual() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaOk()));
		when(userIntegrateClientService.checkCPF(Mockito.anyString())).thenReturn(Boolean.TRUE);
		assertThrows(UnauthorizedException.class, () -> agendaService.registerVote(getNewVoteYesOk()));
	}

	@Test
	void whenRegisterVoteErrorNotExistsSessionVote() {
		when(agendaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAgendaWithOutSession()));
		assertThrows(NotFoundException.class, () -> agendaService.registerVote(getNewVoteYesOk()));
	}

	@Test
	void whenRegisterVoteErrorClosedSessionVote() {
		when(agendaRepository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(getAgendaWithClosedVoteSessionOk()));
		assertThrows(UnauthorizedException.class, () -> agendaService.registerVote(getNewVoteYesOk()));
	}

	@Test
	void whenRegisterVoteErrorCpfNotAbleToVote() {
		when(agendaRepository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(getAgendaOk()));
		when(userIntegrateClientService.checkCPF(Mockito.anyString())).thenReturn(Boolean.FALSE);
		assertThrows(UnauthorizedException.class, () -> agendaService.registerVote(getNewVoteYesOk()));
	}

}
