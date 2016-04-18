/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package invoiceGenerator;

import java.io.IOException;
import java.text.ParseException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
/**
 *
 * @author lixinsun
 */
public class PDFTest1 {
    
    /**
     * @param args the command line arguments
     */
    public void gogo() throws IOException, ParseException {
        // TODO code application logic here
//    Random r = new Random();
//    System.out.println(r.nextInt());
//    PDFTest1 t = new PDFTest1();
        Invoice invoice = new Invoice();
        System.out.println("YO manman");
        invoice.hardcodeTest();
        System.out.println("YO man");
        invoice.generateInvoice("/Users/XUAN/NetBeansProjects/laundrybutler/laundrybutler/LaundryButler/LaundryButler-war/web/resources/temp/newInvoice.pdf");
        //t.generateInvoice();
    }

    public void generateInvoice() throws IOException{
        System.out.println("hello world");
        
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDFont font = PDType1Font.COURIER;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        String templatePath = "/Users/XUAN/NetBeansProjects/laundrybutler/laundrybutler/LaundryButler/LaundryButler-war/invoice_template.png";
        try {
            PDImageXObject bg = PDImageXObject.createFromFile(templatePath, document);
            contentStream.drawImage(bg, 0, 0, 595, 842);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.setLeading(16);
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Hello world!");
        contentStream.newLine();
        contentStream.showText("This is the first pdf generated from this Laundry Butler.");
        contentStream.newLine();
        contentStream.endText();
        
// Make sure that the content stream is closed:
        contentStream.close();
//    }
        
// Save the results and ensure that the document is properly closed:
        document.save("Hello World.pdf");
        document.close();
        
        System.out.println("finished!");
    }
    
    public double mmToPt(double mm){
        return 2.83464 * mm;
    }
    
}
