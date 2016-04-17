/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Customer;
import invoiceGenerator.Invoice;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author XUAN
 */
@Named(value = "invoiceManagedBean")
@SessionScoped
public class InvoiceManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Customer customer;
    String name;
    String add1;
    String add2;
    String city;
    String country;
    String postcode;
    String tel;
    String gstRegNo;
    String invoiceNo;
    Date invoiceDate;
    String pageNo;
    String customerNo;
    String orderNo;
    String paymentMethod;
    Date orderDate;
    String status;
    ArrayList<Record> records;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    float subtotal;
    float gst;
    float total;
    
    public InvoiceManagedBean() {
        customer = new Customer();
    }
    
    public void generate() throws IOException, ParseException {
        System.out.println("YO");
        generateContent();
        System.out.println("YOYo");
        generateInvoice("/Users/XUAN/NetBeansProjects/laundrybutler/laundrybutler/LaundryButler/LaundryButler-war/web/resources/temp/newInvoice.pdf");
    }
    
    public void generateContent()throws ParseException {
        customer = accountManagementRemote.getCustomer();
        this.name = customer.getFirstName() + " " + customer.getLastName();
        this.add1 = "13 Computing Dr";
        this.add2 = "School of Computing, NUS";
        this.city = "Singapore";
        this.country = "Singapore";
        this.postcode = "117417";
        this.tel = "+65-612345";
        SecureRandom random = new SecureRandom();
        this.gstRegNo = "" + new BigInteger(130, random).toString(6) + "/" + new Date().getYear();
        this.invoiceNo = new BigInteger(130, random).toString(10);
        this.invoiceDate = (Date) sdf.parse("21-MAY-2016");
        this.pageNo = "1";
        this.customerNo = customer.getCustomerId().toString();
        this.orderNo = "67889933";
        this.paymentMethod = "Credit Card";
        this.orderDate = (Date) sdf.parse("20-MAY-2016");
        this.status = "SUCCESSFUL";
        this.records = new ArrayList<Record>();
        this.records.add(new Record("F-11-34", "Premium Laundry Service", 1, (float) 29.99));
        this.records.add(new Record("W-23-44", "Express Laundry Weekend", 1, (float) 39.99));
    }
    
    public class Record {
        
        String item;
        String description;
        float qty;
        float up;
        float ep;
        
        public Record() {
            this.item = "";
            this.description = "";
            this.qty = 0;
            this.up = 0;
            this.ep = 0;
        }
        
        public Record(String item, String description, float qty, float up) {
            this.item = item;
            this.description = description;
            this.qty = qty;
            this.up = up;
            this.ep = qty * up;
            
        }
    }
    public void generateInvoice(String exportFile) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDFont font = PDType1Font.COURIER;
        PDPageContentStream cs = new PDPageContentStream(document, page);
        String templatePath = "/Users/XUAN/NetBeansProjects/laundrybutler/laundrybutler/LaundryButler/LaundryButler-war/invoice_template.jpg";
        try {
            PDImageXObject bg = PDImageXObject.createFromFile(templatePath, document);
            cs.drawImage(bg, 0, 0, 595, 842);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        //Sold-To
        ArrayList<String> soldTo = new ArrayList<String>();
        soldTo.add(name);
        soldTo.add(add1);
        soldTo.add(add2);
        soldTo.add(city + " " + postcode);
        soldTo.add(country);
        insert(cs, 52, 696, 30, soldTo);
        
        //Invoice Number
        ArrayList<String> rightUp = new ArrayList<String>();
        rightUp.add(gstRegNo);
        rightUp.add(invoiceNo);
        rightUp.add(sdf.format(invoiceDate));
        rightUp.add(pageNo);
        insert(cs, 420, 674, 15, rightUp);
        
        //Order Titile
        insert(cs, 58, 568, 15, customerNo);
        insert(cs, 181, 568, 15, orderNo);
        insert(cs, 304, 568, 30, paymentMethod);
        
        insert(cs, 58, 530, 15, sdf.format(orderDate));
        insert(cs, 181, 530, 45, status);
        
        //Order item
        int i = 0;
        for (Record current : records){
            int inc = 0;
            insert(cs, 58, 450 - 12 * i, 15, current.item);
            inc += insert(cs, 181, 450 - 12 * i, 20, current.description);
            insert(cs, 352, 450 - 12 * i, 6, "" + current.qty);
            insert(cs, 396, 450 - 12 * i, 10, "" + String.format("%.2f", current.up));
            insert(cs, 474, 450 - 12 * i, 10, "" + String.format("%.2f", current.ep));
            i += inc + 2;
        }
        
        //total
        float subtotal = sum();
        float gst = (float) (0.07 * subtotal);
        float total = subtotal + gst;
        insert(cs, 474, 210, 6, "" + String.format("%.2f", subtotal));
        insert(cs, 474, 196, 6, "" + String.format("%.2f", gst));
        insert(cs, 474, 156, 6, "" + String.format("%.2f", total));
        
        cs.close();
        
        document.save(exportFile);
        document.close();
        
        System.out.println("finished!");
    }
    public float sum (){
        float result = 0;
        for(Record record : records){
            result += record.ep;
        }
        return result;
    }
    public String space(int number) {
        String result = "";
        for (int i = 0; i < number; i++) {
            result += " ";
        }
        return result;
    }
    
    public int insert(PDPageContentStream cs, float x, float y, int width, String input) throws IOException {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(input);
        return insert(cs, x, y, width, temp);
    }
    
    public int insert(PDPageContentStream cs, float x, float y, int width, ArrayList<String> inputs) throws IOException {
        int lines = 1;
        cs.moveTo(0, 0);
        cs.beginText();
        cs.setFont(PDType1Font.COURIER, 12);
        cs.setLeading(14);
        cs.newLineAtOffset(x, y);
        for (String input : inputs) {
            int w = width;
            boolean ini = true;
            while (input.length() > w) {
                if(ini){
                    cs.showText(input.substring(0, width));
                }else{
                    cs.showText("  " + input.substring(0, w));
                }
                input = input.substring(w);
                lines++;
                cs.newLine();
                w = width - 2;
                ini = false;
            }
            if(ini){
                cs.showText(input);
            }else{
                cs.showText("  "+ input);
            }
            cs.newLine();
        }
        cs.endText();
        return lines;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
