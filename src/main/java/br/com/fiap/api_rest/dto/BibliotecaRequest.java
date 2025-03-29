package br.com.fiap.api_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BibliotecaRequest(
        @NotBlank(message = "O nome da biblioteca é obrigatório.") String nome,
        @NotNull(message = "O endereço da biblioteca é obrigatório") String endereco
) {
}