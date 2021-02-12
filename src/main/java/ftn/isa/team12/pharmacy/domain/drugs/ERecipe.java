package ftn.isa.team12.pharmacy.domain.drugs;
import ftn.isa.team12.pharmacy.domain.enums.ERecipeStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ERECIPE")
public class ERecipe implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "erecipe_id", nullable = false, unique = true)
   private UUID eRecipeId;

   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "code", nullable = false, unique = true)
   private String code;

   @Column(name = "e_recipe_status", nullable = false)
   private ERecipeStatus eRecipeStatus;

   @Column(name = "therapy_duration", nullable = false)
   private int duration;

   @Basic
   @Temporal(TemporalType.DATE)
   @Column(name = "dateofissuing", nullable = false)
   private Date dateOfIssuing;

   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
   private Patient patient;

   @ManyToOne
   @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id", nullable = false)
   private Pharmacy pharmacy;

   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "eRecipe")
   private Set<ERecipeItem> eRecipeItems = new HashSet<ERecipeItem>();

}
