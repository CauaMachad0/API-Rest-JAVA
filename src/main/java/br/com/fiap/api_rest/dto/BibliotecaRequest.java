package br.com.fiap.api_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BibliotecaRequest {

    @NotBlank(message = "O nome da biblioteca não pode ser nulo ou vazio")
    private String nome;

    @NotNull(message = "O endereço da biblioteca é obrigatório")
    private Long enderecoId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }
}
