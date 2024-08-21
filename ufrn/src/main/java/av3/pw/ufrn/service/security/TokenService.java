package av3.pw.ufrn.service.security;

import av3.pw.ufrn.core.security.AuthenticationResponse;
import av3.pw.ufrn.repository.ClienteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final ClienteRepository repository;
    private final JwtEncoder encoder;

    public TokenService(ClienteRepository repository, JwtEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }


    public AuthenticationResponse generateToken(UserDetails userDetails) {

        Instant now = Instant.now();
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("PWAV3")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();


        return AuthenticationResponse.builder().token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()).build();
    }



}
