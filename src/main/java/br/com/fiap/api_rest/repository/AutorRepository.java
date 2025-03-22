package br.com.fiap.api_rest.repository;

import br.com.fiap.api_rest.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String autorNome);
}
