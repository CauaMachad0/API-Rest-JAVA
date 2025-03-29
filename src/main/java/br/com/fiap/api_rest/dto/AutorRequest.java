package br.com.fiap.api_rest.dto;

import jakarta.validation.constraints.NotBlank;

public record AutorRequest(
        @NotBlank(message = "O nome do autor é obrigatório") String nome
) {
}