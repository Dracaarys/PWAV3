package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Aluguel;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.dto.AluguelDto;
import av3.pw.ufrn.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import av3.pw.ufrn.repository.AluguelRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    private ModelMapper modelMapper;
    @Autowired
    public AluguelService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public List<Aluguel> listarAlugueis() {
        return aluguelRepository.findAll();
    }

    public Optional<Aluguel> buscarAluguelPorId(Long id) {
        return aluguelRepository.findById(id);
    }

    public Aluguel salvarAluguel(AluguelDto aluguelDto, Long usuarioId) {
        Optional<Cliente> usuarioOptional = clienteRepository.findById(usuarioId);

        if (usuarioOptional.isPresent()) {
            Aluguel aluguel = modelMapper.map(aluguelDto, Aluguel.class);
            aluguel.setCliente(usuarioOptional.get());

            // Mapear manualmente MotoDto para Moto
            if (aluguelDto.getMotos() != null) {
                List<Moto> motos = aluguelDto.getMotos().stream()
                        .map(motoDto -> modelMapper.map(motoDto, Moto.class))
                        .collect(Collectors.toList());
                aluguel.setMotos(motos);
            }

            return aluguelRepository.save(aluguel);
        } else {
            throw new RuntimeException("Usuario not found");
        }
    }

    public void deletarAluguel(Long id) {
        aluguelRepository.deleteById(id);
    }
}
