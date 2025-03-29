package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponse;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.service.BibliotecaService;
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

@RestController
@RequestMapping(value = "/biblioteca")
@Tag(name = "api-biblioteca")
public class BibliotecaController {
    @Autowired
    private BibliotecaService bibliotecaService;

    // CREATE, READ, UPDATE, DELETE
    // POST, GET, PUT, DELETE

    @Operation(summary = "Cria uma nova biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Biblioteca criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Biblioteca.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<BibliotecaResponse> createBiblioteca(@Valid @RequestBody BibliotecaRequest biblioteca) {
        return new ResponseEntity<>(bibliotecaService.save(biblioteca),HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todas as bibliotecas por páginas")
    @GetMapping
    public ResponseEntity<Page<BibliotecaResponse>> readBibliotecas(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest
                .of(pageNumber, 2, Sort.by("nome").ascending());
        return new ResponseEntity<>(bibliotecaService.findAll(pageable), HttpStatus.OK);
    }

    // @PathVariable localhost:8080/bibliotecas/1
    // @RequestParam localhost:8080/bibliotecas/?id=1
    @Operation(summary = "Retorna uma biblioteca por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BibliotecaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma biblioteca encontrada para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaResponse> readBiblioteca(@PathVariable Long id) {
        BibliotecaResponse biblioteca = bibliotecaService.findById(id);
        if (biblioteca == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(biblioteca,HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um biblioteca existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Biblioteca atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Biblioteca.class))),
            @ApiResponse(responseCode = "400", description = "Nenhuma biblioteca encontrada para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaResponse> updateBiblioteca(@PathVariable Long id,
                                                               @RequestBody BibliotecaRequest bibliotecaRequest) {
        BibliotecaResponse biblioteca = bibliotecaService.update(bibliotecaRequest, id);
        if (biblioteca == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(biblioteca,HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um biblioteca por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca excluída com sucesso",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Nenhuma biblioteca encontrada para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBiblioteca(@PathVariable Long id) {
        boolean salvo = bibliotecaService.delete(id);
        if (!salvo) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}