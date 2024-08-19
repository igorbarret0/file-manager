package com.igorbarreto.file_manager.exceptions;

import com.igorbarreto.file_manager.dtos.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class RestExceptionHandler {

    private static final ZoneId BRAZIL_TIMEZONE = ZoneId.of("America/Sao_Paulo");

    @ExceptionHandler({MaxUploadSizeExceededException.class, IOException.class, FileNotFoundException.class})
    public ResponseEntity<MessageResponse> handleExceptions(Exception exception,
                                                            HttpServletRequest request) {


        HttpStatus httpStatus;

        if (exception instanceof MaxUploadSizeExceededException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof IOException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(httpStatus)
                .body(new MessageResponse(LocalDateTime.now(BRAZIL_TIMEZONE).toString(),
                        httpStatus.value(), exception.getMessage(), request.getRequestURI(),
                        request.getMethod()));

    }
}
