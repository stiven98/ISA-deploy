package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.drugs.*;
import ftn.isa.team12.pharmacy.domain.enums.DrugOrderStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.domain.users.Supplier;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugOrderPhAdminDTO;
import ftn.isa.team12.pharmacy.repository.DrugOrderRepository;
import ftn.isa.team12.pharmacy.repository.OfferRepository;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.service.*;
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
public class DrugOrderServiceImpl implements DrugOrderService {

    @Autowired
    private DrugOrderRepository drugOrderRepository;

    @Autowired
    private DrugService drugService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PharmacyAdministratorService pharmacyAdministratorService;

    @Autowired
    private OfferService offerService;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Override
    public DrugOrder createDrugOrder(DrugOrderDTO drugOrder) {

        if(drugOrder.getDeadline().before(new Date())){
            throw new IllegalArgumentException("Bad input date");
        }
        DrugOrder order = new DrugOrder();
        order.setDrugOrderStatus(DrugOrderStatus.waitingForOffers);
        order.setDeadline(drugOrder.getDeadline());
        PharmacyAdministrator pharmacyAdministrator = pharmacyAdministratorService.findAdminByEmail(drugOrder.getPharmacyAdminEmail());
        order.setPharmacyAdministrator(pharmacyAdministrator);
        order.setPharmacy(pharmacyAdministrator.getPharmacy());
        Pharmacy pharmacy = pharmacyAdministrator.getPharmacy();

        for(DrugForOrderDTO orderDto : drugOrder.getDrugOrderItems()){
            DrugOrderItem item = new DrugOrderItem();
            Drug drug = drugService.findById(orderDto.getId());
            if(this.checkIfDrugExistInPharmacy(pharmacy,drug)){
                DrugInPharmacy drugInPharmacy = new DrugInPharmacy();
                drugInPharmacy.setDrug(drug);
                drugInPharmacy.setPharmacy(pharmacy);
                drugInPharmacy.setQuantity(0);
                pharmacy.getDrugs().add(drugInPharmacy);
            }
            item.setDrug(drug);
            item.setQuantity(orderDto.getQuantity());
            item.setDrugOrder(order);
            order.getDrugOrderItems().add(item);
        }

        pharmacy = pharmacyService.saveDrugInPharmacy(pharmacy);
        order.setPharmacy(pharmacy);
        order = drugOrderRepository.save(order);
        return order;
    }

    @Override
    public List<DrugOrder> findAll() {
        return this.drugOrderRepository.findAll();
    }

    @Override
    public DrugOrder findById(UUID id) {
        return this.drugOrderRepository.findById(id).get();
    }

    @Override
    public List<DrugOrder> findAllForSupplier(String email) {
        List<DrugOrder> drugOrders = this.drugOrderRepository.findAll();
        Supplier supplier = this.supplierService.findByEmail(email);
        if (supplier == null ) {
            throw new IllegalArgumentException("Supplier doesn't exists with email!");
        }
        List<Offer> offers = this.offerService.getOfferByIdSupplier(supplier.getUserId());
        for (Offer offer: offers) {
            if (drugOrders.contains(offer.getDrugOrder())) {
                drugOrders.remove(offer.getDrugOrder());
            }
        }
        return drugOrders;
    }

    boolean checkIfDrugExistInPharmacy(Pharmacy pharmacy, Drug drug){
        for (DrugInPharmacy drugInPharmacy : pharmacy.getDrugs())
            if(drugInPharmacy.getDrug().getDrugId() == drug.getDrugId())
                return false;
        return true;
    }


    @Override
    public List<DrugOrderPhAdminDTO> findAllByPharmacyID() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        List<DrugOrderPhAdminDTO> dpPh = new ArrayList<>();
        List<DrugOrder> drugOrders = drugOrderRepository.findALlByPharmacyId(pharmacyAdministrator.getPharmacy().getId());
        if(drugOrders != null){
            for(DrugOrder drugOrder: drugOrders){
                dpPh.add(new DrugOrderPhAdminDTO(drugOrder));
            }
        }
        return dpPh;
    }


    @Override
    public boolean delete(String id) {
        DrugOrder drugOrder = drugOrderRepository.findByOrderId(UUID.fromString(id));
        List<Offer> offers = offerRepository.findAllByDrugOrderOrderId(drugOrder.getOrderId());
        if(!offers.isEmpty())
            throw new IllegalArgumentException("can't delete drug order with code: " + id);
        drugOrderRepository.deleteById(drugOrder.getOrderId());
        return true;
    }


    @Override
    public DrugOrder changeDrugOrder(DrugOrderPhAdminDTO dto) {
        DrugOrder drugOrder = drugOrderRepository.findByOrderId(dto.getOrderId());

        if(drugOrder == null)
            throw new IllegalArgumentException("No drug order with code: " + dto.getOrderId());

        List<Offer> offers = offerRepository.findAllByDrugOrderOrderId(drugOrder.getOrderId());
        if(!offers.isEmpty())
            throw new IllegalArgumentException("Drug order have offer, can't be change");

        for (DrugForOrderDTO d: dto.getDrugorderItem()){
            if(d.getQuantity()<0)
                throw new IllegalArgumentException("Bad input quantity ");

            for (DrugOrderItem dopi: drugOrder.getDrugOrderItems()){
                if(dopi.getDrug().getDrugId().toString().equals(d.getId().toString())){
                    
                    dopi.setQuantity(d.getQuantity());
                }
            }
        }
        drugOrder = drugOrderRepository.save(drugOrder);
        return drugOrder;
    }
}
