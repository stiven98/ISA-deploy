package ftn.isa.team12.pharmacy.domain.users;
import ftn.isa.team12.pharmacy.domain.enums.UserCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AccountCategory implements Serializable {
    @Column(name = "points", nullable = false)
    private int points;
    @Column(name = "category", nullable = false)
    private UserCategory category;


}
