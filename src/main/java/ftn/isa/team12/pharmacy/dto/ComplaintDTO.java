package ftn.isa.team12.pharmacy.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComplaintDTO {
    private String emailPatient;
    private String pharmacyId;
    private String medicalStaffId;
    private String content;
    private String forWho;


    @Override
    public String toString() {
        return "ComplaintDTO{" +
                "emailPatient='" + emailPatient + '\'' +
                ", pharmacyId='" + pharmacyId + '\'' +
                ", medicalStaffId='" + medicalStaffId + '\'' +
                ", content='" + content + '\'' +
                ", forWho='" + forWho + '\'' +
                '}';
    }
}
