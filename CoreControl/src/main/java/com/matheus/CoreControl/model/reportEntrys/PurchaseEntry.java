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
public class PurchaseEntry extends ReportEntry {
    private Double price;
    private Double quantity;
    private Double total;

    public PurchaseEntry() {
        this.setType(EntryType.PURCHASE);
    }

    public PurchaseEntry(Long productId, Long userId, Double price, Double quantity) {
        super(productId, userId);
        this.setType(EntryType.PURCHASE);
        this.price = price;
        this.quantity = quantity;
        this.total = price * quantity;
    }
}
