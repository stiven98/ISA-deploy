package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.enums.FormOfDrug;
import ftn.isa.team12.pharmacy.domain.enums.TypeOfDrug;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewDrugDTO {
    private String name;
    private String code;
    private int dailyDose;
    private String note;
    private String manufacturer;
    private TypeOfDrug typeOfDrug;
    private FormOfDrug formOfDrug;
    private List<String> substituteDrug;
    private List<String> contraindication;
    private List<String> ingredients;
    private String issuanceRegime;
    private int points;

    @Override
    public String toString() {
        return "NewDrugDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", dailyDose=" + dailyDose +
                ", note='" + note + '\'' +
                ", typeOfDrug='" + typeOfDrug + '\'' +
                ", formOfDrug='" + formOfDrug + '\'' +
                ", substituteDrug=" + substituteDrug +
                ", contraindication=" + contraindication +
                ", ingredients=" + ingredients +
                ", issuanceRegime='" + issuanceRegime + '\'' +
                '}';
    }
}
