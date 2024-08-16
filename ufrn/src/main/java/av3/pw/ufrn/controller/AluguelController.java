package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Aluguel;
import av3.pw.ufrn.dto.AluguelDto;
import av3.pw.ufrn.service.AluguelService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class AluguelController {

    private final AluguelService aluguelService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<AluguelDto> listarAlugueis() {
        List<Aluguel> alugueis = aluguelService.listarAlugueis();
        return alugueis.stream()
                .map(aluguel -> modelMapper.map(aluguel, AluguelDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelDto> buscarAluguelPorId(@PathVariable Long id) {
        return aluguelService.buscarAluguelPorId(id)
                .map(aluguel -> ResponseEntity.ok(modelMapper.map(aluguel, AluguelDto.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<Aluguel> createAluguel(@PathVariable Long clienteId, @RequestBody Aluguel aluguel) {
        Aluguel savedAluguel = aluguelService.save(clienteId, aluguel);
        return ResponseEntity.ok(savedAluguel);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAluguel(@PathVariable Long id) {
        aluguelService.buscarAluguelPorId(id).ifPresent(aluguel -> aluguelService.deletarAluguel(id));
    }
}