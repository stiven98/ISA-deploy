package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.drugs.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @Query("select s from Offer s where s.supplier.userId = ?1")
    List<Offer> getOfferBySupplier(UUID id);


    List<Offer> findAllByDrugOrderOrderId(UUID id);


    Offer findByOfferId(UUID offerId);

}
