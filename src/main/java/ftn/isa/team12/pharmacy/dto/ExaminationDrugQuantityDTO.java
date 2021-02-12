package ftn.isa.team12.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationDrugQuantityDTO {
    private UUID drugId;
    private UUID pharmacyId;
    private UUID patientId;
}
