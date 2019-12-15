package de.com.magnojr.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import static de.com.magnojr.main.GenericBankService.mapper;

public class JsonUtils {

    public static String toJson(Object responses) {
        try {
            return mapper.writeValueAsString(responses);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
