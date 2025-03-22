package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.EnderecoRequest;
import br.com.fiap.api_rest.dto.EnderecoResponse;
import br.com.fiap.api_rest.model.Endereco;
import br.com.fiap.api_rest.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public EnderecoResponse criarEndereco(EnderecoRequest request) {
        Endereco endereco = new Endereco();
        endereco.setLocalizacao(request.getLocalizacao());
        endereco = enderecoRepository.save(endereco);
        return new EnderecoResponse(endereco.getId(), endereco.getLocalizacao());
    }

    public EnderecoResponse buscarEnderecoPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        return new EnderecoResponse(endereco.getId(), endereco.getLocalizacao());
    }

    public void deletarEndereco(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        enderecoRepository.delete(endereco);
    }
}
