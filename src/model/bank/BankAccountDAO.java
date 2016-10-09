/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bank;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author DickNeoM
 */
public class BankAccountDAO extends DAOSQL {
    
    private static final String TABLE = "bankaccounts";
    private static final String[] FIELDS = {"id", "code", "name", "idbank", "idtype", "number", "active"};
    
    public static String validate(BankAccount bankAccount) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(BankAccount bankAccount) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[7];
        
        values[0] = bankAccount.getId();
        values[1] = bankAccount.getCode();
        values[2] = bankAccount.getName();
        values[3] = bankAccount.getIdBank();
        values[4] = bankAccount.getIdType();
        values[5] = bankAccount.getNumber();
        values[6] = (bankAccount.isActive()? 1 : 0);
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static BankAccount get(int id) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, id);
        
        BankAccount bankAccount = null;
        if (map != null) {
            bankAccount = MapToBankAccount(map.getFirst());
        }
        
        return bankAccount;
    }
    
    public static BankAccount get(String code) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, code);
        
        BankAccount bankAccount = null;
        if (map != null) {
            bankAccount = MapToBankAccount(map.getFirst());
        }
        
        return bankAccount;
    }
    
    public static BankAccount get(String field, String value) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        
        BankAccount ba = null;
        if (map != null) {
            ba = MapToBankAccount(map.getFirst());
        }
        
        return ba;
    }
    
    public static BankAccounts gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder);
        
        BankAccounts bas = null;
        if (map != null) {
            bas = ListMapToBankAccounts(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        }
        
        return bas;
    }
    
    
    
    public static BankAccounts ListMapToBankAccounts(LinkedList<HashMap<String, Object>> datos) {
        BankAccounts bankAccounts = new BankAccounts();
        
        for (HashMap<String, Object> dato : datos) {
            bankAccounts.add(MapToBankAccount(dato));
        }
        
        return bankAccounts;
    }
    
    
    public static BankAccount MapToBankAccount(HashMap<String, Object> dato) {
        BankAccount bankAccount = new BankAccount();
        
        bankAccount.setId((int) dato.get("id"));
        bankAccount.setCode((String) dato.get("code"));
        bankAccount.setName((String) dato.get("name"));
        bankAccount.setIdBank((int) dato.get("idbank"));
        bankAccount.setIdType((int) dato.get("idtype"));
        bankAccount.setNumber((String) dato.get("number"));
        bankAccount.setActive(((int) dato.get("active")) == 1);
        
        return bankAccount;
    }
}
