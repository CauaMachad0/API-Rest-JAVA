
package br.com.fiap.api_rest.mapper;

import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.AutorResponse;
import br.com.fiap.api_rest.model.Autor;

public class AutorMapper {

    public Autor requestToAutor(AutorRequest autorRequest) {
        return new Autor(autorRequest.nome());
    }

    public AutorResponse autorToResponse(Autor autor) {
        return new AutorResponse(
                autor.getId(),
                autor.getNome()
        );
    }
}
