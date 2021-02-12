package ftn.isa.team12.pharmacy.domain.common;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "WORK_TIME")
@Getter
@Setter
@NoArgsConstructor
public class WorkTime implements Serializable  {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "work_time_id", nullable = false, unique = true)
    private UUID id;
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "starttime", nullable = false)
    private LocalTime startTime;
    @Basic
    @Column(name = "endtime", nullable = false)
    private LocalTime endTime;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "user_id", nullable = false)
    private MedicalStuff employee;
    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

}
