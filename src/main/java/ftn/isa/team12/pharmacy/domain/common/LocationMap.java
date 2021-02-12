package ftn.isa.team12.pharmacy.domain.common;


import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "LOCATIONMAPS")
public class LocationMap {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "map_id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "geo_width", nullable = false, unique = true)
    private double geographicalWidth;
    @Column(name = "geo_lenght", nullable = false, unique = true)
    private double geographicalLength;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id", nullable = false )
    private Pharmacy pharmacy;

}
