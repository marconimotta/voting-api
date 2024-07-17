package com.sicredi.voting.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sicredi.voting.document.Vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponseDTO {

	private String documentNumber;

	private LocalDateTime dateVote;

	public static VoteResponseDTO convertEntityToDTO(final Vote vote) {
		return VoteResponseDTO.builder().documentNumber(vote.getDocumentNumber()).dateVote(vote.getDateVote()).build();
	}

	public static List<VoteResponseDTO> convertListEntityToDTO(final List<Vote> vote) {
		return vote.stream().map(VoteResponseDTO::convertEntityToDTO).collect(Collectors.toList());
	}

}
