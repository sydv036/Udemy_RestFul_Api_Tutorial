package com.example.restful_api.dtos.global;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RestResponse<T> {
    private int statusCode;
    private Object message;
    private T data;
    private String errorCode;
    private LocalDateTime timestamp = LocalDateTime.now();


    public RestResponse() {
    }

    public RestResponse(int statusCode, Object message, T data, String errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
