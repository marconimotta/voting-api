package com.sicredi.voting.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteSessionRequestDTO {

	@NotBlank
	private String agendaId;

	private LocalDateTime closeDate;

}
