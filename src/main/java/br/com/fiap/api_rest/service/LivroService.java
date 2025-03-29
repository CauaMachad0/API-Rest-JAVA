package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.*;
import br.com.fiap.api_rest.mapper.LivroMapper;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivroService {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper = new LivroMapper();

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Page<LivroResponse> findAll(Pageable pageable) {
        //return livroRepository.findAll(pageable).map(livro -> livroToResponse(livro));
        return livroRepository.findAll(pageable).map(livroMapper::livroToResponse);
    }

    public Page<LivroResponseDTO> findAllDTO(Pageable pageable) {
        return livroRepository.findAll(pageable).map(livro -> livroMapper.livroToResponseDTO(livro, true));
    }

    public LivroResponse save(Livro livro) {
        return livroMapper.livroToResponse(livroRepository.save(livro));
    }

    public LivroResponse findById(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.map(livroMapper::livroToResponse).orElse(null);
    }
    public Livro findLivroById(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElse(null);
    }

    public boolean deleteById(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            livroRepository.delete(livro.get());
            return true;
        }
        return false;
    }
}