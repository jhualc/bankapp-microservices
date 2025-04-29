package com.microservices.bankapp.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.microservices.bankapp.entity.Transaction;
import com.microservices.bankapp.service.ReportService;
import com.microservices.bankapp.service.TransactionService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final TransactionService transactionService;

    public ReportServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Override
    public byte[] generateReportPdf(Long customerId, LocalDate startDate, LocalDate endDate) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Título
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Estado de Cuenta", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            List<Transaction> transactions = transactionService.getTransactionsByCustomerIdAndDateRange(customerId, startDate, endDate);
            String customerName = transactions.isEmpty() ? "Desconocido" :
                    transactions.get(0).getAccount().getCustomer().getName();

            document.add(new Paragraph("Cliente: " + customerName + " (ID: " + customerId + ")"));
            document.add(new Paragraph("Desde: " + startDate + " Hasta: " + endDate));
            document.add(new Paragraph(" ")); // Espacio

            // Tabla
            PdfPTable table = new PdfPTable(7); // 7 columnas
            table.setWidthPercentage(100);

            addTableHeader(table, "Fecha", "Cuenta", "Tipo", "Saldo Inicial", "Estado", "Movimiento", "Saldo Disponible");

            for (Transaction transaction : transactions) {
                table.addCell(transaction.getDate().toString());
                table.addCell(transaction.getAccount().getAccountNumber());
                table.addCell(transaction.getAccount().getAccountType().name());
                table.addCell(transaction.getInitialBalance().toString()); // ¡correcto aquí!
                table.addCell(transaction.getAccount().getStatus() ? "Activo" : "Inactivo");
                table.addCell(transaction.getTransactionType().name() + " " + transaction.getAmount());
                table.addCell(transaction.getAvailableBalance().toString());
            }

            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header));
            table.addCell(headerCell);
        }
    }
}
