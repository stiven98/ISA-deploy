package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.repository.CityRepository;
import ftn.isa.team12.pharmacy.service.CityService;
import ftn.isa.team12.pharmacy.validation.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    private CommonValidation commonValidation;



    @Override
    public List<City> findAll() { return this.cityRepository.findAll(); }

    @Override
    public List<City> findByCountry(String name) {
        return this.cityRepository.findByCountry(name);
    }

    @Override
    public City saveAndFlush(City city) {
       commonValidation = new CommonValidation(city.getName());
        if(!commonValidation.regexValidation("[A-Za-z ]+"))
            throw new IllegalArgumentException("Bad input city!");
        City existsCity = this.cityRepository.findByNameAndCountryId(city.getName(), city.getCountry().getCountryId());
        if (existsCity == null) {
            existsCity = this.cityRepository.saveAndFlush(city);
        }
        return existsCity;
    }

}
