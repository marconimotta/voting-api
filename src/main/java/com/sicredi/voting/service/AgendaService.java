package com.sicredi.voting.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.voting.document.Agenda;
import com.sicredi.voting.document.Vote;
import com.sicredi.voting.document.VoteSession;
import com.sicredi.voting.dto.AgendaRequestDTO;
import com.sicredi.voting.dto.AgendaResponseDTO;
import com.sicredi.voting.dto.VoteRequestDTO;
import com.sicredi.voting.dto.VoteResponseDTO;
import com.sicredi.voting.dto.VoteSessionRequestDTO;
import com.sicredi.voting.exceptions.NotFoundException;
import com.sicredi.voting.exceptions.UnauthorizedException;
import com.sicredi.voting.repository.AgendaRepository;
import com.sicredi.voting.utils.Constants;

@Service
public class AgendaService {

	@Autowired
	AgendaRepository agendaRepository;

	@Autowired
	UserIntegrateClientService userIntegrateClientService;

	public List<AgendaResponseDTO> findAll() {
		return AgendaResponseDTO.convertEntityListToDTO(agendaRepository.findAll());
	}

	private Agenda findAgendaById(final String id) {
		return agendaRepository.findById(id).orElseThrow(() -> new NotFoundException(Constants.AGENDA_NOT_FOUND));
	}

	public AgendaResponseDTO findById(final String id) {
		return AgendaResponseDTO.convertEntityToDTO(findAgendaById(id));
	}

	public AgendaResponseDTO save(final AgendaRequestDTO agendaRequest) {
		final Agenda agenda = agendaRepository.save(Agenda.builder().subject(agendaRequest.getSubject()).build());
		return AgendaResponseDTO.convertEntityToDTO(agenda);
	}

	public AgendaResponseDTO openVoteSession(final VoteSessionRequestDTO voteSessionRequest) {
		final Agenda agenda = findAgendaById(voteSessionRequest.getAgendaId());
		if (Objects.nonNull(agenda.getVoteSession())) {
			throw new UnauthorizedException(Constants.VOTE_SESSION_READY_EXISTS);
		}
		final VoteSession voteSession = VoteSession.builder().closeDate(voteSessionRequest.getCloseDate())
				.totalVotes(0L).votesYes(0L).votesNo(0L).build();
		if (Objects.isNull(voteSessionRequest.getCloseDate())) {
			voteSession.setCloseDate(LocalDateTime.now().plusMinutes(Constants.AGENDA_DEFAULT_TIME));
		}
		agenda.setVoteSession(voteSession);

		return AgendaResponseDTO.convertEntityToDTO(agendaRepository.save(agenda));
	}

	private void validateSessionVoteOpenAndUser(final VoteSession voteSession, final VoteRequestDTO voteRequestDTO) {
		if (Objects.isNull(voteSession)) {
			throw new NotFoundException(Constants.VOTE_SESSION_NOT_FOUND);
		}
		if (voteSession.getCloseDate().isBefore(LocalDateTime.now())) {
			throw new UnauthorizedException(Constants.VOTE_SESSION_CLOSED);
		}
		if (!userIntegrateClientService.checkCPF(voteRequestDTO.getDocumentNumber())) {
			throw new UnauthorizedException(Constants.ASSOCIATE_NOT_ABLE_TO_VOTE);
		}

	}

	public VoteResponseDTO registerVote(final VoteRequestDTO voteRequestDTO) {
		final Agenda agenda = findAgendaById(voteRequestDTO.getAgendaId());
		validateSessionVoteOpenAndUser(agenda.getVoteSession(), voteRequestDTO);
		if (Objects.nonNull(agenda.getVoteSession().getVotes())) {
			agenda.getVoteSession().getVotes().stream().filter(v -> v.getDocumentNumber().equals(voteRequestDTO.getDocumentNumber()))
					.findAny().ifPresent(v -> {
						throw new UnauthorizedException(Constants.ASSOCIATE_READY_EXISTS);
					});
		} else {
			agenda.getVoteSession().setVotes(new ArrayList<>());
		}
		final boolean choosedVote = voteRequestDTO.getChoosedVote().booleanValue();
		agenda.getVoteSession().updateVotesCount(choosedVote);

		final Vote vote = Vote.builder().documentNumber(voteRequestDTO.getDocumentNumber())
				.choosedVote(choosedVote)
				.dateVote(LocalDateTime.now()).build();
		agenda.getVoteSession().getVotes().add(vote);
		agendaRepository.save(agenda);

		return VoteResponseDTO.convertEntityToDTO(vote);
	}
}
