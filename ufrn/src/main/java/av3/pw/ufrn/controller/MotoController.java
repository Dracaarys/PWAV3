package av3.pw.ufrn.controller;


import av3.pw.ufrn.domain.Moto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import av3.pw.ufrn.service.MotoService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/motos")
public class MotoController {
    @Autowired
    private MotoService motoService;

    @GetMapping
    public List<Moto> listarMotos() {
        return motoService.listarMotos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarMotoPorId(@PathVariable Long id) {
        Optional<Moto> moto = motoService.buscarMotoPorId(id);
        return moto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Moto criarMoto(@RequestBody Moto moto) {
        return motoService.salvarMoto(moto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizarMoto(@PathVariable Long id, @RequestBody Moto motoAtualizada) {
        return motoService.buscarMotoPorId(id)
                .map(moto -> {
                    moto.setModelo(motoAtualizada.getModelo());
                    moto.setMarca(motoAtualizada.getMarca());
                    moto.setAno(motoAtualizada.getAno());
                    moto.setDisponivel(motoAtualizada.isDisponivel());
                    Moto motoSalva = motoService.salvarMoto(moto);
                    return ResponseEntity.ok(motoSalva);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMoto(@PathVariable Long id) {
        motoService.deletarMoto(id);
        return ResponseEntity.noContent().build();
    }
}
