package com.pigierbackend.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.authentication.AccountStatusException;
// import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
        @SuppressWarnings("null")
        @Override

        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String message = error.getDefaultMessage();
                        errors.put(fieldName, message);

                });
                return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        // @ExceptionHandler(Exception.class)
        // public ProblemDetail handleSecurityException(Exception exception) {
        //     if (exception instanceof BadCredentialsException e) {
        //         return createProblemDetail(401, e.getMessage(), "The username or password is incorrect");
        //     } else if (exception instanceof AccountStatusException e) {
        //         return createProblemDetail(403, e.getMessage(), "The account is locked");
        //     } else if (exception instanceof AccessDeniedException e) {
        //         return createProblemDetail(403, e.getMessage(), "You are not authorized to access this resource");
        //     } else if (exception instanceof SignatureException e) {
        //         return createProblemDetail(403, e.getMessage(), "The JWT signature is invalid");
        //     } else if (exception instanceof ExpiredJwtException e) {
        //         return createProblemDetail(403, e.getMessage(), "The JWT token has expired");
        //     } else {
        //         return createProblemDetail(500, exception.getMessage(), "Unknown internal server error.");
        //     }
        // }  
        // private ProblemDetail createProblemDetail(int status, String message, String detail) {
        //         ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        //         problemDetail.setTitle(message);
        //         problemDetail.setDetail(detail);
        //         return problemDetail;
        //     }
}
