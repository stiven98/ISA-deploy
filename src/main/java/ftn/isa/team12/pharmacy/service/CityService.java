package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.common.City;
import java.util.List;

public interface CityService {

    List<City> findAll();
    List<City> findByCountry(String name);
    City saveAndFlush(City city);
}
