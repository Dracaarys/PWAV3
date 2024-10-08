package av3.pw.ufrn.service;

import av3.pw.ufrn.domain.Moto;
import av3.pw.ufrn.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Moto> listarMotosPaginadas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return motoRepository.findAll(pageable);
    }
}
