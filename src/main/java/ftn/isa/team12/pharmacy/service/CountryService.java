package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.common.Country;
import java.util.List;

public interface CountryService {
    List<Country> findAll();
    Country saveAndFlush(Country country);

}
