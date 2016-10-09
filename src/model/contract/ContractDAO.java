/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.contract;

import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.payment.Payments;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author richneom
 */
public class ContractDAO extends DAOSQL {
    
    private static final String TABLE = "contracts";
    
    public static String validate(Contract contract) {
        String error = null;
        
        return error;
    }
    
    public static String validateCode(Contract contract) throws ClassNotFoundException, SQLException, ParseException {
        if (contract.getCode() == null || contract.getCode().isEmpty()) {
            return AppGlobal.getText("CONTRACTDAO_ERROR_CODEEMPTY_TEXT");
        }
        
        Contract c = get(contract.getCode());
        if (c != null) {
            if (c.getId() != contract.getId()) {
                return AppGlobal.getText("CONTRACTDAO_ERROR_CODEDUPLICATED_TEXT");
            }
        }
        
        return null;
    }
    
    
    
    
    
    public static int update(Contract contract) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[17];
        
        values[0] = contract.getId();
        values[1] = contract.getCode();
        values[2] = contract.getDate();
        values[3] = contract.getIdCustomer();
        values[4] = contract.getIdLocal();
        values[5] = contract.getDateInit();
        values[6] = contract.getDateFinal();
        values[7] = contract.getRent();
        values[8] = contract.getDiscount();
        values[9] = contract.getDiscountDays();
        values[10] = contract.getWarranty();
        values[11] = (contract.isBackWarranty()? 1 : 0);
        values[12] = contract.getIdPayMethodWarranty();
        values[13] = contract.getDateFinish();
        values[14] = contract.getPurpose();
        values[15] = contract.getNotes();
        values[16] = (contract.isActive()? 1 : 0);
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, Contract.FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    /**
     * Devuelve el contrato con el id indicado.
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public static Contract get(int id) throws ClassNotFoundException, SQLException, ParseException {
        Contract contract = get("id", id);
        
        return contract;
    }
    
    /**
     * Devuelve el contrato con el código indicado.
     * @param code
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public static Contract get(String code) throws ClassNotFoundException, SQLException, ParseException {
        Contract contract = get("code", code);
        
        return contract;
    }
    
    /**
     * Devuelve el contrato que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public static Contract get(String field, String value) throws ClassNotFoundException, SQLException, ParseException {
        Contract contract = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        if (map != null && map.size() > 0) {
            contract = MapToContract(map.getFirst());
        }
        
        return contract;
    }
    
    /**
     * Devuelve el contrato que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public static Contract get(String field, int value) throws ClassNotFoundException, SQLException, ParseException {
        Contract contract = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, null);
        if (map != null && map.size() > 0) {
            contract = MapToContract(map.getFirst());
        }
        
        return contract;
    }
    
    public static Contracts gets(String fieldOrder) throws ClassNotFoundException, SQLException, ParseException {
        Contracts pms = ListMapToContracts(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return pms;
    }
    
    
    
    
    
    
    
    public static Contracts ListMapToContracts(LinkedList<HashMap<String, Object>> datos) throws ParseException {
        Contracts contracts = new Contracts();
        
        for (HashMap<String, Object> dato : datos) {
            contracts.add(MapToContract(dato));
        }
        
        return contracts;
    }
    
    
    public static Contract MapToContract(HashMap<String, Object> dato) throws ParseException {
        Contract contract = new Contract();
        
        contract.setId((int) dato.get("id"));
        contract.setCode((String) dato.get("code"));
        
        String dateStr = (String) dato.get("date");
        if (dateStr != null && !dateStr.isEmpty()) {
            contract.setDate(DateTime.getStringToDateUtil(dateStr, AppGlobal.getFormatDate()));
        }
        else {
            contract.setDate(null);
        }
        
        contract.setIdCustomer((int) dato.get("idcustomer"));
        contract.setIdLocal((int) dato.get("idlocal"));
        
        dateStr = (String) dato.get("initdate");
        if (dateStr != null && !dateStr.isEmpty()) {
            contract.setDateInit(DateTime.getStringToDateUtil(dateStr, AppGlobal.getFormatDate()));
        }
        else {
            contract.setDateInit(null);
        }
        
        dateStr = (String) dato.get("finaldate");
        if (dateStr != null && !dateStr.isEmpty()) {
            contract.setDateFinal(DateTime.getStringToDateUtil(dateStr, AppGlobal.getFormatDate()));
        }
        else {
            contract.setDateFinal(null);
        }
        
        contract.setRent((double) dato.get("rent"));
        contract.setDiscount((double) dato.get("discount"));
        contract.setDiscountDays((int) dato.get("discountdays"));
        contract.setWarranty((double) dato.get("warranty"));
        contract.setBackWarranty(((int) dato.get("backwarranty")) == 1);
        contract.setIdPayMethodWarranty((int) dato.get("idpaymethodwarranty"));
        
        dateStr = (String) dato.get("finishdate");
        if (dateStr != null && !dateStr.isEmpty()) {
            contract.setDateFinish(DateTime.getStringToDateUtil((String) dato.get("finishdate"), AppGlobal.getFormatDate()));
        }
        else {
            contract.setDateFinish(null);
        }
        
        contract.setPurpose((String) dato.get("purpose"));
        contract.setNote((String) dato.get("notes"));
        contract.setActive((int) dato.get("active") == 1);
        
        return contract;
    }
    
    
    
    
    
    
    
    
    
    public static double getPayment(Contract contract) throws ClassNotFoundException, SQLException {
        Payments ps = PaymentDAO.getsContract(contract.getId());
        
        double sum = 0;
        if (ps != null) {
            for (Payment p : ps) {
                sum += p.getValue();
            }
        }
        
        return sum;
    }
}
