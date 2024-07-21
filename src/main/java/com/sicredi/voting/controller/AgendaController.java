package com.sicredi.voting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.voting.dto.AgendaRequestDTO;
import com.sicredi.voting.dto.AgendaResponseDTO;
import com.sicredi.voting.dto.VoteRequestDTO;
import com.sicredi.voting.dto.VoteResponseDTO;
import com.sicredi.voting.dto.VoteSessionRequestDTO;
import com.sicredi.voting.service.AgendaService;
import com.sicredi.voting.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Rest to agenda methods")
@RestController
@RequestMapping(value = "api/v1/agenda")
public class AgendaController {

	@Autowired
	AgendaService agendaService;

	@Operation(summary = Constants.AGENDA_LIST_ALL_SUMMARY, description = Constants.AGENDA_LIST_ALL_SUMMARY)
	@GetMapping(value = "/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<AgendaResponseDTO> getAgendas() {
		return agendaService.findAll();
	}

	@Operation(summary = Constants.AGENDA_GET_ID_SUMMARY, description = Constants.AGENDA_LIST_ALL_SUMMARY)
	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public AgendaResponseDTO getAgenda(@PathVariable final String id) {
		return agendaService.findById(id);
	}

	@Operation(summary = Constants.AGENDA_CREATE_SUMMARY, description = Constants.AGENDA_CREATE_SUMMARY)
	@PostMapping(value = "/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public AgendaResponseDTO save(@RequestBody final @Valid AgendaRequestDTO agendaRequest) {
		return agendaService.save(agendaRequest);
	}

	@Operation(summary = Constants.AGENDA_OPEN_SESSION_SUMMARY, description = Constants.AGENDA_OPEN_SESSION_SUMMARY)
	@PostMapping(value = "/open-session")
	@ResponseStatus(value = HttpStatus.CREATED)
	public AgendaResponseDTO openVoteSession(@RequestBody @Valid final VoteSessionRequestDTO voteSessionRequest) {
		return agendaService.openVoteSession(voteSessionRequest);
	}

	@Operation(summary = Constants.AGENDA_VOTE_SUMMARY, description = Constants.AGENDA_VOTE_SUMMARY)
	@PostMapping(value = "/vote")
	@ResponseStatus(value = HttpStatus.CREATED)
	public VoteResponseDTO registerVote(@RequestBody @Valid final VoteRequestDTO voteRequestDTO) {
		return agendaService.registerVote(voteRequestDTO);
	}


}
