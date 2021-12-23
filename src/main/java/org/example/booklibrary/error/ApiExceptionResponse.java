package org.example.booklibrary.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiExceptionResponse implements Serializable {

    private Integer status;

    private String message;

    private String exception;

    private Date occurredAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiException> errors;

    private ApiExceptionResponse(Integer status, String message, String exception) {
        this.status = status;
        this.message = message;
        this.exception = exception;
    }

    public static ApiExceptionResponse valueOf(Integer status, String message, String exception) {
        return new ApiExceptionResponse(status, message, exception);
    }

    public ApiExceptionResponse() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getOccurredAt() {
        if (occurredAt == null) {
            occurredAt = new Date();
        }
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public List<ApiException> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    public void setErrors(List<ApiException> errors) {
        this.errors = errors;
    }
}
