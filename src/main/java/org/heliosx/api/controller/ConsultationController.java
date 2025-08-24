package org.heliosx.api.controller;

import org.heliosx.api.ConsultationResource;
import org.heliosx.api.model.Answer;
import org.heliosx.api.model.ConsultationRequestResult;
import org.heliosx.api.model.QuestionDto;
import org.heliosx.service.ConsultationService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ConsultationController implements ConsultationResource {

    private ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @Override
    public Set<QuestionDto> getQuestions() {
        return consultationService.getQuestions();
    }

    @Override
    public ConsultationRequestResult getAnswersResult(List<Answer> answers) {
        return consultationService.processAnswers(answers);
    }


}
