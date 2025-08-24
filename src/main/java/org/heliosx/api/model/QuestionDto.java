package org.heliosx.api.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class QuestionDto {
    private String question;
    private UUID questionId;
}
