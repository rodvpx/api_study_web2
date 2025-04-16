package org.example.api_study_web2.data;

import jakarta.validation.constraints.NotBlank;

public record StudentInput(
        @NotBlank(message = "O primeiro nome não pode estar em branco") String firstName,
        @NotBlank(message = "O sobrenome não pode estar em branco") String lastName,
        @NotBlank(message = "O endereço não pode estar em branco") String address,
        @NotBlank(message = "O gênero não pode estar em branco") String gender) {
}
