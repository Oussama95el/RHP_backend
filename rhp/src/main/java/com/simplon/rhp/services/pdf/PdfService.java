package com.simplon.rhp.services.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.simplon.rhp.entities.Employee;
import com.simplon.rhp.entities.PaySlip;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.services.PaySlipService;
import com.simplon.rhp.user.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class PdfService {


    private final PaySlipService paySlipService;

    public void generatePaySlipPdf(HttpServletResponse response , Long id) throws Exception {

        PaySlip paySlip = paySlipService.getPaySlipById(id);
        assert paySlip != null;
        Employee employee = paySlip.getEmployee();
        Profile profile = employee.getProfile();
        User user = employee.getUser();
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
            document.addTitle("PAY SLIP FOR " + user.getFirstname().toUpperCase());

            // Add the body section to the document
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            table.addCell(new PdfPCell(new Phrase("Employee Name:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase(user.getFirstname()+ " " + user.getLastname(), bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Employee ID:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase(profile.getMatricule(), bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Net Salary:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( profile.getNetSalary()+" MAD" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Gross Salary:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( paySlip.getGrossSalary()+"MAD" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Tax:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( paySlip.getTaxRate() * 100 +"%" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Tax Amount:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( paySlip.getTaxAmount()+" MAD" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Benefits:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( paySlip.getBenefits()+" MAD" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Deductions:", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( paySlip.getDeductions()+" MAD" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Overtime Hours", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( paySlip.getOvertimeHours()+" H" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Total HT", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( calculateTaxFreeTotal(paySlip)+" MAD" , bodyFont)));

            table.addCell(new PdfPCell(new Phrase("Total TTC", bodyFont)));
            table.addCell(new PdfPCell(new Phrase( calculateTotalAmount(paySlip)+" MAD" , bodyFont)));

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
//        document.close()
    }


    // calculate the total amount of the pay slip
    public double calculateTotalAmount(PaySlip paySlip){
        return paySlip.getGrossSalary() + paySlip.getBenefits() - paySlip.getDeductions() - paySlip.getTaxAmount();
    }

    //calculate the total amount tax free
    public double calculateTaxFreeTotal(PaySlip paySlip){
        return paySlip.getGrossSalary() + paySlip.getBenefits() - paySlip.getDeductions();
    }

}
