package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
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
public class ExaminationRetValDTO {

    private UUID examinationId;
    private Date date;
    private LocalTime time;
    private ExaminationType type;

    public ExaminationRetValDTO(Examination examination){
        this.examinationId = examination.getExaminationId();
        this.date = examination.getDateOfExamination();
        this.time = examination.getTimeOfExamination();
        this.type = examination.getExaminationType();
    }
}
