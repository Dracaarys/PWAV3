package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Aluguel;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.repository.AluguelRepository;
import av3.pw.ufrn.repository.ClienteRepository;
import av3.pw.ufrn.repository.MotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MotoRepository motoRepository;

    public List<Aluguel> listarAlugueis() {
        return aluguelRepository.findAll();
    }

    public Optional<Aluguel> buscarAluguelPorId(Long id) {
        return aluguelRepository.findById(id);
    }

    public void deletarAluguel(Long id) {
        aluguelRepository.deleteById(id);
    }
    @Transactional
    public Aluguel save(Long clienteId, Aluguel aluguel) {
        // Recupera o cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Atualiza as motos
        List<Moto> motos = aluguel.getMotos();
        if (motos != null) {
            for (Moto moto : motos) {
                Moto foundMoto = motoRepository.findById(moto.getId())
                        .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
                foundMoto.setDisponivel(false); // ou qualquer lógica de atualização
                motoRepository.save(foundMoto);
            }
        }

        // Associa o cliente ao aluguel
        aluguel.setCliente(cliente);

        // Salva o aluguel
        return aluguelRepository.save(aluguel);
    }


    public Aluguel atualizarAluguel(Long id, Aluguel aluguelAtualizado) {
        Aluguel aluguelExistente = aluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluguel id " + id + " não encontrado"));
    
        aluguelExistente.setDataInicio(aluguelAtualizado.getDataInicio());
        aluguelExistente.setDataFim(aluguelAtualizado.getDataFim());
        aluguelExistente.setMotos(aluguelAtualizado.getMotos());
 
        if (aluguelAtualizado.getMotos() != null) {
            for (Moto moto : aluguelAtualizado.getMotos()) {
                Moto motoExistente = motoRepository.findById(moto.getId())
                        .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
                motoExistente.setDisponivel(false);
                motoRepository.save(motoExistente);
            }
        }

        Aluguel aluguelSalvo = aluguelRepository.save(aluguelExistente);
    
        return aluguelSalvo;
    }
}