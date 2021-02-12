package ftn.isa.team12.pharmacy.domain.users;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "SUPPLIERS")
public class Supplier extends User implements Serializable {

    @ManyToMany
    @JoinTable(name = "drugs_by_supplier", joinColumns = @JoinColumn(name="user_id" ,  referencedColumnName  = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "drug_id"))
    private Set<Drug> availableDrugs = new HashSet<Drug>();



}
