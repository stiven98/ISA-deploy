package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.enums.FormOfDrug;
import ftn.isa.team12.pharmacy.domain.enums.IssuanceRegime;
import ftn.isa.team12.pharmacy.domain.enums.TypeOfDrug;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugForOrderDTO {

    private UUID id;
    private String name;
    private String code;
    private TypeOfDrug typeOfDrug;
    private FormOfDrug formOfDrug;
    private IssuanceRegime issuanceRegime;
    private String note;
    private String manufactureName;
    private int quantity;


    public DrugForOrderDTO(Drug drug){
        this(drug.getDrugId(),drug.getName(),drug.getCode(),drug.getTypeOfDrug(),drug.getFormOfDrug(),drug.getIssuanceRegime(),drug.getNote(),drug.getManufacturer().getName(),0);
    }

    public DrugForOrderDTO(Drug drug, int quantity ){
        this(drug.getDrugId(),drug.getName(),drug.getCode(),drug.getTypeOfDrug(),drug.getFormOfDrug(),drug.getIssuanceRegime(),drug.getNote(),drug.getManufacturer().getName(),quantity);
    }

    @Override
    public String toString() {
        return "DrugForOrderDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", typeOfDrug=" + typeOfDrug +
                ", formOfDrug=" + formOfDrug +
                ", issuanceRegime=" + issuanceRegime +
                ", note='" + note + '\'' +
                ", manufactureName='" + manufactureName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
