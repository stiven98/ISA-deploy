package ftn.isa.team12.pharmacy.dto;
import ftn.isa.team12.pharmacy.domain.users.Authority;
import ftn.isa.team12.pharmacy.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String cityName;
    public String countryName;
    private int zipCode;
    private String street;
    private int streetNumber;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String role;
    private boolean firstLogin;

    public UserDTO(User user){
        this.email = user.getUsername();
        this.password = "";
        this.cityName= user.getLocation().getCity().getName();
        this.countryName= user.getLocation().getCity().getCountry().getName();
        this.zipCode= user.getLocation().getCity().getZipCode();
        this.street = user.getLocation().getAddress().getStreet();
        this.streetNumber = user.getLocation().getAddress().getNumber();
        this.name = user.getAccountInfo().getName();
        this.lastName = user.getAccountInfo().getLastName();
        this.phoneNumber = user.getAccountInfo().getPhoneNumber();
        this.firstLogin = user.getAccountInfo().isFirstLogin();
        List<Authority> authorities = new ArrayList<>();
        user.getAuthorities().stream().forEach(a -> authorities.add((Authority) a));
        Authority a = authorities.stream().findFirst().orElseGet(null);
        if(a != null)
            this.role = a.getRole();
        else
            this.role = "";
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", zipCode=" + zipCode +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
