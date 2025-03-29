package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponse;
import br.com.fiap.api_rest.mapper.BibliotecaMapper;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BibliotecaService {
    private final BibliotecaRepository bibliotecaRepository;
    private final BibliotecaMapper bibliotecaMapper = new BibliotecaMapper();

    @Autowired
    public BibliotecaService(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    public BibliotecaResponse save(BibliotecaRequest bibliotecaRequest) {
        return bibliotecaMapper.bibliotecaToResponse(bibliotecaRepository.save(bibliotecaMapper.requestToBiblioteca(bibliotecaRequest)));
    }

    public Page<BibliotecaResponse> findAll(Pageable pageable) {
        return bibliotecaRepository.findAll(pageable).map(bibliotecaMapper::bibliotecaToResponse);
    }

    public Biblioteca findBibliotecaById(Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        return biblioteca.orElse(null);
    }

    public BibliotecaResponse findById(Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        return biblioteca.map(bibliotecaMapper::bibliotecaToResponse).orElse(null);
    }

    public BibliotecaResponse update(BibliotecaRequest bibliotecaRequest, Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        if (biblioteca.isPresent()) {
            Biblioteca bibliotecaSalvo = bibliotecaRepository.save(biblioteca.get());
            return bibliotecaMapper.bibliotecaToResponse(bibliotecaSalvo);
        }
        return null;
    }

    public boolean delete(Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        if (biblioteca.isPresent()) {
            bibliotecaRepository.delete(biblioteca.get());
            return true;
        }
        return false;
    }
}