package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugSearcDTO;
import ftn.isa.team12.pharmacy.service.PharmacyAdministratorService;
import ftn.isa.team12.pharmacy.service.SearchDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = false)
public class SearchDrugServiceImpl implements SearchDrugService {

    @Autowired
    private PharmacyAdministratorService pharmacyAdministratorService;

    private Set<DrugInPharmacy> searchList = new HashSet<>();

    @Override
    public List<DrugForOrderDTO> searchDrug(DrugSearcDTO dto) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(dto.getPhAdminEmail());
        Set<DrugInPharmacy> dip = phAdmin.getPharmacy().getDrugs();

        this.searchList = searchDrugByName(dto.getName(), dip);
        this.searchList = searchDrugByCode(dto.getCode());
        this.searchList = searchDrugByTypeOfDrug(dto.getTypeOfDrug());
        this.searchList = searchDrugByFormOfDrug(dto.getFormOfDrug());
        this.searchList = searchDrugByIssuanceRegime(dto.getIssuanceRegime());
        this.searchList = searchDrugByManufactureName(dto.getManufactureName());
        this.searchList = searchDrugByQuantity(dto.getQuantity());

        return prepareForSend();
    }



    @Override
    public Set<DrugInPharmacy> searchDrugByName(String name, Set<DrugInPharmacy> dip) {
        if(name.equals("")) return dip;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: dip)
            if(drugInPharmacy.getDrug().getName().equals(name))
                list.add(drugInPharmacy);
        return list;
    }

    @Override
    public Set<DrugInPharmacy> searchDrugByCode(String code) {
        if(code.equals("")) return this.searchList;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: this.searchList)
            if(drugInPharmacy.getDrug().getCode().equals(code))
                list.add(drugInPharmacy);
        return list;
    }

    @Override
    public Set<DrugInPharmacy> searchDrugByTypeOfDrug(String typeOfDrug) {
        if(typeOfDrug.equals("")) return this.searchList;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: this.searchList)
            if(drugInPharmacy.getDrug().getTypeOfDrug().toString().equals(typeOfDrug) )
                list.add(drugInPharmacy);
        return list;
    }

    @Override
    public Set<DrugInPharmacy> searchDrugByFormOfDrug(String formOfDrug) {
        if(formOfDrug.equals("")) return this.searchList;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: this.searchList)
            if(drugInPharmacy.getDrug().getFormOfDrug().toString().equals(formOfDrug))
                list.add(drugInPharmacy);
        return list;
    }

    @Override
    public Set<DrugInPharmacy> searchDrugByIssuanceRegime(String issuanceRegime) {
        if(issuanceRegime.equals("")) return this.searchList;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: this.searchList)
            if(drugInPharmacy.getDrug().getIssuanceRegime().toString().equals(issuanceRegime))
                list.add(drugInPharmacy);
        return list;
    }

    @Override
    public Set<DrugInPharmacy> searchDrugByManufactureName(String manufactureName) {
        if(manufactureName.equals("")) return this.searchList;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: this.searchList)
            if(drugInPharmacy.getDrug().getManufacturer().equals(manufactureName))
                list.add(drugInPharmacy);
        return list;
    }

    @Override
    public Set<DrugInPharmacy> searchDrugByQuantity(int quantity) {
        if(quantity == 0) return this.searchList;
        Set<DrugInPharmacy> list = new HashSet<>();
        for (DrugInPharmacy drugInPharmacy: this.searchList)
            if(drugInPharmacy.getQuantity() >= quantity)
                list.add(drugInPharmacy);
        return list;
    }


    @Override
    public List<DrugForOrderDTO> prepareForSend() {
        List<DrugForOrderDTO> list = new ArrayList<>();
        if(this.searchList.isEmpty())
            return null;
        for (DrugInPharmacy dip: this.searchList)
            list.add(new DrugForOrderDTO(dip.getDrug(),dip.getQuantity()));


        return list;
    }
}
