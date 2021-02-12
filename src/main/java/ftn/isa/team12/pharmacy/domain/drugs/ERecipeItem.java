package ftn.isa.team12.pharmacy.domain.drugs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ERECIPE_ITEM")
public class ERecipeItem implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "item_id", nullable = false, unique = true)
   private UUID itemId;


   @ManyToOne
   @JoinColumn(name = "drug_id", referencedColumnName = "drug_id", nullable = false)
   private Drug drug;


   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "erecipe_id", referencedColumnName = "erecipe_id", nullable = false)
   private ERecipe eRecipe;

   @Column(name = "quantity", nullable = false)
   private int quantity;


}
