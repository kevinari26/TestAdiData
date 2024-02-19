package com.test.adidata.model.rest.restGenericResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestErrorSchema {
    private String errorCode;
    private String errorMessage;
}
