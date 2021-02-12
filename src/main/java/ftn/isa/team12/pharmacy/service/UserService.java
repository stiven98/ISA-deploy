package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User findById(UUID id);
    User findUserByEmail(String email);
    List<User> findAll ();
    UserDTO changeAccountInfo(User user, UserDTO dto);
    boolean changePassword(User user, String newPassword);
    boolean checkCurrentUserCredentials(String password);
    User findByUserId(UUID id);
    User updateStatus(UUID id);

}
