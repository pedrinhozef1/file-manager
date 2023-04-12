package br.com.project.image.manager.api.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Setter
@Builder
@Data
public class Error {
    private int statusCode;
    private String message;
}
