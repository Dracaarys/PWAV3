package av3.pw.ufrn.controller.security;

import av3.pw.ufrn.core.security.AuthenticationResponse;
import av3.pw.ufrn.core.security.RegisterRequest;
import av3.pw.ufrn.dto.security.LoginDto;
import av3.pw.ufrn.repository.ClienteRepository;
import av3.pw.ufrn.service.security.AuthenticationService;
import av3.pw.ufrn.service.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {


    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private final AuthenticationService service;

    private final ClienteRepository repository;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, AuthenticationService service, ClienteRepository repository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/login")
    public AuthenticationResponse token(@RequestBody LoginDto loginDTO) {
        System.out.println("entrou no login");
        System.out.println(loginDTO.email());
        System.out.println(repository.findByEmail(loginDTO.email()).orElseThrow());
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
                );


        return tokenService.generateToken((UserDetails) repository.findByEmail(loginDTO.email()).orElseThrow());
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }


}