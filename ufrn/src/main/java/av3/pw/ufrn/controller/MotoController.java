package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.dto.MotoDto;
import av3.pw.ufrn.service.MotoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/motos")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class MotoController {

    private final MotoService motoService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<MotoDto> listarMotos() {
        return motoService.listarMotos().stream()
                .map(moto -> modelMapper.map(moto, MotoDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<MotoDto>> listarMotosPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Moto> motosPage = motoService.listarMotosPaginadas(page, size);
        Page<MotoDto> motoDtosPage = motosPage.map(moto -> modelMapper.map(moto, MotoDto.class));
        return ResponseEntity.ok(motoDtosPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDto> buscarMotoPorId(@PathVariable Long id) {
        return motoService.buscarMotoPorId(id)
                .map(moto -> ResponseEntity.ok(modelMapper.map(moto, MotoDto.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MotoDto> createMoto(@RequestBody MotoDto motoDto) {
        Moto moto = modelMapper.map(motoDto, Moto.class);
        Moto savedMoto = motoService.salvarMoto(moto);
        MotoDto savedMotoDto = modelMapper.map(savedMoto, MotoDto.class);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMotoDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedMotoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDto> atualizarMoto(@PathVariable Long id, @RequestBody MotoDto motoDto) {
        return motoService.buscarMotoPorId(id)
                .map(motoExistente -> {
                    motoExistente.setModelo(motoDto.getModelo());
                    motoExistente.setMarca(motoDto.getMarca());
                    motoExistente.setAno(motoDto.getAno());

                    Moto motoAtualizada = motoService.salvarMoto(motoExistente);
                    return ResponseEntity.ok(modelMapper.map(motoAtualizada, MotoDto.class));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarMoto(@PathVariable Long id) {
        motoService.buscarMotoPorId(id).ifPresent(moto -> motoService.deletarMoto(id));
    }
}
