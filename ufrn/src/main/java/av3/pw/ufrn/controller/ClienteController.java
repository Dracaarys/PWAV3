package av3.pw.ufrn.controller;


import av3.pw.ufrn.domain.*;
import av3.pw.ufrn.dto.*;
import av3.pw.ufrn.service.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final ModelMapper mapper;

    @GetMapping
    public List<ClienteResponseDto> listAll() {
        return service.listarClientes().stream()
                .map(cliente -> mapper.map(cliente, ClienteResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> create(@RequestBody ClienteRequestDto clienteRequestDTO) {
        Cliente cliente = mapper.map(clienteRequestDTO, Cliente.class);

        Cliente created = service.salvarCliente(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(created.getId())
                .toUri();

        ClienteResponseDto ClienteResponseDto = mapper.map(created, ClienteResponseDto.class);

        ClienteResponseDto.add(linkTo(ClienteController.class).slash(created.getId()).withSelfRel());
        if (created.getEndereco() != null) {
            ClienteResponseDto.add(linkTo(EnderecoController.class).slash(created.getEndereco().getId()).withRel("endereco"));
        }

        return ResponseEntity.created(location).body(ClienteResponseDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDto> listById(@PathVariable("id") Long id) {
        Optional<Cliente> clienteOptional = service.buscarClientePorId(id);

        if (clienteOptional.isPresent()) {
            ClienteResponseDto ClienteResponseDto = mapper.map(clienteOptional.get(), ClienteResponseDto.class);
            return ResponseEntity.ok(ClienteResponseDto);
        }
        throw new EntityNotFoundException("Cliente id " + id + " não encontrado");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        Optional<Cliente> clienteOptional = service.buscarClientePorId(id);
        if (clienteOptional.isPresent()) {
            service.deletarCliente(id);
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException("Cliente id " + id + " não encontrado");
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteResponseDto> update(@RequestBody ClienteRequestDto clienteRequestDTO, @PathVariable("id") Long id) {
        Optional<Cliente> clienteOptional = service.buscarClientePorId(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = mapper.map(clienteRequestDTO, Cliente.class);
            Cliente updated = service.salvarCliente(cliente);

            ClienteResponseDto ClienteResponseDto = mapper.map(updated, ClienteResponseDto.class);
            return ResponseEntity.ok(ClienteResponseDto);
        }

        Cliente cliente = mapper.map(clienteRequestDTO, Cliente.class);
        Cliente created = service.salvarCliente(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(created.getId())
                .toUri();

        ClienteResponseDto ClienteResponseDto = mapper.map(created, ClienteResponseDto.class);
        return ResponseEntity.created(location).body(ClienteResponseDto);
    }
}

