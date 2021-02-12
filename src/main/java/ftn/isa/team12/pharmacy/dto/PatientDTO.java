package ftn.isa.team12.pharmacy.dto;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.users.AccountCategory;
import ftn.isa.team12.pharmacy.domain.users.AccountInfo;
import ftn.isa.team12.pharmacy.domain.users.LoginInfo;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private UUID userId;
    private LoginInfo loginInfo;
    private Location location;
    private AccountInfo accountInfo;
    private int penalties;
    private AccountCategory category;

    public PatientDTO(Patient patient){
        this(patient.getUserId(),patient.getLoginInfo(),patient.getLocation(),patient.getAccountInfo(),patient.getPenalties(),patient.getCategory());
    }
}
