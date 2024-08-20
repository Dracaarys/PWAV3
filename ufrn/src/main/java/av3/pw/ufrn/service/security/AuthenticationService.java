package av3.pw.ufrn.service.security;

import av3.pw.ufrn.domain.SecurityUser;
import av3.pw.ufrn.repository.SecurityUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    SecurityUserRepository repository;
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        Optional<SecurityUser> credencials = Optional.ofNullable(repository.findByEmail(Email));

        if(credencials.isPresent()){
            return credencials.get();
        } else {
            throw new UsernameNotFoundException("Usuário não cadastrado com email: " + Email);
        }
    }
}