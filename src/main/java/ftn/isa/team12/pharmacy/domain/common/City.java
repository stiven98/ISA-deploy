package ftn.isa.team12.pharmacy.domain.common;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name="CITIES")
@Getter
@Setter
@NoArgsConstructor
public class City implements Serializable {
   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "city_id", nullable = false, unique = true)
   private UUID cityId;

   @Column(name = "name", nullable = false, unique=true)
   private String name;

   @Column(name = "zip_code", nullable = false, unique = false)
   private int zipCode;

   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "countryId")
   @JsonIdentityReference(alwaysAsId = true)
   @ManyToOne
   @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false)
   private Country country;




   public City(String name, Country country, int zipCode){
      this.name = name;
      this.country = country;
      this.zipCode = zipCode;
   }






}
