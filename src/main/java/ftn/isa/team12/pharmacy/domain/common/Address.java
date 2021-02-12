package ftn.isa.team12.pharmacy.domain.common;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address implements Serializable {

   @Column(name = "street", nullable = false)
   private String street;

   @Column(name = "number", nullable = false)
   private int number;


   public Address(Address address){
      this(address.getStreet(), address.getNumber());
   }

}
