package net.tayebi.j2eeebankingbackendfinalprojectmodule.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.support.JettyHeadersAdapter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;


    @GetMapping("/profile")
//    @PreAuthorize("HasRole("USER")")
    public Authentication authenticate(Authentication authentication) {
        return authentication;
    }
    @PostMapping("/login")
        public Map<String,String > login(String username, String password) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // genere les jwt
        Instant instant = Instant.now();
        String scope=authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(""));

        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .issuer("http://localhost:8080") // aui a gennr e token
                .claim("scope", scope)
                .build();
        //enmcoder en rapplelant quelle algo
        JwtEncoderParameters jwtEncoderParameters=JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),jwtClaimsSet);
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        // retourne le ss form de json comme cle access tokn
        return Map.of("access_token",jwt);

    }
}