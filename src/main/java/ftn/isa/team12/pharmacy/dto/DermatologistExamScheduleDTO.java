package ftn.isa.team12.pharmacy.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DermatologistExamScheduleDTO {
    private UUID examinationId;
    private String patientEmail;
}
