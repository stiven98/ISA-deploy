package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.dto.PasswordChangeDTO;
import ftn.isa.team12.pharmacy.dto.UserDTO;
import ftn.isa.team12.pharmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/getUser")
    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PH_ADMIN', 'ROLE_DERMATOLOGIST', 'ROLE_SYSTEM_ADMINISTRATOR', 'ROLE_SUPPLIER', 'ROLE_PHARMACIST')") // Dodati ostale role
    public ResponseEntity<UserDTO> user(Principal user) {
        User userDetails = this.userService.findUserByEmail(user.getName());
        UserDTO dto = new UserDTO(userDetails);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //treba dodati role da ne moze ne ulogovan da pristupi stranici
    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PH_ADMIN', 'ROLE_DERMATOLOGIST', 'ROLE_SYSTEM_ADMINISTRATOR', 'ROLE_SUPPLIER', 'ROLE_PHARMACIST')") // Dodati ostale role
    @PostMapping("/change")
    public ResponseEntity<UserDTO> changeAccountInfo(@RequestBody UserDTO userDto) {
            userService.checkCurrentUserCredentials(userDto.getPassword());
            User user = userService.findUserByEmail(userDto.getEmail());
            UserDTO changedUser = userService.changeAccountInfo(user, userDto);
            if (changedUser == null)
                return new ResponseEntity<UserDTO>(changedUser, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<UserDTO>(changedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PH_ADMIN', 'ROLE_DERMATOLOGIST', 'ROLE_SYSTEM_ADMINISTRATOR', 'ROLE_SUPPLIER', 'ROLE_PHARMACIST')") // Dodati ostale role
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwords) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        boolean isPasswordValid = userService.checkCurrentUserCredentials(passwords.getOldPassword());
        Map<String, String> result = new HashMap<>();
        if(!isPasswordValid){
            result.put("result", "Bad credentials");
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        User userToChange = userService.findUserByEmail(email);
        boolean isPasswordChanged = userService.changePassword(userToChange, passwords.getPassword());
        if (!isPasswordChanged){
            result.put("result", "Password is not in the appropriate format");
            return ResponseEntity.badRequest().body(result);
        }
        result.put("result", "Password successfully changed");
        return ResponseEntity.accepted().body(result);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getByID(@PathVariable UUID id) {
        User user = userService.findByUserId(id);
        UserDTO dto = new UserDTO(user);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/activateAccount/{id}")
    public void activateAccount(@PathVariable String id, HttpServletResponse httpServletResponse) {

        User user = this.userService.updateStatus(UUID.fromString(id));
        httpServletResponse.setHeader("Location", "http://localhost:4200/login");
        httpServletResponse.setStatus(302);
    }

}
