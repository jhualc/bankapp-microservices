package com.microservices.bankapp.service;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    byte[] generateReportPdf(Long customerId, LocalDate startDate, LocalDate endDate);
}
