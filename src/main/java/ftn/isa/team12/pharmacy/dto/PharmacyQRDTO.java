package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.common.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyQRDTO {
    private UUID id;
    private String name;
    private double price;
    private Location location;
    private double averageMark;



}
