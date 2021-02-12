package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.users.Authority;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.dto.LoginDTO;
import ftn.isa.team12.pharmacy.dto.LoginResponseDTO;
import ftn.isa.team12.pharmacy.security.TokenUtils;
import ftn.isa.team12.pharmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> createAuthenticationToken(@RequestBody LoginDTO authenticationRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        List<Authority> authorities = new ArrayList<>();
        user.getAuthorities().stream().forEach(a -> authorities.add((Authority) a));
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new LoginResponseDTO(jwt, (long)expiresIn, authorities, user.getUserId()));
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<LoginResponseDTO> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();
            List<Authority> authorities = new ArrayList<>();
            user.getAuthorities().stream().forEach(a -> authorities.add((Authority) a));
            return ResponseEntity.ok(new LoginResponseDTO(refreshedToken,(long)expiresIn, authorities, user.getUserId()));
        } else {
            LoginResponseDTO userTokenState = new LoginResponseDTO();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

}
