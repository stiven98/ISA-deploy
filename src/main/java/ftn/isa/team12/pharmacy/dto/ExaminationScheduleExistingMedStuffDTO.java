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
public class ExaminationScheduleExistingMedStuffDTO {
    private UUID patientId;
    private UUID pharmacyId;
    private UUID medStuffId;
    private UUID examinationId;
}
