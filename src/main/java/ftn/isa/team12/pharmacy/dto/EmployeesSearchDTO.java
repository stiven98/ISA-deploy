package ftn.isa.team12.pharmacy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeesSearchDTO {
    String name;
    String lastName;
    String role;
    String email;
}
