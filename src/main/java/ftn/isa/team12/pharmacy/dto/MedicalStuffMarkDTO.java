package ftn.isa.team12.pharmacy.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicalStuffMarkDTO {
    private String medicalStuffEmail;
    private String patientEmail;
    private double mark;
}
