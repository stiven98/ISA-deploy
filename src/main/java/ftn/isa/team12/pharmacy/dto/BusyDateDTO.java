package ftn.isa.team12.pharmacy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class BusyDateDTO {
    private LocalTime start;
    private LocalTime end;
    List<BusyDateDTO> busyDateDTOS = new ArrayList<>();
}
