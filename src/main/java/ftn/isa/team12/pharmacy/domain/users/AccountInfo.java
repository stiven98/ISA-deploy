package ftn.isa.team12.pharmacy.domain.users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountInfo implements Serializable {

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "lastname", nullable = false)
   private String lastName;

   @Column(name = "phonenumber", nullable = false)
   private String phoneNumber;

   @Column(name = "active", nullable = false)
   private boolean active;

   @Column(name = "firstlogin", nullable = false)
   private boolean isFirstLogin;

}
