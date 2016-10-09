/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.company;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author DickNeoM
 */
public class CompanyDAO extends DAOSQL {
    
    private static final String TABLE = "companies";
    private static final String[] FIELDS = {"id", "code", "name", "ruc", "tradename", "idtype", "lastinvoicenumber"};
    
    public static String validate(Company company) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(Company company) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[7];
        
        values[0] = company.getId();
        values[1] = company.getCode();
        values[2] = company.getName();
        values[3] = company.getRuc();
        values[4] = company.getTradename();
        
        values[5] = company.getIdtype();
        values[6] = (company.getLastInvoiceNumber());
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    public static int updateLastInvoiceNumber(int idCompany, int invoiceNumber) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[2];
        
        values[0] = idCompany;
        values[1] = invoiceNumber;
        
        String[] fields = {"id", "lastinvoicenumber"};
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, fields, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    public static Company get(int id) throws ClassNotFoundException, SQLException {
        
        Company company = MapToCompany(select(AppGlobal.getDataBase(), TABLE, id).getFirst());
        
        return company;
    }
    
    public static Company get(String code) throws ClassNotFoundException, SQLException {
        
        Company company = MapToCompany(select(AppGlobal.getDataBase(), TABLE, code).getFirst());
        
        return company;
    }
    
    public static Company get(String field, String value) throws ClassNotFoundException, SQLException {
        
        Company Company = MapToCompany(select(AppGlobal.getDataBase(), TABLE, field, value, true, null).getFirst());
        
        return Company;
    }
    
    public static Companies gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        
        Companies companies = ListMapToCompanies(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return companies;
    }
    
    
    
    
    public static Companies ListMapToCompanies(LinkedList<HashMap<String, Object>> datos) {
        Companies companies = new Companies();
        
        for (HashMap<String, Object> dato : datos) {
            companies.add(MapToCompany(dato));
        }
        
        return companies;
    }
    
    
    public static Company MapToCompany(HashMap<String, Object> dato) {
        Company company = new Company();
        
        company.setId((int) dato.get("id"));
        company.setCode((String) dato.get("code"));
        company.setName((String) dato.get("name"));
        company.setRuc((String) dato.get("ruc"));
        company.setTradename((String) dato.get("tradename"));
        company.setIdtype((int) dato.get("idtype"));
        company.setLastInvoiceNumber((int) dato.get("lastinvoicenumber"));
        
        return company;
    }
}
