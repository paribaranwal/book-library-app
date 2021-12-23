package org.example.booklibrary.error;

import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
        return generateApiError(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
        return generateApiError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return generateApiError(ex, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> generateApiError(Exception ex, WebRequest request, HttpStatus status) {
        String error = ex.getMessage();
        error = StringUtils.isEmpty(error) ? "Exception has no message" : error;
        logger.info(error);
        logger.info(ex.getStackTrace());

        final ApiExceptionResponse response = ApiExceptionResponse.valueOf(
                status.value(), error, ex.getClass().getName()
        );
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }
}
