package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import ftn.isa.team12.pharmacy.domain.pharmacy.ExaminationPrice;
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
public class ExaminationPriceDTO {
    private UUID examinationPriceId;
    private ExaminationType examinationType;
    private double price;
    private Date startDate;
    private Date endDate;



    public ExaminationPriceDTO(ExaminationPrice ex){
        this(ex.getExaminationPriceId(),ex.getExaminationType(),ex.getPrice(),ex.getDateOfValidity().getStartDate(),ex.getDateOfValidity().getEndDate());
    }




    @Override
    public String toString() {
        return "ExaminationPriceDTO{" +
                "examinationPriceId=" + examinationPriceId +
                ", examinationType=" + examinationType +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
