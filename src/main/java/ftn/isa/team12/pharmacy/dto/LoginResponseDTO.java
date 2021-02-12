package ftn.isa.team12.pharmacy.dto;
import ftn.isa.team12.pharmacy.domain.users.Authority;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class LoginResponseDTO {

    private String accessToken;
    private Long expiresIn;
    private List<Authority> authorities;
    private UUID userId;

    public LoginResponseDTO() {
        this.accessToken = null;
        this.expiresIn = null;
        this.authorities = new ArrayList<>();
        this.userId = null;
    }

    public LoginResponseDTO(String accessToken, Long expiresIn, List<Authority> authorities, UUID userId) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.authorities = authorities;
        this.userId = userId;
    }
}
