package ftn.isa.team12.pharmacy.domain.drugs;
import ftn.isa.team12.pharmacy.domain.enums.OfferStatus;
import ftn.isa.team12.pharmacy.domain.users.Supplier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "OFFERS")
public class Offer implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "offer_id", nullable = false, unique = true)
    private UUID offerId;

    @ManyToOne
    @JoinColumn(name = "drugorder_id", referencedColumnName = "drugorder_id", nullable = false)
    private DrugOrder drugOrder;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Supplier supplier;

    @Column(name = "price")
    private double price;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "status")
    private OfferStatus status;



}
