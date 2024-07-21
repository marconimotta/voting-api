package com.sicredi.voting.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sicredi.voting.integration.config.DefaultClientConfig;
import com.sicredi.voting.integration.dto.UserIntegrateStatusDTO;

@FeignClient(value = "UserIntegrateClient", url = "${user-integrate.host}", configuration = DefaultClientConfig.class)
public interface UserIntegrateClient {
	
	@GetMapping("/users/{cpf}")
	UserIntegrateStatusDTO existsCpf(@PathVariable(value = "cpf") final String cpf);

}
