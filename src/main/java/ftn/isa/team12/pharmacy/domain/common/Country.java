package ftn.isa.team12.pharmacy.domain.common;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


@Entity
@Table(name = "COUNTRIES")
@Getter
@Setter
@NoArgsConstructor
public class Country implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "country_id", nullable = false, unique = true)
   public UUID countryId;

   @Column(name = "name", nullable = false, unique = true)
   public String name;

   @JsonIgnore
   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "country")
   private Set<City> cities = new HashSet<City>();

   public Country(String name){
      this.name = name;
   }







}
