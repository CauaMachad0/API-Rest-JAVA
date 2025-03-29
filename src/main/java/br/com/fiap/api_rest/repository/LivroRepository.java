package br.com.fiap.api_rest.repository;

import br.com.fiap.api_rest.model.Categoria;
import br.com.fiap.api_rest.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}