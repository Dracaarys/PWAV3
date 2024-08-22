package av3.pw.ufrn.controller;

import av3.pw.ufrn.domain.Cliente;
import av3.pw.ufrn.dto.LoginRequestDTO;
import av3.pw.ufrn.dto.RegisterRequestDTO;
import av3.pw.ufrn.dto.ResponseDTO;
import av3.pw.ufrn.infra.security.TokenService;
import av3.pw.ufrn.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Cliente cliente = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), cliente.getSenha())) {
            String token = this.tokenService.GenerateToken(cliente);
            return ResponseEntity.ok(new ResponseDTO(cliente.getEmail(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Cliente> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            Cliente newUser = new Cliente();
            newUser.setSenha(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setNome(body.nome());
            this.repository.save(newUser);

            String token = this.tokenService.GenerateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
