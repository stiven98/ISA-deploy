package ftn.isa.team12.pharmacy.domain.common;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "LOCATIONS")
@Getter
@Setter
@NoArgsConstructor
public class Location implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "location_id", nullable = false, unique = true)
   private UUID locationId;

   @ManyToOne
   @JoinColumn(name = "city_id", referencedColumnName = "city_id", nullable = false, unique = false)
   private City city;

   @Embedded
   private Address address;



   public  Location(City city, Address address){
      this.city=city;
      this.address=address;
   }

   public String getCityName(){
      return city.getName();
   }

   public String getCountryName(){
      return city.getCountry().getName();
   }







}
