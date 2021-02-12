package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportsEmployeeDTO {

    private String employeeName;
    private String employeeSurName;
    private double employeeAverageMarks;
    private String employeeType;


    public ReportsEmployeeDTO(MedicalStuff medicalStuff,String type){
        this(medicalStuff.getAccountInfo().getName(),medicalStuff.getAccountInfo().getLastName(),medicalStuff.getAverageMark(),type);
    }

}
