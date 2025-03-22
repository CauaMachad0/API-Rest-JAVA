package br.com.fiap.api_rest.dto;

import jakarta.validation.constraints.NotBlank;

public class EnderecoRequest {

    @NotBlank(message = "A localização não pode ser nula ou vazia")
    private String localizacao;

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
