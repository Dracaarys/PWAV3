package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.domain.Endereco;
import av3.pw.ufrn.dto.ClienteRequestDto;
import av3.pw.ufrn.dto.ClienteResponseDto;
import av3.pw.ufrn.service.ClienteService;
import com.jayway.jsonpath.JsonPath;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final ModelMapper mapper;

    @GetMapping
    public List<ClienteResponseDto> listAll() {
        return service.listarClientes(); // O serviço já adiciona os links HATEOAS
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
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        service.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> update(@RequestBody ClienteRequestDto clienteRequestDTO, @PathVariable("id") Long id) {
        ClienteResponseDto clienteResponseDto = service.atualizarCliente(id, clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDto);
    }


    private ClienteResponseDto convertToDto(Cliente cliente) {
        ClienteResponseDto clienteResponseDto = mapper.map(cliente, ClienteResponseDto.class);
        clienteResponseDto.addLinks(cliente);
        return clienteResponseDto;
    }

    private Cliente convertToEntity(ClienteRequestDto clienteRequestDto) {
        Cliente cliente = mapper.map(clienteRequestDto, Cliente.class);
        Endereco endereco = mapper.map(clienteRequestDto.getEndereco(), Endereco.class);
        cliente.setEndereco(endereco);
        return cliente;
    }
}
