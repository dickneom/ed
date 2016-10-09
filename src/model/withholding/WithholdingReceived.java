/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.withholding;

import java.util.Date;

/**
 *
 * @author DickNeoM
 */
public class WithholdingReceived {
    private int id;
    private String serie;
    private String number;
    private int idInvoiceType;
    private int idInvoice;
    private Date date;
    private double value;
    private boolean canceled;
    private String observations;

    public WithholdingReceived() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getIdInvoiceType() {
        return idInvoiceType;
    }

    public void setIdInvoiceType(int idinvoicetype) {
        this.idInvoiceType = idinvoicetype;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idinvoice) {
        this.idInvoice = idinvoice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
    
    
    
    public String getFullNumber() {
        return serie + "-" + number;
    }
    
}
