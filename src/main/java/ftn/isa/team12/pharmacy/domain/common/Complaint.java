package ftn.isa.team12.pharmacy.domain.common;

import ftn.isa.team12.pharmacy.domain.enums.StatusOfComplaint;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Complaint")
@Getter
@Setter
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "complaint_id", nullable = false, unique = true)
    public UUID complaintId;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "medical_stuff_id", referencedColumnName = "user_id")
    private MedicalStuff medicalStuff;

    @ManyToOne
    @JoinColumn(name = "patietn_id", referencedColumnName = "user_id", nullable = false)
    private Patient patient;

    @Column(name = "name", nullable = false)
    private StatusOfComplaint statusOfComplaint;

    @Version
    private Integer version;
}
