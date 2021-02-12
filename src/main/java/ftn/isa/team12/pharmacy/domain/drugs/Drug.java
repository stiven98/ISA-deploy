package ftn.isa.team12.pharmacy.domain.drugs;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ftn.isa.team12.pharmacy.domain.enums.FormOfDrug;
import ftn.isa.team12.pharmacy.domain.enums.IssuanceRegime;
import ftn.isa.team12.pharmacy.domain.enums.TypeOfDrug;
import ftn.isa.team12.pharmacy.domain.marks.DrugMarks;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DRUGS")
public class Drug implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "drug_id", nullable = false, unique = true)
   private UUID drugId;

   @Column(name = "name", nullable = false, unique = true)
   private String name;

   @Column(name = "code", nullable = false, unique = true)
   private String code;

   @Column(name = "typeofdrug", nullable = false)
   private TypeOfDrug typeOfDrug;

   @Column(name = "formofdrug", nullable = false)
   private FormOfDrug formOfDrug;

   @ManyToMany(mappedBy = "allergies")
   @JsonIgnore
   private Set<Patient> patientsAllergies = new HashSet<Patient>();

   @OneToMany(mappedBy = "drug")
   @JsonIgnore
   private Set<DrugMarks> drugMarks = new HashSet<DrugMarks>();

   @Column(name = "issuanceregime", nullable = false)
   private IssuanceRegime issuanceRegime;

   @Column(name = "note")
   private String note;

   @Column(name = "points")
   private int points;

   @Column(name = "AVERAGEMARK")
   private Double averageMark;

   @ManyToOne
   @JoinColumn(name = "manufacturer_id", referencedColumnName = "manufacturer_id", nullable = false )
   private Manufacturer manufacturer;


   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "drug")
   private Set<DrugPrice> priceList = new HashSet<DrugPrice>();

   @ManyToMany
   @JoinTable(name = "substitute_drugs", joinColumns = @JoinColumn(name="drug_id" ,  referencedColumnName  = "drug_id"),
           inverseJoinColumns = @JoinColumn(name = "substitute_drug_id", referencedColumnName = "drug_id"))
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "drugId")
   @JsonIdentityReference(alwaysAsId = true)
   private Set<Drug> substituteDrugs = new HashSet<Drug>();

   @ManyToMany
   @JoinTable(name = "drugs_contraindications", joinColumns = @JoinColumn(name="drug_id" ,  referencedColumnName  = "drug_id"),
           inverseJoinColumns = @JoinColumn(name = "contraindication_id", referencedColumnName = "contraindication_id"))
   private Set<Contraindication> contraindications = new HashSet<Contraindication>();

   @ManyToMany
   @JoinTable(name = "ingredients_in_drugs", joinColumns = @JoinColumn(name="drug_id" ,  referencedColumnName  = "drug_id"),
           inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredient_id"))
   private Set<Ingredient> ingredients = new HashSet<Ingredient>();


   @Override
   public String toString() {
      return "Drug{" +
              "drugId=" + drugId +
              ", name='" + name + '\'' +
              ", code='" + code + '\'' +
              ", typeOfDrug=" + typeOfDrug +
              ", formOfDrug=" + formOfDrug +
              ", ingredients=" + ingredients +
              ", IssuanceRegime=" + issuanceRegime+
              ", note='" + note + '\'' +
              ", manufacturer=" + manufacturer +
              '}';
   }
}
