package org.heliosx.api;

import org.heliosx.api.model.AnswersRequestBody;
import org.heliosx.api.model.ConsultationRequestResult;
import org.heliosx.api.model.QuestionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/v1/consultation")
public interface ConsultationResource {

    @GetMapping("/questions")
    Set<QuestionDto> getQuestions();

    @PostMapping("/result")
    ConsultationRequestResult getAnswersResult(@RequestBody AnswersRequestBody answers);
}
