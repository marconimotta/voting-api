package com.sicredi.voting.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sicredi.voting.document.Agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AgendaResponseDTO {

	private String id;

	private String subject;

	@Setter
	private VoteSessionResponseDTO voteSession;

	public static AgendaResponseDTO convertEntityToDTO(final Agenda agenda) {
		final AgendaResponseDTO agendaDTO = AgendaResponseDTO.builder().id(agenda.getId()).subject(agenda.getSubject())
				.build();
		if (Objects.nonNull(agenda.getVoteSession())) {
			agendaDTO.setVoteSession(VoteSessionResponseDTO.convertEntityToDTO(agenda.getVoteSession()));
		}

		return agendaDTO;
	}

	public static List<AgendaResponseDTO> convertEntityListToDTO(final List<Agenda> agendas) {
		return agendas.stream().map(AgendaResponseDTO::convertEntityToDTO).collect(Collectors.toList());
	}

}
