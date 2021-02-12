package ftn.isa.team12.pharmacy.domain.drugs;
import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.enums.ReservationStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "DRUG_RESERVATIONS")
public class DrugReservation implements Serializable {
   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "drug_reservation_id", nullable = false, unique = true)
   private UUID drug_reservation_id;

   @ManyToOne
   @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id", nullable = false)
   private Pharmacy pharmacy;

   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
   private Patient patient;

   @Column(name = "quantity")
   private int quantity;

   @Column(name = "price")
   private double price;

   @Column(name = "discount")
   private double discount;

   @Column(name = "code")
   private String code;

   @Embedded
   @AttributeOverrides({
           @AttributeOverride(name = "startDate" , column = @Column(name = "reservation_start")),
           @AttributeOverride(name = "endDate" , column = @Column(name = "reservation_end"))
   })
   private DateRange reservationDateRange;

   @Column(name = "reservation_status")
   private ReservationStatus reservationStatus;

   @ManyToOne
   @JoinColumn(name = "drug_id", referencedColumnName = "drug_id", nullable = false)
   private Drug drug;

   @Version
   private Long version;

}
