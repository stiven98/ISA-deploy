package ftn.isa.team12.pharmacy.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportsMonthlyDTO {
    private List<Integer> numberOfExamination = new ArrayList<>();
    private List<String> days = new ArrayList<>();
}
