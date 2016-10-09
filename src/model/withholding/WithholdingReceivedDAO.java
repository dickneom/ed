/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.withholding;

import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.payment.Payments;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author richneom
 */
public class WithholdingReceivedDAO extends DAOSQL {
    
    private static final String TABLE = "withholdings";
    private static final String[] FIELDS = {"id", "serie", "number", "idinvoicetype", "idinvoice", "date", "value", "observations", "canceled"};
    
    public static String validate(WithholdingReceived withholdingReceived) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(WithholdingReceived withholdingReceived) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[9];
        
        values[0] = withholdingReceived.getId();
        values[1] = withholdingReceived.getSerie();
        values[2] = withholdingReceived.getNumber();
        values[3] = withholdingReceived.getIdInvoiceType();
        values[4] = withholdingReceived.getIdInvoice();
        values[5] = withholdingReceived.getDate();
        values[6] = withholdingReceived.getValue();
        values[7] = withholdingReceived.getObservations();
        values[8] = withholdingReceived.isCanceled();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static WithholdingReceived get(int id) throws ClassNotFoundException, SQLException {
        WithholdingReceived withholdingReceived = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, id);
        if (map != null && map.size() > 0) {
            withholdingReceived = MapToWithholdingReceived(map.getFirst());
        }
        
        return withholdingReceived;
    }
    
    public static WithholdingReceived get(String code) throws ClassNotFoundException, SQLException {
        return get("code", code);
    }
    
    public static WithholdingReceived get(String field, String value) throws ClassNotFoundException, SQLException {
        WithholdingReceived wr = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        if (map != null && map.size() > 0) {
            wr = MapToWithholdingReceived(map.getFirst());
        }
        
        return wr;
    }
    
    public static WithholdingReceiveds gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        
        WithholdingReceiveds withholdingReceiveds = ListMapToWithholdingReceiveds(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return withholdingReceiveds;
    }
    
    
    
    public static WithholdingReceiveds ListMapToWithholdingReceiveds(LinkedList<HashMap<String, Object>> datos) {
        WithholdingReceiveds withholdingReceiveds = new WithholdingReceiveds();
        
        for (HashMap<String, Object> dato : datos) {
            withholdingReceiveds.add(MapToWithholdingReceived(dato));
        }
        
        return withholdingReceiveds;
    }
    
    
    public static WithholdingReceived MapToWithholdingReceived(HashMap<String, Object> dato) {
        WithholdingReceived withholdingReceived = new WithholdingReceived();
        
        withholdingReceived.setId((int) dato.get("id"));
        withholdingReceived.setSerie((String) dato.get("serie"));
        withholdingReceived.setNumber((String) dato.get("number"));
        withholdingReceived.setIdInvoiceType((int) dato.get("idinvoicetype"));
        withholdingReceived.setIdInvoice((int) dato.get("idinvoice"));
        try {
            withholdingReceived.setDate(DateTime.getStringToDateUtil((String) dato.get("date"), AppGlobal.getFormatDate()));
        } catch (ParseException ex) {
            Logger.getLogger(WithholdingReceivedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        withholdingReceived.setValue((double) dato.get("value"));
        withholdingReceived.setObservations((String) dato.get("observations"));
        withholdingReceived.setCanceled((int) dato.get("canceled") != 0);
        
        return withholdingReceived;
    }
    
    
    
    
    
    
    public static double getUsed(WithholdingReceived wr) throws ClassNotFoundException, SQLException {
        Payments ps = PaymentDAO.getsWithholding(wr.getId());
        
        double sum = 0;
        if (ps != null) {
            for (Payment p : ps) {
                sum += p.getValue();
            }
        }
        
        return sum;
    }
}
