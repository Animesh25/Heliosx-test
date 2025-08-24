package org.heliosx.api.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Answer {
    private String question;
    private UUID questionId;
    private String answer;
}
