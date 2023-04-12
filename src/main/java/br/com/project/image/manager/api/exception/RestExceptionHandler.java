package br.com.project.image.manager.api.exception;

import br.com.project.image.manager.domain.model.BusinessException;
import br.com.project.image.manager.domain.model.NotFoundException;
import br.com.project.image.manager.infrastructure.InfrastructureException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Error handleNotFoundException(NotFoundException ex) {
        return Error.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleBusinessException(BusinessException ex) {
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InfrastructureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Error handleInfrastructureException(InfrastructureException ex) {
        return Error.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Header " + ex.getHeaderName() + " is required")
                .build();
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("File is not present " + ex.getRequestPartName())
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Parameter " + ex.getParameter() + " require type " + ex.getRequiredType())
                .build();
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleFileSizeLimitExceededException(FileSizeLimitExceededException ex) {
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("file size too big " + ex.getFileName())
                .build();
    }
}
