package av3.pw.ufrn.controller.security;

import av3.pw.ufrn.dto.security.LoginDto;
import av3.pw.ufrn.service.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final TokenService service;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/token")
    public String getToken(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
                );

        return service.generateToken(authentication);
    }
}
