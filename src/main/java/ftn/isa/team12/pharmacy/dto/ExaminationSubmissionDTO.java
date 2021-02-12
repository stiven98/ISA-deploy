package ftn.isa.team12.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationSubmissionDTO {
    private String note;
    private int therapyDuration;
    private List<UUID> drugIds = new ArrayList<>();
    private UUID patientId;
    private UUID pharmacyId;
    private UUID examinationId;
}
