package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponse;
import br.com.fiap.api_rest.service.BibliotecaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping
    public ResponseEntity<BibliotecaResponse> criarBiblioteca(@Valid @RequestBody BibliotecaRequest request) {
        BibliotecaResponse biblioteca = bibliotecaService.criarBiblioteca(request);
        return ResponseEntity.ok(biblioteca);
    }

    @GetMapping
    public ResponseEntity<List<BibliotecaResponse>> listarBibliotecas() {
        return ResponseEntity.ok(bibliotecaService.listarBibliotecas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaResponse> buscarBiblioteca(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.buscarBibliotecaPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBiblioteca(@PathVariable Long id) {
        bibliotecaService.deletarBiblioteca(id);
        return ResponseEntity.noContent().build();
    }
}
