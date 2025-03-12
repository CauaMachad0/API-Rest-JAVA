package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroResponseDTO;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
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

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/livros", consumes = {"application/json"})
@Tag(name = "api-livros")
public class LivroController {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private LivroService livroService;

    // CREATE, READ, UPDATE, DELETE
    // POST, GET, PUT, DELETE
    @Operation(summary = "Cria um novo livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                content = {@Content(
                        mediaType = "app",
                        schema = @Schema(implementation = Livro.class))}),
        @ApiResponse(responseCode = "400", description = "Parâmetro inválidos",
                content = {@Content(schema = @Schema())} )
    })
    @PostMapping
    public ResponseEntity<Livro> createLivro(@Valid @RequestBody LivroRequest livro) {
        Livro livroSalvo = livroRepository.save(livroService.requestToLivro(livro));
        return new ResponseEntity<>(livroSalvo,HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os livros por página")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = {@Content(
                            mediaType = "app",
                            schema = @Schema(implementation = Livro.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválidos",
                    content = {@Content(schema = @Schema())} )
    })
    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> readLivros(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest
                .of(pageNumber, 2, Sort.by("autor").ascending()
                        .and(Sort.by("titulo").ascending()));
//        Page<LivroResponse> livros = livroService.findAll(pageable);
//        for (LivroResponse livro : livros) {
//            livro.setLink(
//                    linkTo(
//                            methodOn(LivroController.class)
//                                    .readLivro(livro.getId())
//                    ).withSelfRel()
//            );
//        }
        return new ResponseEntity<>(livroService.findAllDTO(pageable), HttpStatus.OK);
    }

    // @PathVariable localhost:8080/livros/1
    // @RequestParam localhost:8080/livros/?id=1
    @Operation(summary = "Retorna um livro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro encontrado com sucesso",
                    content = {@Content(
                            mediaType = "app",
                            schema = @Schema(implementation = LivroResponseDTO.class))}),
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> readLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        LivroResponse livroResponse = livroService.livroToResponse(livro.get());
//        livroResponse.setLink(
//                linkTo(
//                        methodOn(LivroController.class)
//                                .readLivros(0)
//                ).withRel("Lista de Livros")
//        );
        LivroResponseDTO livroResponseDTO = livroService.livroToResponseDTO(livro.get(), false);
        return new ResponseEntity<>(livroResponseDTO,HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = {@Content(
                            mediaType = "app",
                            schema = @Schema(implementation = Livro.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválidos",
                    content = {@Content(schema = @Schema())} )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id,
                                             @RequestBody LivroRequest livro) {
        Optional<Livro> livroExistente = livroRepository.findById(id);
        if (livroExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Livro livroConvertido = livroService.requestToLivro(livro);
        livroConvertido.setId(livroExistente.get().getId());
        Livro livroSalvo = livroRepository.save(livroConvertido);
        return new ResponseEntity<>(livroSalvo,HttpStatus.CREATED);
    }

    @Operation(summary = "Deleta um livro existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = {@Content(
                            mediaType = "app",
                            schema = @Schema(implementation = Livro.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválidos",
                    content = {@Content(schema = @Schema())} )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}