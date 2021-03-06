package ftn.isa.team12.pharmacy.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChangeDrugPriceDTO {

    private UUID drugPrice;
    private double price;
}
