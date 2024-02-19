package com.test.adidata.model.rest.restGenericResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestGenericResponse {
    private RestErrorSchema errorSchema;
    private Object responseData;
}
