package ftn.isa.team12.pharmacy.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugReservationDTO {

    private String patientEmail;
    private Date deadline;
    private UUID pharmacyId;
    private int quantity;
    private UUID drugId;
    private double price;

}
