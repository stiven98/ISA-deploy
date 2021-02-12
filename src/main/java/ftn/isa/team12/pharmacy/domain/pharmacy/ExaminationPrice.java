package ftn.isa.team12.pharmacy.domain.pharmacy;
import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
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
@Table(name = "EXAMINATION_PRICES")
public class ExaminationPrice  implements Serializable  {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "examination_price_id", nullable = false, unique = true)
    private UUID examinationPriceId;
    @Column(name="examination_type")
    private ExaminationType examinationType;
    @Column(name="price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;
    @Embedded
    private DateRange dateOfValidity;
}
