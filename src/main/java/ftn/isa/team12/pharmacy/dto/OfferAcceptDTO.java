package ftn.isa.team12.pharmacy.dto;


import ftn.isa.team12.pharmacy.domain.drugs.Offer;
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
public class OfferAcceptDTO {
    private UUID offerId;
    private UUID drugOrderId;
    private String emailSuplier;
    private double price;
    private Date deadline;
    private String phAdminEmail;



    public OfferAcceptDTO(Offer offer){
        this(offer.getOfferId(),offer.getDrugOrder().getOrderId(),
                offer.getSupplier().getUsername(),offer.getPrice(),offer.getDeadline(),offer.getDrugOrder().getPharmacyAdministrator().getUsername());
    }


    @Override
    public String toString() {
        return "OfferAcceptDTO{" +
                "offerId=" + offerId +
                ", drugOrderId=" + drugOrderId +
                ", emailSuplier='" + emailSuplier + '\'' +
                ", price=" + price +
                ", deadline=" + deadline +
                ", phAdminEmail='" + phAdminEmail + '\'' +
                '}';
    }
}
