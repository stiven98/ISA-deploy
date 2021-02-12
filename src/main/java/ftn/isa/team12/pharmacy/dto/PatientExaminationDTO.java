package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientExaminationDTO {
    private UUID id;
    private String name;
    private String lastName;
    private Date examinationDate;
    private String email;
    private String phoneNumber;

    public PatientExaminationDTO(Patient patient, Examination examination){
        this(patient.getUserId(), patient.getAccountInfo().getName(), patient.getAccountInfo().getLastName(), examination.getDateOfExamination(), patient.getUsername(), patient.getAccountInfo().getPhoneNumber());
    }
}
