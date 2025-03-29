package br.com.fiap.api_rest.dto;

public record BibliotecaResponse(
        Long id,
        String nome,
        EnderecoResponse endereco
) {
}