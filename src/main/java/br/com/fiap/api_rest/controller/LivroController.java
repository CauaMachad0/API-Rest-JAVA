package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroResponse;
import br.com.fiap.api_rest.mapper.LivroMapper;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
import br.com.fiap.api_rest.service.AutorService;
import br.com.fiap.api_rest.service.BibliotecaService;
import br.com.fiap.api_rest.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/livros")
@Tag(name = "api-livros")
public class LivroController {
    @Autowired
    private LivroService livroService;
    private final LivroMapper livroMapper = new LivroMapper();
    @Autowired
    private AutorService autorService;
    @Autowired
    private BibliotecaService bibliotecaService;

    // CREATE, READ, UPDATE, DELETE
    // POST, GET, PUT, DELETE

    @Operation(summary = "Cria um novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<LivroResponse> createLivro(@Valid @RequestBody LivroRequest livroRequest) {
        List<Autor> autores = autorService.saveAll(livroRequest.getAutores());
        Livro livro = livroMapper.requestToLivro(livroRequest);
        livro.setAutores(autores);
        livro.setBiblioteca(bibliotecaService.findBibliotecaById(livroRequest.getBiblioteca()));
        return new ResponseEntity<>(livroService.save(livro),HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os livros por páginas")
    @GetMapping
    public ResponseEntity<Page<LivroResponse>> readLivros(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest
                .of(pageNumber, 2, Sort.by("titulo").ascending());
        return new ResponseEntity<>(livroService.findAll(pageable), HttpStatus.OK);
    }

    // @PathVariable localhost:8080/livros/1
    // @RequestParam localhost:8080/livros/?id=1
    @Operation(summary = "Retorna um livro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> readLivro(@PathVariable Long id) {
        LivroResponse livroResponse = livroService.findById(id);
        if (livroResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livroResponse,HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "400", description = "Nenhum livro encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> updateLivro(@PathVariable Long id,
                                                     @RequestBody LivroRequest livroRequest) {
        Livro livro = livroService.findLivroById(id);
        if (livro == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Autor> autores = autorService.saveAll(livroRequest.getAutores());
        Livro livroConvertido = livroMapper.requestToLivro(livroRequest);
        livroConvertido.setId(livro.getId());
        livroConvertido.setAutores(autores);
        return new ResponseEntity<>(livroService.save(livroConvertido),HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um livro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro excluído com sucesso",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Nenhum livro encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        boolean deletado = livroService.deleteById(id);
        if (!deletado) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}