package com.matheus.CoreControl.model.reportEntrys;

import com.matheus.CoreControl.model.enums.EntryType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class SaleEntry extends ReportEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public String[] getInfo() {
        return new String[] { getId().toString(), getType().toString(), getProductId().toString(),
                getUserId().toString(), getDate().toString(), getTime().toString() };
    }
}
