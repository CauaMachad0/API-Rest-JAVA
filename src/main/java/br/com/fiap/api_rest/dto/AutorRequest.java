package br.com.fiap.api_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class AutorRequest {

    @NotBlank(message = "O nome do autor não pode ser nulo ou vazio")
    @Size(min = 3, max = 150, message = "O nome do autor deve ter entre 3 e 150 caracteres")
    private String nome;

    private List<Long> livrosIds; // Lista de IDs dos livros para associação

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Long> getLivrosIds() {
        return livrosIds;
    }

    public void setLivrosIds(List<Long> livrosIds) {
        this.livrosIds = livrosIds;
    }
}
