package com.matheus.CoreControl.model.reportEntrys;

import com.matheus.CoreControl.model.enums.EditType;
import com.matheus.CoreControl.model.enums.EntryType;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class EditEntry extends ReportEntry {
    private EditType editType;

    public EditEntry() {
        this.setType(EntryType.EDIT);
    }

    public EditEntry(EditType editType, Long productId, Long userId) {
        super(productId, userId);
        this.setType(EntryType.EDIT);
        this.editType = editType;
    }
}