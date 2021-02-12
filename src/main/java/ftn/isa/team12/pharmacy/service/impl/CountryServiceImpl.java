package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.repository.CountryRepository;
import ftn.isa.team12.pharmacy.service.CountryService;
import ftn.isa.team12.pharmacy.validation.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;
    private CommonValidation commonValidation;



    @Override
    public List<Country> findAll() { return this.countryRepository.findAll(); }



    @Override
    public Country saveAndFlush(Country country) {
        commonValidation = new CommonValidation(country.getName());
        if(!commonValidation.regexValidation("[A-Za-z ]+"))
            throw new IllegalArgumentException("Bad input country.");

        Country existsCountry = this.countryRepository.findByName(country.getName());
        if (existsCountry == null) {
            existsCountry = this.countryRepository.saveAndFlush(country);
        }
        return existsCountry;
    }







}
