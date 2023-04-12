package br.com.project.image.manager.domain.model;

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
