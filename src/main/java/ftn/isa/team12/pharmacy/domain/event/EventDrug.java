package ftn.isa.team12.pharmacy.domain.event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EVENTDRUG")
public class EventDrug {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "event_drug_id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "drug_id", nullable = false)
    private UUID drugID;

    @Column(name = "drug_name", nullable = false)
    private String drugName;

    @Column(name = "pharmacy_name", nullable = false)
    private String pharmacyName;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_event", nullable = false)
    private Date dateOfEvent;

}
