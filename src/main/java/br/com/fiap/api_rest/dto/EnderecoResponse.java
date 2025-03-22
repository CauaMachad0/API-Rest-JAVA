package br.com.fiap.api_rest.dto;

public class EnderecoResponse {

    private Long id;
    private String localizacao;

    public EnderecoResponse(Long id, String localizacao) {
        this.id = id;
        this.localizacao = localizacao;
    }

    public Long getId() {
        return id;
    }

    public String getLocalizacao() {
        return localizacao;
    }
}
