package org.example.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class NotAJSONOrWrongJSONFormat extends JsonProcessingException {
    public NotAJSONOrWrongJSONFormat(String msg) {
        super(msg);
    }
}
