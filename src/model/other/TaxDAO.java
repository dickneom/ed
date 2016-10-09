/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.other;

import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author DickNeoM
 */
public class TaxDAO extends DAOSQL {
    
    private static String TABLE = "taxes";
    private static String[] FIELDS = {"id", "code", "name", "value", "dateinit", "datefinal"};
    
    public static String validate(WindowData tax) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(WindowData tax) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[6];
        
        values[0] = tax.getId();
        values[1] = tax.getCode();
        values[2] = tax.getName();
        values[3] = tax.getValue();
        values[4] = tax.getDateInit();
        values[5] = tax.getDateFinal();
        
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static WindowData get(int id) throws ClassNotFoundException, SQLException {
        
        WindowData tax = MapToTax(select(AppGlobal.getDataBase(), TABLE, id).getFirst());
        
        return tax;
    }
    
    public static WindowData get(String code) throws ClassNotFoundException, SQLException {
        
        WindowData tax = MapToTax(select(AppGlobal.getDataBase(), TABLE, code).getFirst());
        
        return tax;
    }
    
    public static WindowData get(String field, String value) throws ClassNotFoundException, SQLException {
        
        WindowData tax = MapToTax(select(AppGlobal.getDataBase(), TABLE, field, value, true, null).getFirst());
        
        return tax;
    }
    
    public static Taxes gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        
        Taxes dts = ListMapToTaxes(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return dts;
    }
    
    
    
    
    

    public static Taxes ListMapToTaxes(LinkedList<HashMap<String, Object>> datos) {
        Taxes tax = new Taxes();
        
        for (HashMap<String, Object> dato : datos) {
            tax.add(MapToTax(dato));
        }
        
        return tax;
    }
    
    
    public static WindowData MapToTax(HashMap<String, Object> dato) {
        WindowData tax = new WindowData();
        
        tax.setId((int) dato.get("id"));
        tax.setCode((String) dato.get("code"));
        tax.setName((String) dato.get("name"));
        tax.setValue((double) dato.get("value"));
        
        try {
            tax.setDateInit(DateTime.getStringToDateUtil((String) dato.get("dateinit"), AppGlobal.getFormatDate()));
        } catch (ParseException ex) {
            Logger.getLogger(TaxDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            tax.setDateFinal(DateTime.getStringToDateUtil((String) dato.get("datefinal"), AppGlobal.getFormatDate()));
        } catch (ParseException ex) {
            Logger.getLogger(TaxDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tax;
    }
}
