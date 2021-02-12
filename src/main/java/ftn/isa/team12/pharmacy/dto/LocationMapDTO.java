package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.common.LocationMap;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationMapDTO {
    private UUID pharmacyID;
    private double geographicalWidth;
    private double geographicalLength;


    public LocationMapDTO(Pharmacy ph, LocationMap l) {
        this(ph.getId(), l.getGeographicalWidth(),l.getGeographicalLength());
    }
}
