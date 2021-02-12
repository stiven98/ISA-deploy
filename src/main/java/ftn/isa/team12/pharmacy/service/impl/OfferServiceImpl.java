package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.drugs.DrugOrder;
import ftn.isa.team12.pharmacy.domain.drugs.DrugOrderItem;
import ftn.isa.team12.pharmacy.domain.drugs.Offer;
import ftn.isa.team12.pharmacy.domain.enums.DrugOrderStatus;
import ftn.isa.team12.pharmacy.domain.enums.OfferStatus;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.domain.users.Supplier;
import ftn.isa.team12.pharmacy.dto.OfferAcceptDTO;
import ftn.isa.team12.pharmacy.dto.OfferDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.DrugInPharmacyRepository;
import ftn.isa.team12.pharmacy.repository.DrugOrderRepository;
import ftn.isa.team12.pharmacy.repository.OfferRepository;
import ftn.isa.team12.pharmacy.service.DrugOrderService;
import ftn.isa.team12.pharmacy.service.DrugService;
import ftn.isa.team12.pharmacy.service.OfferService;
import ftn.isa.team12.pharmacy.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private DrugOrderService drugOrderService;

    @Autowired
    DrugOrderRepository drugOrderRepository;

    @Autowired
    DrugInPharmacyRepository drugInPharmacyRepository;

    @Autowired
    EmailSender emailSender;

    @Override
    public List<Offer> getOfferByIdSupplier(UUID id) {
        return offerRepository.getOfferBySupplier(id);
    }

    @Override
    public Offer addOffer(OfferDTO offerRequest) {
        Offer offer = new Offer();
        offer.setStatus(OfferStatus.waiting);
        offer.setPrice(offerRequest.getPrice());
        offer.setDeadline(offerRequest.getDeliveryTime());
        Supplier supplier = supplierService.findByEmail(offerRequest.getEmail());
        if (supplier == null) {
            throw new IllegalArgumentException("Bad email of supplier");
        }


        // if(!supplier.getAvailableDrugs().containsAll(drugService.getByIds(offerRequest.getIds()))){
        //     throw new IllegalArgumentException("Supplier don't have all drugs!");
        // }


        offer.setSupplier(supplier);
        DrugOrder drugOrder = drugOrderService.findById(offerRequest.getOrderId());
        if (drugOrder == null) {
            throw new IllegalArgumentException("Bad order ID!");
        }

        offer.setDrugOrder(drugOrder);
        return this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public Offer saveAndFlush(Offer offer) {
        return this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public Offer findById(UUID id) {
        return this.offerRepository.findById(id).get();
    }

    @Override
    public List<OfferAcceptDTO> findByDrugOrderId(UUID orderId) {
        List<Offer> offers = offerRepository.findAllByDrugOrderOrderId(orderId);
        if(offers == null)
            throw new IllegalArgumentException("No ofer for drug order id " + orderId.toString());

        List<OfferAcceptDTO> dto = new ArrayList<>();
        for(Offer o: offers)
            dto.add(new OfferAcceptDTO(o));

        return dto;
    }


    @Override
    @Transactional(readOnly = false)
    public Offer acceptOffer(OfferAcceptDTO dto) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        DrugOrder drugOrder = drugOrderRepository.findByOrderId(dto.getDrugOrderId());
        Offer offer = offerRepository.findByOfferId(dto.getOfferId());

        if(!dto.getPhAdminEmail().equals(pharmacyAdministrator.getLoginInfo().getEmail()))
            throw new IllegalArgumentException("This administrator not create drugOrder");

        if(offer == null)
            throw new IllegalArgumentException("This offer no for drug order with id " + dto.getDrugOrderId());

        if(!drugOrder.getPharmacyAdministrator().getLoginInfo().getEmail().equals(pharmacyAdministrator.getLoginInfo().getEmail()))
            throw new IllegalArgumentException("Pharmacy admin not crate this drug order with code " + dto.getDrugOrderId());

        if(drugOrder.getDeadline().after(new Date()))
            throw new IllegalArgumentException("Time for give offer not expire");

        if(drugOrder.getDrugOrderStatus() == DrugOrderStatus.processed)
            throw new IllegalArgumentException("This order processed already");

        List<Offer> offers = offerRepository.findAllByDrugOrderOrderId(drugOrder.getOrderId());

        offer.setStatus(OfferStatus.accepted);
        offer = offerRepository.save(offer);

        for(Offer o: offers){
            if(o.getOfferId() != offer.getOfferId()) {
                o.setStatus(OfferStatus.declined);
                Offer declineOffer =  offerRepository.save(o);
                if(declineOffer == null){
                    throw new IllegalArgumentException("Can't update decline offer");
                }
            }
        }

        if(offer != null) {
            for (DrugOrderItem doi : drugOrder.getDrugOrderItems()) {
                DrugInPharmacy drugInPharmacy = drugInPharmacyRepository.findDrugInPharmacy(doi.getDrug().getDrugId(), drugOrder.getPharmacy().getId());
                drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() + doi.getQuantity());
                drugInPharmacy = drugInPharmacyRepository.save(drugInPharmacy);
                if(drugInPharmacy == null){
                    throw new IllegalArgumentException("Can't update drug in pharmacy quantity");
                }
            }
        }

        drugOrder.setDrugOrderStatus(DrugOrderStatus.processed);
        drugOrder = drugOrderRepository.save(drugOrder);

        if(drugOrder == null)
            throw new IllegalArgumentException("Can't update drug order");

        try{
            for (Offer o: offers) {
                if(o.getOfferId() == offer.getOfferId()) {
                    emailSender.sendEmailToSupplier(dto.getDrugOrderId(), dto.getEmailSuplier(), " accepted");
                }else{

                    emailSender.sendEmailToSupplier(dto.getDrugOrderId(), dto.getEmailSuplier(), " declined");
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return offer;
    }
}
