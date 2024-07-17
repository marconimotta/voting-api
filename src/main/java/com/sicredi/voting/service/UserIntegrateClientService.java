package com.sicredi.voting.service;

import static com.sicredi.voting.enums.StatusToVoteEnum.ABLE_TO_VOTE;
import static com.sicredi.voting.enums.StatusToVoteEnum.toEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.sicredi.voting.integration.UserIntegrateClient;
import com.sicredi.voting.integration.dto.UserIntegrateStatusDTO;

@Service
public class UserIntegrateClientService {
	
	@Autowired
	UserIntegrateClient userIntegrateClient;

	public boolean checkCPF(final String cpf) {
		try {
			final UserIntegrateStatusDTO check = userIntegrateClient.existsCpf(cpf);
			return toEnum(check.getStatus()).equals(ABLE_TO_VOTE);
		} catch (final HttpStatusCodeException ex) {
			if (!ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw ex;
			}
		}
		return false;
	}

}
