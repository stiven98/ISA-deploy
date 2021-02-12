package ftn.isa.team12.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class QrCodeItem {
    private String name;
    private int quantity;

    public QrCodeItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
