package ftn.isa.team12.pharmacy.domain.drugs;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ftn.isa.team12.pharmacy.domain.enums.DrugOrderStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;


@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
@Table(name = "DRUG_ORDERS")
public class DrugOrder implements Serializable {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "drugorder_id", nullable = false, unique = true)
   private UUID orderId;

   @ManyToOne
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
   @JsonIdentityReference(alwaysAsId = true)
   @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id", nullable = false)
   private Pharmacy pharmacy;

   @Column(name = "drug_order_status")
   private DrugOrderStatus drugOrderStatus;

   @ManyToOne
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
   @JsonIdentityReference(alwaysAsId = true)
   @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
   private PharmacyAdministrator pharmacyAdministrator;

   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "drugOrder")
   private Set<DrugOrderItem> drugOrderItems = new HashSet<DrugOrderItem>();

   @Basic
   @Temporal(TemporalType.DATE)
   @Column(name = "deadline")
   private Date deadline;

}
