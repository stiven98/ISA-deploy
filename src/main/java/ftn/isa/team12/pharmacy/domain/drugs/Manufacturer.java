package ftn.isa.team12.pharmacy.domain.drugs;
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
@Table(name = "MANUFACTURER")
public class Manufacturer implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "manufacturer_id", nullable = false, unique = true)
   private UUID manufacturerId;

   @Column(name = "name", nullable = false, unique = true)
   private String name;

   }
