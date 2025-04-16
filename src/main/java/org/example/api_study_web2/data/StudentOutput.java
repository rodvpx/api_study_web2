package org.example.api_study_web2.data;

import java.util.UUID;

public record StudentOutput(
        UUID id,
        String firstName,
        String lastName,
        String address,
        String gender) {
}
