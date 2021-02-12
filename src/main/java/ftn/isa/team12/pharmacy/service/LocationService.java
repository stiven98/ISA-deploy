package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.common.Location;

public interface LocationService {
    Location saveAndFlush(Location location);
}
