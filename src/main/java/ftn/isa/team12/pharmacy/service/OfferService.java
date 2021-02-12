package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.drugs.Offer;
import ftn.isa.team12.pharmacy.dto.OfferAcceptDTO;
import ftn.isa.team12.pharmacy.dto.OfferDTO;

import java.util.List;
import java.util.UUID;

public interface OfferService {

    List<Offer> getOfferByIdSupplier(UUID id);

    Offer addOffer(OfferDTO offerRequest);

    Offer saveAndFlush(Offer offer);

    Offer findById(UUID id);


    List<OfferAcceptDTO> findByDrugOrderId(UUID oferId);

    Offer acceptOffer(OfferAcceptDTO dto);
}
