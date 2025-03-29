package br.com.fiap.api_rest.mapper;

import br.com.fiap.api_rest.dto.EnderecoRequest;
import br.com.fiap.api_rest.dto.EnderecoResponse;
import br.com.fiap.api_rest.model.Endereco;

public class EnderecoMapper {
    public Endereco requestToEndereco(String localizacao) {
        return new Endereco(localizacao);
    }

    public EnderecoResponse enderecoToResponse(Endereco endereco) {
        return new EnderecoResponse(endereco.getId(), endereco.getLocalizacao());
    }
}