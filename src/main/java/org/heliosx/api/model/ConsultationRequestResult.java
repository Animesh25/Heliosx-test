package org.heliosx.api.model;

import lombok.Builder;

@Builder
public class ConsultationRequestResult {
    private boolean accepted;
    private String rejectionReason;
}
