package com.matheus.CoreControl.controllers.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.reportEntrys.PurchaseEntry;
import com.matheus.CoreControl.model.reportEntrys.SaleEntry;
import com.matheus.CoreControl.service.ReportService;
import com.matheus.CoreControl.util.FileGenerator;

@WebMvcTest(DownloadController.class)
public class DownloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @MockBean
    private FileGenerator fileGenerator;

    private Report mockReport;

    @BeforeEach
    public void setUp() {
        mockReport = new Report();

        SaleEntry saleEntry = new SaleEntry();
        saleEntry.setId(1L);
        saleEntry.setProductId(1L);
        saleEntry.setUserId(1L);
        saleEntry.setDate(LocalDate.now());
        saleEntry.setTime(LocalTime.now());

        PurchaseEntry purchaseEntry = new PurchaseEntry();
        purchaseEntry.setId(2L);
        purchaseEntry.setProductId(2L);
        purchaseEntry.setUserId(2L);
        purchaseEntry.setDate(LocalDate.now());
        purchaseEntry.setTime(LocalTime.now());

        mockReport.setEntries(Arrays.asList(saleEntry, purchaseEntry));
    }

    @Test
    public void testDownloadSuccess() throws Exception {
        Mockito.when(reportService.getReportById(1L)).thenReturn(mockReport);
        String csvContent = "Id,Type,Product Id,User Id,Date,Time\n" +
                "1,SALE,1,1," + LocalDate.now() + "," + LocalTime.now() + "\n" +
                "2,PURCHASE,2,2," + LocalDate.now() + "," + LocalTime.now() + "\n";
        Mockito.when(fileGenerator.csvWritter(Mockito.anyList())).thenReturn(csvContent);

        mockMvc.perform(get("/api/download/1"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.csv"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8"))
                .andExpect(
                        content().string(org.hamcrest.Matchers.matchesPattern("Id,Type,Product Id,User Id,Date,Time\n" +
                                "1,SALE,1,1,\\d{4}-\\d{2}-\\d{2},\\d{2}:\\d{2}:\\d{2}.\\d+\n" +
                                "2,PURCHASE,2,2,\\d{4}-\\d{2}-\\d{2},\\d{2}:\\d{2}:\\d{2}.\\d+\n")));
    }

    @Test
    public void testDownloadFailure() throws Exception {
        Mockito.when(reportService.getReportById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/download/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDownloadFileGenerationFailure() throws Exception {
        mockReport.setEntries(null);
        Mockito.when(reportService.getReportById(1L)).thenReturn(mockReport);
        Mockito.when(fileGenerator.csvWritter(Mockito.anyList())).thenReturn(null);

        mockMvc.perform(get("/api/download/1"))
                .andExpect(status().isInternalServerError());
    }
}
