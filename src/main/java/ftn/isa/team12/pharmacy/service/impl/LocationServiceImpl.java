package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.repository.LocationRepository;
import ftn.isa.team12.pharmacy.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location saveAndFlush(Location location) {
        Location existsLocation = this.locationRepository.findByLocationAndCity(location.getAddress().getStreet(), location.getAddress().getNumber(), location.getCity().getCityId());
        if (existsLocation == null) {
            existsLocation = this.locationRepository.saveAndFlush(location);
        }
        return existsLocation;
    }







}
