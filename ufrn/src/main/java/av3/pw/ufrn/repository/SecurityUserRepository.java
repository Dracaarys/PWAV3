package av3.pw.ufrn.repository;

import av3.pw.ufrn.domain.SecurityUser;
import org.springframework.data.repository.CrudRepository;

public interface SecurityUserRepository extends CrudRepository<SecurityUser, Long> {
    SecurityUser findByEmail(String email);
}
