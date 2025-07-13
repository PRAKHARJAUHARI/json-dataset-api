package com.example.jsondataset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private String dataset;
    private Long recordId;

    public ApiResponse(String message, String error) {
        this.message = message;
        this.dataset = error;
    }
}