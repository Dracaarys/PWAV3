package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.dto.ClienteRequestDto;
import av3.pw.ufrn.dto.ClienteResponseDto;
import av3.pw.ufrn.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper mapper;

    public List<ClienteResponseDto> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> mapper.map(cliente, ClienteResponseDto.class))
                .collect(Collectors.toList());
    }

    public ClienteResponseDto buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente id " + id + " não encontrado"));
        return mapper.map(cliente, ClienteResponseDto.class);
    }

    public ClienteResponseDto salvarCliente(ClienteRequestDto clienteRequestDTO) {
        Cliente cliente = mapper.map(clienteRequestDTO, Cliente.class);
        Cliente savedCliente = clienteRepository.save(cliente);
        return mapper.map(savedCliente, ClienteResponseDto.class);
    }

    public ClienteResponseDto atualizarCliente(Long id, ClienteRequestDto clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente id " + id + " não encontrado"));
        mapper.map(clienteRequestDTO, cliente);
        Cliente updatedCliente = clienteRepository.save(cliente);
        return mapper.map(updatedCliente, ClienteResponseDto.class);
    }

    public void deletarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente id " + id + " não encontrado"));
        clienteRepository.delete(cliente);
    }
}
