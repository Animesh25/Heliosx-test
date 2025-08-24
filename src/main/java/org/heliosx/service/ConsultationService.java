package org.heliosx.service;

import jakarta.annotation.PostConstruct;
import org.heliosx.api.model.AnswersRequestBody;
import org.heliosx.api.model.ConsultationRequestResult;
import org.heliosx.api.model.QuestionDto;
import org.heliosx.repository.ConsultationRepository;
import org.heliosx.repository.model.QuestionDtoMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConsultationService {

    private static final Long THIRTY_MINUTES = 1800_000L;
    private static final int MINIMUM_AGE = 18;
    HashSet<QuestionDto> cachedQuestions;

    private ConsultationRepository consultationRepository;
    private QuestionDtoMapper questionDtoMapper;

    public ConsultationService(ConsultationRepository consultationRepository){
        this.consultationRepository = consultationRepository;
        this.cachedQuestions = new HashSet<>();
    }

    public Set<QuestionDto> getQuestions() {
        return cachedQuestions;
    }

    @Scheduled(fixedDelay = THIRTY_MINUTES)
    private void refreshCache(){
        cachedQuestions = createTemporaryQuestions();
    }

    private HashSet<QuestionDto> createTemporaryQuestions() {
        QuestionDto q1 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("What is your age?")
                .build();

        QuestionDto q2 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("What is your gender?")
                .build();

        QuestionDto q3 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("Do you have any allergies?")
                .build();

        QuestionDto q4 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("What is your height?")
                .build();

        QuestionDto q5 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("What is your weight?")
                .build();

        QuestionDto q6 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("Do you have any chronic medical conditions?")
                .build();

        QuestionDto q7 = QuestionDto.builder()
                .questionId(UUID.randomUUID())
                .question("Are you pregnant?")
                .build();

        return new HashSet<QuestionDto>(Arrays.asList(q1,q2,q3,q4,q5,q6,q7));
    }

    public ConsultationRequestResult processAnswers(AnswersRequestBody answers) {
        if(!isValid(answers)){
            return ConsultationRequestResult.builder()
                    .accepted(false)
                    .rejectionReason("Ensure all fields as filled and valid")
                    .build();
        }

        // check user health
        // e.g. query allergy db
        if(hasAllergies(answers)){
            return ConsultationRequestResult.builder()
                    .accepted(false)
                    .rejectionReason("We cannot prescribe for people who are allergic to X ingredient(s)")
                    .build();
        }
        if(isUnderAge(answers)){
            return ConsultationRequestResult.builder()
                    .accepted(false)
                    .rejectionReason("We cannot prescribe for people who are less than 18")
                    .build();
        }

        return ConsultationRequestResult.builder()
                .accepted(true)
                .build();

    }

    private boolean isValid(AnswersRequestBody answers) {
        return !(answers == null || answers.getAnswerList() == null ||
                answers.getAnswerList().size() == 0);
    }

    private boolean hasAllergies(AnswersRequestBody answers){
        return answers.getAnswerList()
                .stream()
                .anyMatch(a -> {
                    return a.getQuestion().getQuestion().toLowerCase().contains("allergies") &&
                            a.getAnswer() != null && !a.getAnswer().isBlank() &&
                            a.getAnswer().equalsIgnoreCase("yes");
                });
    }

    private boolean isUnderAge(AnswersRequestBody answers){
        return answers.getAnswerList()
                .stream()
                .anyMatch(a -> {
                    return a.getQuestion().getQuestion().toLowerCase().contains("your age") &&
                            a.getAnswer() != null && !a.getAnswer().isBlank() &&
                            Integer.parseInt(a.getAnswer()) >= MINIMUM_AGE;
                });
    }

    @PostConstruct
    private void initQuestionCache(){
        cachedQuestions = createTemporaryQuestions();
        // When we have an actual populated DB linked we can uncomment below
//        cachedQuestions.addAll(consultationRepository.findAll()
//                .stream()
//                .map(questionDtoMapper::toDto)
//                .collect(Collectors.toSet()));
    }


}
