package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private String toCc;

    /*public Mail(String mailTo, String subject, String message) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }*/
}
