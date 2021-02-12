package ftn.isa.team12.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleExaminationDTO {
   private UUID userId;
    private String pharmacyName;
    private Date date;
    private LocalTime time;
    private String patientEmail;
}
