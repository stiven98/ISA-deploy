package ftn.isa.team12.pharmacy.domain.pharmacy;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationStatus;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EXAMINATIONS")
public class Examination implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "examination_id", nullable = false, unique = true)
    private UUID examinationId;
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_examination", nullable = false)
    private Date dateOfExamination;
    @Basic
    @Column(name = "time_of_examination", nullable = false)
    private LocalTime timeOfExamination;
    @Column(name = "duration")
    private long duration;
    @Column(name = "discount")
    private double discount;
    @Column(name = "examination_status")
    private ExaminationStatus examinationStatus;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;
    @OneToOne
    @JoinColumn(name = "examination_price_id", referencedColumnName = "examination_price_id")
    private ExaminationPrice examinationPrice;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "user_id")
    private MedicalStuff employee;
    @Column(name = "note")
    private String note;
    @Column(name="examination_type")
    private ExaminationType examinationType;

    @Version
    private Long version;

}
