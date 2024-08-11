package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Moto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import av3.pw.ufrn.repository.MotoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MotoService {
    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }

    public Optional<Moto> buscarMotoPorId(Long id) {
        return motoRepository.findById(id);
    }

    public Moto salvarMoto(Moto moto) {
        return motoRepository.save(moto);
    }

    public void deletarMoto(Long id) {
        motoRepository.deleteById(id);
    }

}
