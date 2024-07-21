package com.sicredi.voting.service;

import static com.sicredi.voting.mock.AgendaMock.CPF_EXAMPLE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sicredi.voting.enums.StatusToVoteEnum;
import com.sicredi.voting.integration.UserIntegrateClient;
import com.sicredi.voting.integration.dto.UserIntegrateStatusDTO;

@SpringBootTest
class UserIntegrateClientServiceTest {

	@Autowired
	UserIntegrateClientService userIntegrateClientService;

	@MockBean
	UserIntegrateClient userIntegrateClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenCheckCPFReturnTrue() {
		when(userIntegrateClient.existsCpf(Mockito.anyString()))
				.thenReturn(UserIntegrateStatusDTO.builder().status(StatusToVoteEnum.ABLE_TO_VOTE.getName()).build());
		assertEquals(Boolean.TRUE, userIntegrateClientService.checkCPF(CPF_EXAMPLE_1));
	}

	@Test
	void whenCheckCPFReturnFalse() {
		when(userIntegrateClient.existsCpf(Mockito.anyString()))
				.thenReturn(UserIntegrateStatusDTO.builder().status(StatusToVoteEnum.UNABLE_TO_VOTE.getName()).build());
		assertEquals(Boolean.FALSE, userIntegrateClientService.checkCPF(CPF_EXAMPLE_1));
	}

}
