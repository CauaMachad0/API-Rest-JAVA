package br.com.fiap.api_rest.dto;

import org.springframework.hateoas.Link;

public class LivroResponse {
    private Long id;
    private String infoLivro;
    private Link link;

    public LivroResponse(Long id, String infoLivro) {
        this.id = id;
        this.infoLivro = infoLivro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoLivro() {
        return infoLivro;
    }

    public void setInfoLivro(String infoLivro) {
        this.infoLivro = infoLivro;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}