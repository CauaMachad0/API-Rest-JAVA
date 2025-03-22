package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.AutorResponse;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.AutorRepository;
import br.com.fiap.api_rest.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public AutorResponse criarAutor(AutorRequest request) {
        Autor autor = new Autor();
        autor.setNome(request.getNome());

        if (request.getLivrosIds() != null && !request.getLivrosIds().isEmpty()) {
            List<Livro> livros = livroRepository.findAllById(request.getLivrosIds());
            autor.setLivros(livros);
        }

        autor = autorRepository.save(autor);

        return new AutorResponse(autor.getId(), autor.getNome(),
                autor.getLivros().stream().map(Livro::getTitulo).collect(Collectors.toList()));
    }

    public List<AutorResponse> listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        return autores.stream()
                .map(autor -> new AutorResponse(autor.getId(), autor.getNome(),
                        autor.getLivros().stream().map(Livro::getTitulo).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public AutorResponse buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        return new AutorResponse(autor.getId(), autor.getNome(),
                autor.getLivros().stream().map(Livro::getTitulo).collect(Collectors.toList()));
    }

    @Transactional
    public AutorResponse atualizarAutor(Long id, AutorRequest request) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        autor.setNome(request.getNome());

        if (request.getLivrosIds() != null && !request.getLivrosIds().isEmpty()) {
            List<Livro> livros = livroRepository.findAllById(request.getLivrosIds());
            autor.setLivros(livros);
        } else {
            autor.setLivros(null);
        }

        autor = autorRepository.save(autor);

        return new AutorResponse(autor.getId(), autor.getNome(),
                autor.getLivros().stream().map(Livro::getTitulo).collect(Collectors.toList()));
    }

    @Transactional
    public void deletarAutor(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor não encontrado");
        }
        autorRepository.deleteById(id);
    }
}
