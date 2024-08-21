package av3.pw.ufrn.service.security;

import av3.pw.ufrn.core.security.AuthenticationResponse;
import av3.pw.ufrn.core.security.RegisterRequest;
import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.domain.Role;
import av3.pw.ufrn.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final ClienteRepository repository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse register(RegisterRequest request){
        var cliente = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(Role.USER)
                .build();


        repository.save(cliente);
        var token =  tokenService.generateToken((UserDetails) cliente).toString();
        return AuthenticationResponse.builder().token(token).build();

    }

}