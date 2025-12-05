package com.mycompany.myapp.web.rest.errors;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class ErrorModel {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
