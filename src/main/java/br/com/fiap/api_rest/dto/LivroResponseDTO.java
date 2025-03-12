package br.com.fiap.api_rest.dto;

import org.springframework.hateoas.Link;

public record LivroResponseDTO(Long id, String infoLivro, Link link) {
}