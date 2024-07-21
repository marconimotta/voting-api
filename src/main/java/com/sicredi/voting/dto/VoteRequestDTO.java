package com.sicredi.voting.dto;

import static com.sicredi.voting.utils.Constants.DOCUMENT_NUMBER_SIZE_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequestDTO {

	@NotBlank
	@Size(min = 11, max = 11, message = DOCUMENT_NUMBER_SIZE_MESSAGE)
	private String documentNumber;

	@NotBlank
	private String agendaId;

	@NotNull
	private Boolean choosedVote;

}
