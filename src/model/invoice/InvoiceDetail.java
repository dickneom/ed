/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.invoice;

import java.util.Date;

/**
 *
 * @author DickNeoM
 */
public class InvoiceDetail {
    private int id;
    private int idInvoice;
    private double quantity;
    private int idItem;
    private String description;
    private double price;
    private double discount;
    private double taxes;
    private int idLocal;
    private int month;
    private int year;
    private Date dateInit;
    private Date dateFinal;
    
    private boolean deleted;

    public static final String[] FIELDS_DETAIL = new String[]{"id", "idinvoice", "quantity", "idItem", "idLocal", "month", "year", "dateinit", "datefinal", "price", "discount", "tax", "description"};;
    
    public InvoiceDetail() {
        this.id = -1;
        this.deleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
 
    
    
    public Object[] toArray() {
        Object[] array = new Object[FIELDS_DETAIL.length];
        
        array[0] = getId();
        array[1] = getIdInvoice();
        array[2] = getQuantity();
        array[3] = getIdItem();
        array[4] = getIdLocal();
        array[5] = getMonth();
        array[6] = getYear();
        array[7] = getDateInit();
        array[8] = getDateFinal();
        array[9] = getPrice();
        array[10] = getDiscount();
        array[11] = getTaxes();
        array[12] = getDescription();
        
        return array;
    }
    
    
}
