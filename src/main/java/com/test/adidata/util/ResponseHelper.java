package com.test.adidata.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.adidata.model.rest.restGenericResponse.RestErrorSchema;
import com.test.adidata.model.rest.restGenericResponse.RestGenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ResponseHelper {
	@Value("#{${message-code}}")
	Map<String, String> messageCode;

	@Autowired
    ObjectMapper objectMapper;

    public ResponseEntity setRestResponseMessage (HttpStatus httpStatus, String messageCode, String errorMessage, Object responseData) {
        try {
            String[] responseCodeMsg = this.messageCode.get(messageCode).split("\\|");
            RestErrorSchema restErrorSchema = new RestErrorSchema();
            restErrorSchema.setErrorCode(responseCodeMsg[0]);
            if (errorMessage!=null) restErrorSchema.setErrorMessage(errorMessage);
            else restErrorSchema.setErrorMessage(responseCodeMsg[1]);

            RestGenericResponse restGenericResponse = new RestGenericResponse();
            restGenericResponse.setErrorSchema(restErrorSchema);
            restGenericResponse.setResponseData(responseData);

            return ResponseEntity.status(httpStatus).body(restGenericResponse);
        } catch (Exception ignored) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error");
        }
    }
}
