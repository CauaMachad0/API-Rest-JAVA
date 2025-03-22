package br.com.fiap.api_rest.dto;

import java.util.List;

public class BibliotecaResponse {

    private Long id;
    private String nome;
    private String endereco;
    private List<String> livros;

    public BibliotecaResponse(Long id, String nome, String endereco, List<String> livros) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.livros = livros;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<String> getLivros() {
        return livros;
    }
}
