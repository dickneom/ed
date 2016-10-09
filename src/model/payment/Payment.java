/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.payment;

import java.util.Date;

/**
 *
 * @author DickNeoM
 */
public class Payment {
    private int id;
    private String code;
    private Date date;
    private int idType; // Id de lo que se paga
    private int idInvoice; // Id de la factura
    private int idPaymentMethod; // id de la forma de pago
    private int idPaymentDoc; // id del documento de pago
    private double value;
    private String observations;

    public Payment() {
        this.id = -1;
        this.code = "";
        this.date = null;
        this.idType = -1;
        this.idInvoice = -1;
        this.idPaymentMethod = -1;
        this.idPaymentDoc = -1;
        this.value = 0;
        this.observations = "";
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

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(int idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public int getIdPaymentDoc() {
        return idPaymentDoc;
    }

    public void setIdPaymentDoc(int idPaymentDoc) {
        this.idPaymentDoc = idPaymentDoc;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
    
    
    
}
