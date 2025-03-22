package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponse;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.model.Endereco;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.BibliotecaRepository;
import br.com.fiap.api_rest.repository.EnderecoRepository;
import br.com.fiap.api_rest.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final EnderecoRepository enderecoRepository;
    private final LivroRepository livroRepository;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository, EnderecoRepository enderecoRepository, LivroRepository livroRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.enderecoRepository = enderecoRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public BibliotecaResponse criarBiblioteca(BibliotecaRequest request) {
        Endereco endereco = enderecoRepository.findById(request.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNome(request.getNome());
        biblioteca.setEndereco(endereco);

        biblioteca = bibliotecaRepository.save(biblioteca);

        List<String> livros = livroRepository.findAllByBiblioteca(biblioteca).stream()
                .map(Livro::getTitulo)
                .collect(Collectors.toList());

        return new BibliotecaResponse(biblioteca.getId(), biblioteca.getNome(), endereco.getRua(), livros);
    }

    public BibliotecaResponse buscarBibliotecaPorId(Long id) {
        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca não encontrada"));

        List<String> livros = livroRepository.findAllByBiblioteca(biblioteca).stream()
                .map(Livro::getTitulo)
                .collect(Collectors.toList());

        return new BibliotecaResponse(biblioteca.getId(), biblioteca.getNome(), biblioteca.getEndereco().getRua(), livros);
    }

    public List<BibliotecaResponse> listarBibliotecas() {
        List<Biblioteca> bibliotecas = bibliotecaRepository.findAll();

        return bibliotecas.stream()
                .map(biblioteca -> new BibliotecaResponse(biblioteca.getId(), biblioteca.getNome(),
                        biblioteca.getEndereco().getRua(),
                        livroRepository.findAllByBiblioteca(biblioteca).stream()
                                .map(Livro::getTitulo)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarBiblioteca(Long id) {
        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca não encontrada"));
        bibliotecaRepository.delete(biblioteca);
    }
}
