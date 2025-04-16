package org.example.api_study_web2.data;

public record StudentInput(
        String firstName,
        String lastName,
        String address,
        String gender
) {
}
