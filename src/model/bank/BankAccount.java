/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bank;

/**
 *
 * @author richneom
 */
public class BankAccount {
    private int id;
    private String code;
    private String name;
    private int idBank;
    private int idType;
    private String number;
    private boolean active;

    public BankAccount() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getIdBank() {
        return idBank;
    }

    public void setIdBank(int idBank) {
        this.idBank = idBank;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
    
    
    
    
    @Override
    public String toString() {
        return code;
    }
    
    public String aTexto() {
        String str;
        
        str = "Cuenta Bancaria:";
        str += " Id: " + getId();
        str += " Codigo: " + getCode();
        str += " Banco: " + getIdBank();
        str += " Tipo: " + getIdType();
        str += " Number: " + getNumber();
        str += " Activo: " + (isActive()? "SI": "NO");
        
        return str;
    }
}
