package br.com.project.image.manager.domain.model;


public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
