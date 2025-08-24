package org.heliosx.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class ConsultationRequestResult {
    private boolean accepted;
    private String rejectionReason;
}
