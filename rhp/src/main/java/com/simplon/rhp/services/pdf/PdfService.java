package com.simplon.rhp.services.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {


    public void generatePaySlipPdf(HttpServletResponse response) throws Exception {

        try {
            // Create a new PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            // Set the page size and margins for the document
            document.setPageSize(PageSize.A4);
            document.setMargins(36, 36, 36, 36);

            // Create a new font for the header and body text
            Font headerFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font bodyFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            // Add the header section to the document
            Paragraph header = new Paragraph("PAY SLIP", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.addTitle("PAY SLIP");

            // Add the body section to the document
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            table.addCell(new PdfPCell(new Phrase("Employee Name:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase("John Doe", bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Employee ID:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase("12345", bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Salary:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase("$5000", bodyFont)));

            document.add(table);

            // Add the footer section to the document
            Paragraph footer = new Paragraph("Thank you for your hard work!", bodyFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);


            document.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Save the PDF document as a byte array
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PdfWriter.getInstance(document, outputStream);
//        document.open();
//        document.close();

    }
}
