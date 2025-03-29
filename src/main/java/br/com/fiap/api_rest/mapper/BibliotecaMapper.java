package br.com.fiap.api_rest.mapper;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponse;
import br.com.fiap.api_rest.model.Biblioteca;

public class BibliotecaMapper {
    private final EnderecoMapper enderecoMapper = new EnderecoMapper();

    public Biblioteca requestToBiblioteca(BibliotecaRequest bibliotecaRequest) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNome(bibliotecaRequest.nome());
        biblioteca.setEndereco(enderecoMapper.requestToEndereco(bibliotecaRequest.endereco()));
        return biblioteca;
    }

    public BibliotecaResponse bibliotecaToResponse(Biblioteca biblioteca) {
        return new BibliotecaResponse(
                biblioteca.getId(),
                biblioteca.getNome(),
                enderecoMapper.enderecoToResponse(biblioteca.getEndereco()));
    }
}