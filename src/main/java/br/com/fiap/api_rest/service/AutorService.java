package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.AutorResponse;
import br.com.fiap.api_rest.mapper.AutorMapper;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    private final AutorMapper autorMapper = new AutorMapper();

    // SINGLETON----
    private final AutorRepository autorRepository;
    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
    // SINGLETON----

    public AutorResponse save(AutorRequest autorRequest) {
        return autorMapper.autorToResponse(autorRepository.save(autorMapper.requestToAutor(autorRequest)));
    }

    public List<Autor> saveAll(List<AutorRequest> autoresRequest) {
        List<Autor> autores = new ArrayList<>();
        for (AutorRequest autorRequest : autoresRequest) {
            autores.add(autorMapper.requestToAutor(autorRequest));
        }
        return autorRepository.saveAll(autores);
    }

    public Page<AutorResponse> findAll(Pageable pageable) {
        return autorRepository.findAll(pageable).map(autorMapper::autorToResponse);
    }

    public AutorResponse findById(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.map(autorMapper::autorToResponse).orElse(null);
    }

    public AutorResponse update(AutorRequest autorRequest, Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isPresent()) {
            Autor autorSalvo = autorRepository.save(autor.get());
            return autorMapper.autorToResponse(autorSalvo);
        }
        return null;
    }

    public boolean delete(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isPresent()) {
            autorRepository.delete(autor.get());
            return true;
        }
        return false;
    }
}