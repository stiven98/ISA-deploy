package ftn.isa.team12.pharmacy.dto;


import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReportsAverageMarksDTO {

    private String pharmacyName;
    private double averageMarkPharmacy;
    List<ReportsEmployeeDTO>  employeeDTOS = new ArrayList<>();


    public ReportsAverageMarksDTO(Pharmacy pharmacy) {
        this.pharmacyName = pharmacy.getName();
        this.averageMarkPharmacy = pharmacy.getAverageMark();
    }

}
