/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.type;

import console.DknConsole;
import java.sql.SQLException;
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
public class TypeDAO extends DAOSQL {
    
    private static String[] FIELDS = {"id", "code", "name", "description", "active"};
    
    public static String validate(String table, Type type) {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Validando");
        String error = null;
        
        error = validateCode(table, type); 
        if (error != null) {
            return error;
        }
        
        return error;
    }
    
    public static String validateCode(String table, Type type) {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Validando código");
        String error = null;
        
        if (type.getCode() == null || type.getCode().isEmpty()) {
            return AppGlobal.getText("WEDIT_TYPES_MSG_ERROR_EMPTYCODE_TEXT");
        }
        
        Type t = null;
        try {
            t = TypeDAO.get(table, type.getCode());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (t != null) {
            if (t.getId() != type.getId()) {
                return AppGlobal.getText("WEDIT_TYPES_MSG_ERROR_DUPLICATECODE_TEXT");
            }
        }
        
        return error;
    }
    
    
    
    public static int update(String table, Type type) throws ClassNotFoundException, SQLException {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Actualizando");
        Object[] values = new Object[5];
        
        values[0] = type.getId();
        values[1] = type.getCode();
        values[2] = type.getName();
        values[3] = type.getDescription();
        values[4] = (type.isActive()? 1 : 0);
        
        int rowsAffected = update(AppGlobal.getDataBase(), table, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static Type get(String table, int id) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), table, id);
        
        Type type = null;
        if (map != null && map.size() > 0) {
            type = MapToType(map.getFirst());
        }
        
        return type;
    }
    
    public static Type get(String table, String code) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), table, code);
        
        Type type = null;
        if (map != null && map.size() > 0) {
            type = MapToType(map.getFirst());
        }

        return type;
    }
    
    public static Type get(String table, String field, String value) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), table, field, value, true, null);
        
        Type type = null;
        if (map != null && map.size() > 0) {
            type = MapToType(map.getFirst());
        }
        
        return type;
    }
    
    public static Types gets(String table, String fieldOrder) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> map = selectAll(AppGlobal.getDataBase(), table, fieldOrder);
        
        Types types = null;
        if (map != null && map.size() > 0) {
            types = ListMapToTypes(map);
        }
        
        return types;
    }
    
    
    
    public static Types ListMapToTypes(LinkedList<HashMap<String, Object>> datos) {
        Types type = new Types();
        
        for (HashMap<String, Object> dato : datos) {
            type.add(MapToType(dato));
        }
        
        return type;
    }
    
    
    public static Type MapToType(HashMap<String, Object> dato) {
        Type type = new Type();
        
        type.setId((int) dato.get(FIELDS[0]));
        type.setCode((String) dato.get(FIELDS[1]));
        type.setName((String) dato.get(FIELDS[2]));
        type.setDescription((String) dato.get(FIELDS[3]));
        type.setActive(((int) dato.get(FIELDS[4])) == 1);
        
        return type;
    }
}
