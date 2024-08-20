package av3.pw.ufrn.repository;

import av3.pw.ufrn.domain.SecurityUser;
import org.springframework.data.repository.CrudRepository;

public interface SecurityUserRerpository extends CrudRepository<SecurityUser, Long> {
    SecurityUser findByUsername(String username);
}
