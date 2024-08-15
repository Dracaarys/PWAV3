package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Endereco;
import av3.pw.ufrn.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizarEndereco(Long id, Endereco enderecoAtualizado) {
        return enderecoRepository.findById(id)
                .map(endereco -> {
                    endereco.setRua(enderecoAtualizado.getRua());
                    endereco.setCidade(enderecoAtualizado.getCidade());
                    endereco.setEstado(enderecoAtualizado.getEstado());
                    endereco.setCep(enderecoAtualizado.getCep());
                    return enderecoRepository.save(endereco);
                })
                .orElseThrow(() -> new EntityNotFoundException("Endereço id " + id + " não encontrado"));
    }

    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}