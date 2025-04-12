package org.example.api_study_web2.dto;

import java.util.UUID;

public record StudentDTO(
        String firstName,
        String lastName,
        String address,
        String gender
) {
}
