package com.jellmayer.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> T parseJson(String json, Class<T> targetClass){
        try {
            return mapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Desserialização do JSON falhou.", e);
        }
    }
}
