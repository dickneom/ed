/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.contract;

import java.util.Date;

/**
 *
 * @author DickNeoM
 */
public class Contract {
    
    private int id;
    private String code;
    private Date date;
    private int idCustomer;
    private int idLocal;
    private Date dateInit; // Fecha desde la que rige el contrato
    private Date dateFinal; // Fecha de terminación del contrato cuando se firma
    private double rent;
    private double discount;
    private int discountDays;
    private double warranty;
    private boolean backWarranty;
    private int idPayMethodWarranty;
    private Date dateFinish; // Fecha de cuando el contrato a terminado, cuando se devuelve la oficina
    private String purpose;
    private String notes;
    private boolean active;
    
    public static final String[] FIELDS = {"id", "code", "date", "idcustomer", "idlocal", "initdate", "finaldate", "rent", "discount",
        "discountdays", "warranty", "backWarranty", "idpaymethodwarranty", "finishdate", "purpose", "notes", "active"};
    
    public Contract() {
        this.id = -1;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
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

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(int discountDays) {
        this.discountDays = discountDays;
    }

    public double getWarranty() {
        return warranty;
    }

    public void setWarranty(double warranty) {
        this.warranty = warranty;
    }

    public boolean isBackWarranty() {
        return backWarranty;
    }

    public void setBackWarranty(boolean backWarranty) {
        this.backWarranty = backWarranty;
    }

    public int getIdPayMethodWarranty() {
        return idPayMethodWarranty;
    }

    public void setIdPayMethodWarranty(int idPayMethodWarranty) {
        this.idPayMethodWarranty = idPayMethodWarranty;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNote(String notes) {
        this.notes = notes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        String str;
        
        str = "Contrato: Código: " + code;
        
        return str;
    }
    
    public String toText() {
        String str;
        
        str = "Contrato:";
        str += " Código: " + code;
        
        return str;
    }
}
