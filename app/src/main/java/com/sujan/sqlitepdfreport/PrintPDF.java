package com.sujan.sqlitepdfreport;


import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;


public class PrintPDF {
    //declare objects
    int tripNo;
    String customerName;
    String number;
    long tripdate;
    String item1;
    int itemQty1;
    int itemamount1;
    String item2;
    int itemQty2;
    int itemamount2;


    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public PrintPDF(int tripNo, String customerName, String number, long tripdate, String item1, int itemQty1, int itemamount1, String item2, int itemQty2, int itemamount2) {
        this.tripNo = tripNo;
        this.customerName = customerName;
        this.number = number;
        this.tripdate = tripdate;
        this.item1 = item1;
        this.itemQty1 = itemQty1;
        this.itemamount1 = itemamount1;
        this.item2 = item2;
        this.itemQty2 = itemQty2;
        this.itemamount2 = itemamount2;
    }

    //to create pdf
    public  void getPDF() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"myPdf.pdf");
        OutputStream outputStream= new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document= new Document(pdfDocument);

        float columnwidth[] = {120,220,120,300};
        Table table = new Table(columnwidth);

        table.addCell(new Cell().add(new Paragraph("Customer Name").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(customerName).setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Trip No").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(tripNo+"").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("P Number").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(number).setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Date").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(dateFormat.format(tripdate)).setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("").setFontSize(14)).setBorder(Border.NO_BORDER));

        float columnwidth2[] = {360,100,100};
        Table table2 = new Table(columnwidth2);

        table2.addCell(new Cell().add(new Paragraph("Vehicle Description")));
        table2.addCell(new Cell().add(new Paragraph("Quantity")));
        table2.addCell(new Cell().add(new Paragraph("Amount")));

        table2.addCell(new Cell().add(new Paragraph(item1)));
        table2.addCell(new Cell().add(new Paragraph(itemQty1+"")));
        table2.addCell(new Cell().add(new Paragraph(itemamount1+"")));

        table2.addCell(new Cell().add(new Paragraph(item2)));
        table2.addCell(new Cell().add(new Paragraph(itemQty2+"")));
        table2.addCell(new Cell().add(new Paragraph(itemamount2+"")));

        table2.addCell(new Cell(1,2).add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(itemamount1+itemamount2))));

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("___________________________________________________________________"));
        document.add(table2);
        document.close();


    }
}
