package ftn.isa.team12.pharmacy.dto;


import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyChangeDTO {
    private UUID pharmacyID;
    private String text;
    private String name;

    public PharmacyChangeDTO(Pharmacy pharmacy){
        this(pharmacy.getId(),pharmacy.getDescription(),pharmacy.getName());
    }


}
