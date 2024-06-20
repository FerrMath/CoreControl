package com.matheus.CoreControl.util;

import com.matheus.CoreControl.model.reportEntrys.ReportEntry;
import java.util.List;

public class FileGenerator {
    public String csvWritter(List<ReportEntry> entries) {
        // Generate CSV content here
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Id,Type,Product Id,User Id,Date,Time\n");

        for (ReportEntry entry : entries) {
            csvBuilder.append(entry.getId()).append(",")
                    .append(entry.getType()).append(",")
                    .append(entry.getProductId()).append(",")
                    .append(entry.getUserId()).append(",")
                    .append(entry.getDate()).append(",")
                    .append(entry.getTime()).append("\n");
        }

        return csvBuilder.toString();
    }
}
