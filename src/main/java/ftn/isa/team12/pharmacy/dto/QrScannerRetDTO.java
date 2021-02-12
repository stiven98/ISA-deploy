package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QrScannerRetDTO {

    List<QrCodeItem> drugs;
    List<PharmacyQRDTO> pharmacies;

    public QrScannerRetDTO(List<QrCodeItem> drugs) {
        this.drugs = drugs;
        pharmacies = new ArrayList<>();
    }


}
