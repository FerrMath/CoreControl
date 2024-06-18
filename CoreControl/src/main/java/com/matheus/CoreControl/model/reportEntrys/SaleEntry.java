package com.matheus.CoreControl.model.reportEntrys;

import com.matheus.CoreControl.model.enums.EntryType;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class SaleEntry extends ReportEntry {
    private Long clientId;
    private Double price;
    private Double quantity;
    private Double total;

    public SaleEntry() {
        this.setType(EntryType.SALE);
    }

    public SaleEntry(Long productId, Long userId, Long clientId, Double price, Double quantity) {
        super(productId, userId);
        this.setType(EntryType.SALE);
        this.clientId = clientId;
        this.price = price;
        this.quantity = quantity;
        this.total = price * quantity;
    }
}
