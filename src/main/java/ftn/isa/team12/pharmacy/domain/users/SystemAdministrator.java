package ftn.isa.team12.pharmacy.domain.users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "SYSTEM_ADMINISTRATORS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SystemAdministrator extends User implements Serializable {

}
