/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bank;

import java.util.Date;

/**
 *
 * @author DickNeoM
 */
public class BankTrans {
    
    private int id;
    private int idBankAccount;
    private int idType;
    private Date date;
    private String number;
    private double value;
    private String observations;
    private boolean canceled;
    private String type;
    private int idRegType;

    public BankTrans() {
        this.id = -1;
        this.canceled = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(int idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdRegType() {
        return idRegType;
    }

    public void setIdRegType(int idRegType) {
        this.idRegType = idRegType;
    }
    
    
}
