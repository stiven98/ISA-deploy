package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationScheduleMedStuffDTO {
    private UUID patientId;
    private UUID pharmacyId;
    private UUID medStuffId;
    private Date date;
    private LocalTime time;
    private ExaminationType type;

    @Override
    public String toString() {
        return "ExaminationScheduleMedStuffDTO{" +
                "patientId=" + patientId +
                ", pharmacyId=" + pharmacyId +
                ", medStuffId=" + medStuffId +
                ", date=" + date +
                ", time=" + time +
                ", type=" + type +
                '}';
    }
}
