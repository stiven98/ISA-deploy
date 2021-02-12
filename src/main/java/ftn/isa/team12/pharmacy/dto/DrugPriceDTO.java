package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.drugs.DrugPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugPriceDTO {
    private UUID drugPrice;
    private UUID idDrug;
    private double price;
    private Date startDate;
    private Date endDate;
    private String drugName;

    public DrugPriceDTO(DrugPrice drugPrice){
        this(drugPrice.getId(),drugPrice.getDrug().getDrugId(),drugPrice.getPrice(),drugPrice.getValidityPeriod().getStartDate(),
                drugPrice.getValidityPeriod().getEndDate(),drugPrice.getDrug().getName());
    }


    @Override
    public String toString() {
        return "DrugPriceDTO{" +
                "drugPrice=" + drugPrice +
                ", idDrug=" + idDrug +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", drugName='" + drugName + '\'' +
                '}';
    }
}
