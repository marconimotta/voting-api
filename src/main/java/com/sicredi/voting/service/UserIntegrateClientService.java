package com.sicredi.voting.service;

import static com.sicredi.voting.enums.StatusToVoteEnum.ABLE_TO_VOTE;
import static com.sicredi.voting.enums.StatusToVoteEnum.toEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.sicredi.voting.integration.UserIntegrateClient;
import com.sicredi.voting.integration.dto.UserIntegrateStatusDTO;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
		} catch (final FeignException.NotFound e) {
			log.error("Api para checagem de cpf está sem conexão");
		}
		return false;
	}

}
