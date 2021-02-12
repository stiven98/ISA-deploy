package ftn.isa.team12.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {

    private double price;
    private Date deliveryTime;
    private UUID orderId;
    private String email;
    private List<String> ids;

    @Override
    public String toString() {
        return "OfferDTO{" +
                "price=" + price +
                ", deliveryTime=" + deliveryTime +
                ", orderId=" + orderId +
                ", email='" + email + '\'' +
                '}';
    }
}
