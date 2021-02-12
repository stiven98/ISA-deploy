package ftn.isa.team12.pharmacy.domain.users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "PATIENTS")
public class Patient extends User implements Serializable {
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "allergies", joinColumns = @JoinColumn(name="user_id" ,  referencedColumnName  = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "drug_id"))
    private Set<Drug> allergies = new HashSet<Drug>();

    //@Scheduled(cron = "")
    @Column(name = "penalties")
    private int penalties;

    @Embedded
    private AccountCategory category;

    @JsonIgnore
    @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "patient")
    private Set<Examination> examinations = new HashSet<Examination>();

    @ManyToMany
    @JoinTable(name = "subscribed", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id"))
    private Set<Pharmacy> subscribedPharmacies = new HashSet<>();

}
