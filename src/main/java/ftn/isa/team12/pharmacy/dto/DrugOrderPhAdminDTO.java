package ftn.isa.team12.pharmacy.dto;

import ftn.isa.team12.pharmacy.domain.drugs.DrugOrder;
import ftn.isa.team12.pharmacy.domain.drugs.DrugOrderItem;
import ftn.isa.team12.pharmacy.domain.enums.DrugOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DrugOrderPhAdminDTO {
    private UUID orderId;
    private List<DrugForOrderDTO> drugorderItem = new ArrayList<>();
    private Date deadline;
    private DrugOrderStatus drugOrderStatus;
    private String phAdminEmail;


    public DrugOrderPhAdminDTO(DrugOrder drugOrder){
        this.orderId = drugOrder.getOrderId();
        this.deadline = drugOrder.getDeadline();
        this.drugOrderStatus = drugOrder.getDrugOrderStatus();
        this. phAdminEmail = drugOrder.getPharmacyAdministrator().getLoginInfo().getEmail();

        for(DrugOrderItem d: drugOrder.getDrugOrderItems()){
            this.drugorderItem.add(new DrugForOrderDTO(d.getDrug(),d.getQuantity()));
        }
    }


    @Override
    public String toString() {
        return "DrugOrderPhAdminDTO{" +
                "orderId=" + orderId +
                ", drugorderItem=" + drugorderItem +
                ", deadline=" + deadline +
                ", drugOrderStatus=" + drugOrderStatus +
                ", phAdminEmail='" + phAdminEmail + '\'' +
                '}';
    }
}
