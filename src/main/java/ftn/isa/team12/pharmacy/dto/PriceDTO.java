package ftn.isa.team12.pharmacy.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class PriceDTO {
    private UUID pharmacyId;
    private UUID drugId;
}
