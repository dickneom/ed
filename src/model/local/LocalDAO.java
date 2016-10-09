/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.local;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author DickNeoM
 */
public class LocalDAO extends DAOSQL {
    
    private static final String TABLE = "local";
    
    public static String validate(Local local) throws ClassNotFoundException, SQLException {
        String error;
        
        error = validateCode(local);
        if (error != null) {
            return error;
        }
        
        return error;
    }
    
    public static String validateCode(Local local) throws ClassNotFoundException, SQLException {
        if (local.getCode() == null || local.getCode().isEmpty()) {
            return AppGlobal.getText("LOCALDAO_ERROR_CODEEMPTY_TEXT");
        }
        
        Local l = get(local.getCode());
        if (l != null) {
            if (l.getId() != local.getId()) {
                return AppGlobal.getText("LOCALDAO_ERROR_CODEDUPLICATED_TEXT");
            }
        }
        
        return null;
    }
    
    
    
    public static int update(Local local) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[9];
        
        values[0] = local.getId();
        values[1] = local.getCode();
        values[2] = local.getNotes();
        values[3] = local.getProperty();
        values[4] = local.getIdType();
        values[5] = local.getFloor();
        values[6] = local.getArea();
        values[7] = local.getRent();
        values[8] = local.getDiscount();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, Local.FIELDS, values);
        return rowsAffected;
    }
    
    
    
    
    
    
    
    /**
     * Devuelve el local con el id indicado.
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Local get(int id) throws ClassNotFoundException, SQLException {
        Local local = get("id", id);
        
        return local;
    }
    
    /**
     * Devuelve el local con el código indicado.
     * @param code
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Local get(String code) throws ClassNotFoundException, SQLException {
        Local local = get("code", code);
        
        return local;
    }
    
    /**
     * Devuelve el local que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value <code>int</code>
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Local get(String field, int value) throws ClassNotFoundException, SQLException {
        Local local = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, null);
        if (map != null && map.size() > 0) {
            local = MapToLocal(map.getFirst());
        }
        
        return local;
    }
    
    /**
     * Devuelve el local que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value <code>String</code>
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Local get(String field, String value) throws ClassNotFoundException, SQLException {
        Local local = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        if (map != null && map.size() > 0) {
            local = MapToLocal(map.getFirst());
        }
        
        return local;
    }
    
    public static Locals gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        Locals locals = ListMapToLocals(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return locals;
    }
    
    
    
    public static Locals ListMapToLocals(LinkedList<HashMap<String, Object>> datos) {
        Locals locals = new Locals();
        
        for (HashMap<String, Object> dato : datos) {
            locals.add(MapToLocal(dato));
        }
        
        return locals;
    }
    
    
    public static Local MapToLocal(HashMap<String, Object> dato) {
        Local local = new Local();
        
        local.setId((int) dato.get("id"));
        local.setCode((String) dato.get("code"));
        local.setNotes((String) dato.get("notes"));
        local.setProperty((String) dato.get("property"));
        local.setIdType((int) dato.get("idtype"));
        local.setProperty((String) dato.get("property"));
        local.setFloor((int) dato.get("floor"));
        local.setArea((double) dato.get("area"));
        local.setRent((double) dato.get("rent"));
        local.setDiscount((double) dato.get("discount"));
        
        return local;
    }
}
