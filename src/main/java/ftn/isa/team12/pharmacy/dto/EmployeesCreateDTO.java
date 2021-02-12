package ftn.isa.team12.pharmacy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeesCreateDTO {
    UserDTO user;
    String emailPhAdmin;
    List<WorkTimeDTO> workTimes = new ArrayList<>();


    @Override
    public String toString() {
        return "EmployeesCreateDTO{" +
                "user=" + user +
                ", emailPhAdmin='" + emailPhAdmin + '\'' +
                ", workTimes=" + workTimes +
                '}';
    }
}
