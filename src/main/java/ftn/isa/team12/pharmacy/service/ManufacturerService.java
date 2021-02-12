package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.drugs.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> findAll();

    Manufacturer findById(String manufacturer);
}
