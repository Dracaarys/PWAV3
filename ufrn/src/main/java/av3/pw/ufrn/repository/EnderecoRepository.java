package av3.pw.ufrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import av3.pw.ufrn.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}