package ftn.isa.team12.pharmacy.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class PromotionDTO {
    private UUID id;
    private Date startDate;
    private Date endDate;
    private String text;

}
