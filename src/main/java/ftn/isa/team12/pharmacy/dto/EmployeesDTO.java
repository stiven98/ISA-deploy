package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.users.Dermatologist;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesDTO {
    String name;
    String lastName;
    Double averageMark;
    String nummber;
    String email;
    List<PharmacyDTO> pharmacies = new ArrayList<>();


    public EmployeesDTO(Dermatologist dermatologist, List<PharmacyDTO> phDto){
        this(dermatologist.getAccountInfo().getName(),dermatologist.getAccountInfo().getLastName(),
                dermatologist.getAverageMark(),dermatologist.getAccountInfo().getPhoneNumber(),dermatologist.getLoginInfo().getEmail(), phDto);
    }

    public EmployeesDTO(Pharmacist pharmacist, List<PharmacyDTO> phDto){
        this(pharmacist.getAccountInfo().getName(),pharmacist.getAccountInfo().getLastName(),
                pharmacist.getAverageMark(),pharmacist.getAccountInfo().getPhoneNumber(),pharmacist.getLoginInfo().getEmail(), phDto);
    }
}
