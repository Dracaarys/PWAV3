package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Aluguel;
import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.dto.AluguelDto;
import av3.pw.ufrn.dto.MotoDto;
import av3.pw.ufrn.service.AluguelService;
import av3.pw.ufrn.service.MotoService;
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
    private final MotoService motoService;
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

    @PostMapping("/{usuarioId}")
    public ResponseEntity<AluguelDto> criarAluguel(@PathVariable Long usuarioId, @RequestBody AluguelDto aluguelDto) {
        Aluguel aluguelCriado = aluguelService.salvarAluguel(aluguelDto, usuarioId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aluguelCriado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(aluguelCriado, AluguelDto.class));
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<AluguelDto> atualizarAluguel(@PathVariable Long id, @RequestBody AluguelDto aluguelDto) {
        return aluguelService.buscarAluguelPorId(id)
                .map(aluguelExistente -> {
                    aluguelExistente.setDataInicio(aluguelDto.getDataInicio());
                    aluguelExistente.setDataFim(aluguelDto.getDataFim());

                    // Mapear manualmente MotoDto para Moto
                    if (aluguelDto.getMotos() != null) {
                        List<Moto> motos = aluguelDto.getMotos().stream()
                                .map(motoDto -> modelMapper.map(motoDto, Moto.class))
                                .collect(Collectors.toList());
                        aluguelExistente.setMotos(motos);
                    }

                    Aluguel aluguelAtualizado = aluguelService.salvarAluguel(aluguelExistente);
                    return ResponseEntity.ok(modelMapper.map(aluguelAtualizado, AluguelDto.class));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAluguel(@PathVariable Long id) {
        aluguelService.buscarAluguelPorId(id).ifPresent(aluguel -> aluguelService.deletarAluguel(id));
    }
}
