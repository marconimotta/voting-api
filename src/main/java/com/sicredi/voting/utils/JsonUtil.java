package com.sicredi.voting.utils;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
	
	public static String convertToJson(final Object object) {

		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		try {
			if(Objects.nonNull(object)) {				
				return mapper.writeValueAsString(object);				
			}
		} catch (final JsonProcessingException e) {
            log.error(e.getMessage());
		}
		return null;
	}

}
