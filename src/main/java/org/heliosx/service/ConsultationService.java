package org.heliosx.service;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.heliosx.api.model.Answer;
import org.heliosx.api.model.ConsultationRequestResult;
import org.heliosx.api.model.QuestionDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NoArgsConstructor
public class ConsultationService {

    private static final long THIRTY_MINUTES = 1800_000L;
    private static final int MINIMUM_AGE = 18;
    HashSet<QuestionDto> cachedQuestions;

//    private ConsultationRepository consultationRepository;
//    private QuestionDtoMapper questionDtoMapper;

//    public ConsultationService(ConsultationRepository consultationRepository){
//        this.consultationRepository = consultationRepository;
//        this.cachedQuestions = new HashSet<>();
//    }

    public Set<QuestionDto> getQuestions() {
        return cachedQuestions;
    }

    @Scheduled(fixedDelay = THIRTY_MINUTES)
    private void refreshCache() {
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

        return new HashSet<QuestionDto>(Arrays.asList(q1, q2, q3, q4, q5, q6, q7));
    }

    public ConsultationRequestResult processAnswers(List<Answer> answers) {
        if (!isValid(answers)) {
            return ConsultationRequestResult.builder()
                    .accepted(false)
                    .rejectionReason("Ensure all fields as filled and valid")
                    .build();
        }

        // check user health
        // e.g. query allergy db
        if (hasAllergies(answers)) {
            return ConsultationRequestResult.builder()
                    .accepted(false)
                    .rejectionReason("We cannot prescribe for people who are allergic to X ingredient(s)")
                    .build();
        }
        if (isUnderAge(answers)) {
            return ConsultationRequestResult.builder()
                    .accepted(false)
                    .rejectionReason("We cannot prescribe for people who are less than 18")
                    .build();
        }

        return ConsultationRequestResult.builder()
                .accepted(true)
                .build();

    }

    private boolean isValid(List<Answer> answers) {
        return !(answers == null || answers.size() == 0);
    }

    private boolean hasAllergies(List<Answer> answers) {
        return answers
                .stream()
                .anyMatch(a -> {
                    return a.getQuestion().toLowerCase().contains("allergies") &&
                            a.getAnswer() != null && !a.getAnswer().isBlank() &&
                            a.getAnswer().equalsIgnoreCase("yes");
                });
    }

    private boolean isUnderAge(List<Answer> answers) {
        return answers
                .stream()
                .anyMatch(a -> {
                    return a.getQuestion().toLowerCase().contains("your age") &&
                            a.getAnswer() != null && !a.getAnswer().isBlank() &&
                            Integer.parseInt(a.getAnswer()) >= MINIMUM_AGE;
                });
    }

    @PostConstruct
    private void initQuestionCache() {
        cachedQuestions = createTemporaryQuestions();
        // When we have an actual populated DB linked we can uncomment below
//        cachedQuestions.addAll(consultationRepository.findAll()
//                .stream()
//                .map(questionDtoMapper::toDto)
//                .collect(Collectors.toSet()));
    }


}
