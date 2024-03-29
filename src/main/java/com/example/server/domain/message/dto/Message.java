package com.example.server.domain.message.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Message {
    private HttpStatus status;
    private int statusCode;
    private String statusMessage;
    private String message;
    private String socketBehavior;
    private String socketMemo;
    private String memo;
    private Object data;
    private String path;
    private Date timestamp;
    private String error;

    public Message() {
        this.status = HttpStatus.BAD_REQUEST;
        this.statusCode = this.status.value();
        this.statusMessage = this.status.name();
        this.message = null;
        this.socketBehavior = null;
        this.socketMemo = null;
        this.memo = null;
        this.data = null;
        this.timestamp = new Date();
        
    }

    public void setStatus(HttpStatus status){
        this.status = status;
        this.statusCode = status.value();
        this.statusMessage = status.name();
    }
}
