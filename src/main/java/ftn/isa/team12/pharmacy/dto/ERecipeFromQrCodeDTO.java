package ftn.isa.team12.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ERecipeFromQrCodeDTO {
    private String email;
    private List<QrCodeItem> qrCodeItems;
    private String pharmacyId;

    @Override
    public String toString() {
        return "ERecipeFromQrCodeDTO{" +
                "email='" + email + '\'' +
                ", qrCodeItems=" + qrCodeItems +
                ", pharmacyQRDTO=" + pharmacyId +
                '}';
    }
}
