/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.invoice;

import java.util.Date;

/**
 *
 * @author richneom
 */
public class Invoice {
    
    private int id;
    private String serie;
    private int number;
    private Date date;
    private int idContract;
//    private int idClient;
//    private double sum;
//    private double discount;
//    private double taxes;
    private String observations;
    private boolean canceled; 
    private double tax; // valor del impuesto en porcentaje
    private int idType; // tipo de factura (nota de credito, factura u otro)
    
    private InvoiceDetails details;

    public static final String[] FIELDS = {"id", "serie", "number", "date", "idcontract", "observations", "canceled", "tax", "idinvoicestype"};
    
    public Invoice() {
        this.id = -1;
        this.canceled = false;
        details = new InvoiceDetails();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

//    public int getIdClient() {
//        return idClient;
//    }
//
//    public void setIdClient(int idClient) {
//        this.idClient = idClient;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public double getSum() {
//        return sum;
//    }
//
//    public void setSum(double sum) {
//        this.sum = sum;
//    }
//
//    public double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(double discount) {
//        this.discount = discount;
//    }
//
//    public double getTaxes() {
//        return taxes;
//    }
//
//    public void setTaxes(double taxes) {
//        this.taxes = taxes;
//    }

    public String getObservations() {
        return observations;
    }

    public void setDetails(InvoiceDetails details) {
        this.details = details;
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getIdType() {
        return idType;
    }
    
    public void setIdType(int idType) {
        this.idType = idType;
    }

    
    
    
    
    
    
    public String getFullNumber(int tam) {
        return serie + "-" + getNumberStr(tam);
    }
    
    public String getNumberStr(int tam) {
        String numberStr = String.valueOf(number);
        
        while (numberStr.length() < tam) {
            numberStr = "0" + numberStr;
        }
        
        return numberStr;
    }
    
    
    
    
    public InvoiceDetails getDetails() {
        return details;
    }
    
    /**
     * Inserta un nuevo registro
     * @param detail 
     */
    public void addDetail(InvoiceDetail detail) {
        details.add(detail);
    }
    
    public void setDetail(InvoiceDetail detail) {
        details.set(detail.getId(), detail);
    }
    
    public void deleteDetail(int id) {
        details.remove(id);
    }
    
    public InvoiceDetail getDetail(int index) {
        return details.get(index);
    }
    
    
    
    
    
    
    
    
    
    public Object[] toArray() {
        Object[] array = new Object[FIELDS.length];
        
        array[0] = getId();
        array[1] = getSerie();
        array[2] = getNumber();
        array[3] = getDate();
        array[4] = getIdContract();
        array[5] = getObservations();
        array[6] = (isCanceled()? 1 : 0);
        array[7] = getTax();
        array[8] = getIdType();
        
        return array;
    }
    
    
}
