/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.payment;

import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
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
public class PaymentDAO extends DAOSQL {
    
    private static final String TABLE = "payments";
    private static final String[] FIELDS = {"id", "code", "date", "idtype", "idinvoice",
        "idpaymentmethod", "idpaymentdoc", "value", "observations"};
    
    public static String validate(Payment payment) {
        String error = null;
        
        error = validateValue(payment.getValue());
        if (error != null) {
            return error;
        }
        
        
        return error;
    }
    
    public static String validateValue(double pay) {
        if (pay <= 0) {
            return AppGlobal.getText("PAYMENTDAO_ERROR_VALUEZERO_TEXT");
        }
        return null;
    }
    
    public static String validateDate(Date dateDoc, Date dateFac, Date datePay) {
        if (dateDoc.before(datePay) || dateDoc.before(dateFac)) {
            return AppGlobal.getText("PAYMENTDAO_ERROR_DATEBEFORE_TEXT");
        }
            
        return null;
    }
    
    
    
    public static int update(Payment payment) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[9];
        
        values[0] = payment.getId();
        values[1] = payment.getCode();
        values[2] = payment.getDate();
        values[3] = payment.getIdType();
        values[4] = payment.getIdInvoice();
        values[5] = payment.getIdPaymentMethod();
        values[6] = payment.getIdPaymentDoc();
        values[7] = payment.getValue();
        values[8] = payment.getObservations();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static Payment get(int id) throws ClassNotFoundException, SQLException {
        Payment payment = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, id);
        if (map != null && map.size() > 0) {
            payment = MapToPayment(map.getFirst());
        }
        
        return payment;
    }
    
    public static Payment get(String code) throws ClassNotFoundException, SQLException {
        return get("code", code);
    }
    
    public static Payment get(String field, String value) throws ClassNotFoundException, SQLException {
        Payment pm = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        if (map != null && map.size() > 0) {
            pm = MapToPayment(map.getFirst());
        }
        
        return pm;
    }
    
    public static Payments gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        Payments pms = ListMapToPayments(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return pms;
    }
    
    public static Payments getsInvoice(int idInvoice) throws ClassNotFoundException, SQLException {
        String where = "idtype = 1 AND idinvoice = " + idInvoice;
        
        LinkedList<HashMap<String, Object>> datos = select(AppGlobal.getDataBase(), TABLE, "*", where, null, null);
        
        Payments pms = null;
        if (datos != null) {
            pms = ListMapToPayments(datos);
        }
        
        return pms;
    }
    
    public static Payments getsContract(int idContract) throws ClassNotFoundException, SQLException {
        String where = "idtype = 2 AND idinvoice = " + idContract;
        
        LinkedList<HashMap<String, Object>> datos = select(AppGlobal.getDataBase(), TABLE, "*", where, null, null);
        
        Payments pms = null;
        if (datos != null) {
            pms = ListMapToPayments(datos);
        }
        
        return pms;
    }
    
    public static Payments getsBankTrans(int idBrankTrans) throws ClassNotFoundException, SQLException {
        String where = "(idpaymentmethod = 5 OR idpaymentmethod = 8) AND idpaymentdoc = " + idBrankTrans;
        LinkedList<HashMap<String, Object>> datos = select(AppGlobal.getDataBase(), TABLE, "*", where, null, null);
        
        Payments pms = null;
        if (datos != null) {
            pms = ListMapToPayments(datos);
        }
        
        return pms;
    }
    
    public static Payments getsWithholding(int idWithholding) throws ClassNotFoundException, SQLException {
        String where = "idpaymentmethod = 4 AND idpaymentdoc = " + idWithholding;
        LinkedList<HashMap<String, Object>> datos = select(AppGlobal.getDataBase(), TABLE, "*", where, null, null);
        
        Payments pms = null;
        if (datos != null) {
            pms = ListMapToPayments(datos);
        }
        
        return pms;
    }
    
    
    
    
    public static Payments ListMapToPayments(LinkedList<HashMap<String, Object>> datos) {
        Payments payments = new Payments();
        
        for (HashMap<String, Object> dato : datos) {
            payments.add(MapToPayment(dato));
        }
        
        return payments;
    }
    
    
    public static Payment MapToPayment(HashMap<String, Object> dato) {
        Payment payment = new Payment();
        
//        payment.setId((int) dato.get("id"));
//        payment.setCode((String) dato.get("code"));
//        
//        String dateStr = (String) dato.get("date");
//        if (dateStr != null && !dateStr.isEmpty()) {
//            try {
//                payment.setDate(DateTime.getStringToDateUtil(dateStr, AppGlobal.getDateFormat()));
//            } catch (ParseException ex) {
//                Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        else {
//            payment.setDate(null);
//        }
//        
//        payment.setIdType((int) dato.get("idtype"));
//        payment.setIdInvoice((int) dato.get("idinvoice"));
//        payment.setIdPaymentMethod((int) dato.get("idpaymentmethod"));
//        payment.setIdDocument((int) dato.get("iddocument"));
//        payment.setValue((double) dato.get("value"));
//        payment.setObservations((String) dato.get("observations"));
//        {"id", "code", "date", "idtype", "idinvoice",
//        "idpaymentmethod", "iddocument", "value", "observations"};
        payment.setId((int) dato.get(FIELDS[0]));
        payment.setCode((String) dato.get(FIELDS[1]));
        
        String dateStr = (String) dato.get(FIELDS[2]);
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                payment.setDate(DateTime.getStringToDateUtil(dateStr, AppGlobal.getFormatDate()));
            } catch (ParseException ex) {
                Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            payment.setDate(null);
        }
        
        payment.setIdType((int) dato.get(FIELDS[3]));
        payment.setIdInvoice((int) dato.get(FIELDS[4]));
        payment.setIdPaymentMethod((int) dato.get(FIELDS[5]));
        payment.setIdPaymentDoc((int) dato.get(FIELDS[6]));
        payment.setValue((double) dato.get(FIELDS[7]));
        payment.setObservations((String) dato.get(FIELDS[8]));
        
        return payment;
    }
    
    private static void setPaymentProperty(Payment payment, String property, Object value) {
        switch(property) {
            case "id":
                payment.setId((int) value);
        }
    }
}
