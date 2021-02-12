package ftn.isa.team12.pharmacy.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeDTO {
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;


    @Override
    public String toString() {
        return "WorkTimeDTO{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
