package br.com.fiap.api_rest.mapper;

import br.com.fiap.api_rest.controller.LivroController;
import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroResponse;
import br.com.fiap.api_rest.dto.LivroResponseDTO;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Livro;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LivroMapper {

    public Livro requestToLivro(LivroRequest livroRequest) {
        Livro livro = new Livro();
        livro.setTitulo(livroRequest.getTitulo());
        livro.setPreco(livroRequest.getPreco());
        livro.setCategoria(livroRequest.getCategoria());
        livro.setIsbn(livroRequest.getIsbn());
        livro.setNumeroExemplar(livroRequest.getNumeroExemplar());
        return livro;
    }

    public LivroResponse livroToResponse(Livro livro) {
        String autores = livro.getAutores().stream().map(Autor::getNome).collect(Collectors.joining(", "));
        return new LivroResponse(livro.getId(),  autores + " - " + livro.getTitulo());
    }

    public LivroResponseDTO livroToResponseDTO(Livro livro, boolean self) {
        Link link;
        if (self) {
            link = linkTo(methodOn(LivroController.class).readLivro(livro.getId())).withSelfRel();
        } else {
            link = linkTo(methodOn(LivroController.class).readLivros(0)).withRel("Lista de Livros");
        }
        return new LivroResponseDTO(livro.getId(), livro.getAutores().stream().map(Autor::getNome) + " - " + livro.getTitulo(), link);
    }

    public List<LivroResponse> livrosToResponse(List<Livro> livros) {
        List<LivroResponse> listaLivros = new ArrayList<>();
        for (Livro livro : livros) {
            listaLivros.add(livroToResponse(livro));
        }
        return listaLivros;
    }
}