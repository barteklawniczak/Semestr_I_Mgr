package com.blawniczak.util;

import com.blawniczak.domain.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PDFViewer extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Ticket ticket = (Ticket) model.get("ticket");

        doc.add(new Paragraph("Your bill for ticket with id=" + ticket.getId()));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {1.6f, 1.8f, 1.8f, 1.8f, 1.0f, 1.0f});
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(6);

        // write table header
        cell.setPhrase(new Phrase("Ticket type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Purchase date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Start date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("End date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Half price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Duration", font));
        table.addCell(cell);

        table.addCell(ticket.getTicketType().getDescription());
        table.addCell(ticket.getPurchaseDate().toString());
        table.addCell(ticket.getStartDate().toString());
        table.addCell(ticket.getEndDate().toString());
        if(ticket.isHalfPrice())
            table.addCell("true");
        else
            table.addCell("false");
        table.addCell(ticket.getTicketType().getDuration().toString() + " days");

        doc.add(table);

        doc.add(new Paragraph("You have to pay " + ticket.getPrice() + " zlotych"));

    }
}
