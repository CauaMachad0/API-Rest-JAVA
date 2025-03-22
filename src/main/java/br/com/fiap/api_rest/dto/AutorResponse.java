package br.com.fiap.api_rest.dto;

import java.util.List;

public class AutorResponse {

    private Long id;
    private String nome;
    private List<String> livros;

    public AutorResponse(Long id, String nome, List<String> livros) {
        this.id = id;
        this.nome = nome;
        this.livros = livros;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getLivros() {
        return livros;
    }
}
