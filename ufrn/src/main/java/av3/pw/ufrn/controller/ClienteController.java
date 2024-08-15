package av3.pw.ufrn.controller;

import av3.pw.ufrn.dto.ClienteRequestDto;
import av3.pw.ufrn.dto.ClienteResponseDto;
import av3.pw.ufrn.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public List<ClienteResponseDto> listAll() {
        return service.listarClientes();
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> create(@RequestBody ClienteRequestDto clienteRequestDTO) {
        ClienteResponseDto clienteResponseDto = service.salvarCliente(clienteRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteResponseDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(clienteResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> listById(@PathVariable("id") Long id) {
        ClienteResponseDto clienteResponseDto = service.buscarClientePorId(id);
        return ResponseEntity.ok(clienteResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {  // ALTERAÇÃO FEITA PARA O CONTROLADOR RECEBER SOMENTE A REQUISÃO HTTP  E INVOCAR OS METODOS DO SERVICE
        service.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> update(@RequestBody ClienteRequestDto clienteRequestDTO, @PathVariable("id") Long id) {
        ClienteResponseDto clienteResponseDto = service.atualizarCliente(id, clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDto);
    }
}
