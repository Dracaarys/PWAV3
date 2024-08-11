package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Aluguel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import av3.pw.ufrn.repository.AluguelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    public List<Aluguel> listarAlugueis() {
        return aluguelRepository.findAll();
    }

    public Optional<Aluguel> buscarAluguelPorId(Long id) {
        return aluguelRepository.findById(id);
    }

    public Aluguel salvarAluguel(Aluguel aluguel) {
        return aluguelRepository.save(aluguel);
    }

    public void deletarAluguel(Long id) {
        aluguelRepository.deleteById(id);
    }
}
