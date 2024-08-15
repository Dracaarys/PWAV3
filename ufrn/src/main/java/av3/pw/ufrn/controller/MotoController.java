package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//controlador feito ""
@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listarMotos() {
        List<Moto> motos = motoService.listarMotos();
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarMotoPorId(@PathVariable Long id) {
        Optional<Moto> moto = motoService.buscarMotoPorId(id);
        return moto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Moto> criarMoto(@RequestBody Moto moto) {
        Moto motoCriada = motoService.salvarMoto(moto);
        return ResponseEntity.ok(motoCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizarMoto(@PathVariable Long id, @RequestBody Moto motoAtualizada) {
        Optional<Moto> moto = motoService.buscarMotoPorId(id);

        if (moto.isPresent()) {
            Moto motoExistente = moto.get();
            motoExistente.setModelo(motoAtualizada.getModelo());
            motoExistente.setMarca(motoAtualizada.getMarca());
            motoExistente.setAno(motoAtualizada.getAno());
            motoExistente.setDisponivel(motoAtualizada.isDisponivel());

            Moto motoSalva = motoService.salvarMoto(motoExistente);
            return ResponseEntity.ok(motoSalva);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMoto(@PathVariable Long id) {
        if (motoService.buscarMotoPorId(id).isPresent()) {
            motoService.deletarMoto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
