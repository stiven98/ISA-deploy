package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.enums.VacationRequestStatus;
import ftn.isa.team12.pharmacy.domain.users.VacationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacationDTO {
    private UUID vacationId;
    private String email;
    private Date startDate;
    private Date endDate;
    private VacationRequestStatus status;
    private String note;


    public VacationDTO(VacationRequest vacationRequest){
        this(vacationRequest.getVacationId(),vacationRequest.getEmployee().getLoginInfo().getEmail(),vacationRequest.getDateRange().getStartDate(),
                vacationRequest.getDateRange().getEndDate(),vacationRequest.getStatus(),vacationRequest.getNote());
    }


}
