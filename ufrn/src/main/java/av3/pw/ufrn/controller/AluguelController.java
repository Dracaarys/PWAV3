package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Aluguel;
import av3.pw.ufrn.dto.AluguelDto;
import av3.pw.ufrn.service.AluguelService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aluguel")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AluguelController {

    private final AluguelService aluguelService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<AluguelDto> listarAlugueis() {
        List<Aluguel> alugueis = aluguelService.listarAlugueis();
        return alugueis.stream()
                .map(aluguel -> {
                    AluguelDto aluguelDto = modelMapper.map(aluguel, AluguelDto.class);
                    aluguelDto.addLinks(aluguel); // Adiciona links ao DTO
                    return aluguelDto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelDto> buscarAluguelPorId(@PathVariable Long id) {
        return aluguelService.buscarAluguelPorId(id)
                .map(aluguel -> {
                    AluguelDto aluguelDto = modelMapper.map(aluguel, AluguelDto.class);
                    aluguelDto.addLinks(aluguel); // Adiciona links ao DTO
                    return ResponseEntity.ok(aluguelDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<AluguelDto> createAluguel(@PathVariable Long clienteId, @RequestBody Aluguel aluguel) {
        Aluguel savedAluguel = aluguelService.save(clienteId, aluguel);
        AluguelDto savedAluguelDto = modelMapper.map(savedAluguel, AluguelDto.class);
        savedAluguelDto.addLinks(savedAluguel); // Adiciona links ao DTO
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAluguelDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAluguelDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAluguel(@PathVariable Long id) {
        aluguelService.buscarAluguelPorId(id).ifPresent(aluguel -> aluguelService.deletarAluguel(id));
    }
}