package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDTO {
    String name;
    Double averageMarke;
    String description;
    String cityName;


    public PharmacyDTO(Pharmacy pharmacy){
        this(pharmacy.getName(),pharmacy.getAverageMark(),pharmacy.getDescription(), pharmacy.getLocation().getCityName());
    }


}
