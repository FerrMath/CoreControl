package com.matheus.CoreControl.util;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import com.matheus.CoreControl.model.reportEntrys.ReportEntry;
import com.opencsv.CSVWriter;

public class FileGenerator {
    File file;

    public String csvWritter(List<ReportEntry> data) {
        if (data == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        try (CSVWriter writer = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            String[] header = { "Id", "Type", "Product Id", "User Id", "Date", "Time" };
            writer.writeNext(header);
            for (ReportEntry object : data) {
                String[] line = object.getInfo();
                writer.writeNext(line);
            }

            String csvContent = stringWriter.toString();
            return csvContent;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
