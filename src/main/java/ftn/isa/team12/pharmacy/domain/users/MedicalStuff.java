package ftn.isa.team12.pharmacy.domain.users;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ftn.isa.team12.pharmacy.domain.common.WorkTime;
import ftn.isa.team12.pharmacy.domain.marks.MedicalStuffMarks;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEDICAL_STUFF")
public abstract class MedicalStuff extends User implements Serializable {

   @Column(name = "AVERAGEMARK")
   private Double averageMark;
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
   @JsonIdentityReference(alwaysAsId = true)
   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "employee")
   private Set<WorkTime> workTime = new HashSet<WorkTime>();
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "examinationId")
   @JsonIdentityReference(alwaysAsId = true)
   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "employee")
   private Set<Examination> examinations = new HashSet<Examination>();
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vacationId")
   @JsonIdentityReference(alwaysAsId = true)
   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "employee")
   private Set<Vacation> vacations = new HashSet<Vacation>();
   @JsonIgnore
   @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "medicalStuff")
   private Set<MedicalStuffMarks> medicalStuffMarks = new HashSet<>();

}
