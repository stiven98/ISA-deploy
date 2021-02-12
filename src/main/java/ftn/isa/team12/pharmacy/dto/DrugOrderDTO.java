package ftn.isa.team12.pharmacy.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugOrderDTO {

    private String pharmacyAdminEmail;
    private Date deadline;
    private Set<DrugForOrderDTO> drugOrderItems = new HashSet<>();

}
