/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.item;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author richneom
 */
public class ItemDAO extends DAOSQL {
    
    private static final String TABLE = "items";
    private static final String[] FIELDS = {"id", "code", "name", "barcode", "idtype", "stock", "cost", "utility", "price", "observations", "active", "hastax"};
    
    public static String validate(Item item) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(Item item) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[12];
        
        values[0] = item.getId();
        values[1] = item.getCode();
        values[2] = item.getName();
        values[3] = item.getBarcode();
        
        values[4] = item.getIdType();
        
        values[5] = item.getStock();
        values[6] = item.getCost();
        values[7] = item.getUtility();
        values[8] = item.getPrice();
        
        values[9] = item.getObservations();
        values[10] = (item.isActive()? 1 : 0);
        values[11] = (item.isHasTax()? 1 : 0);
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static Item get(int id) throws ClassNotFoundException, SQLException {
        
        Item item = MapToItem(select(AppGlobal.getDataBase(), TABLE, id).getFirst());
        
        return item;
    }
    
    public static Item get(String code) throws ClassNotFoundException, SQLException {
        
        Item item = MapToItem(select(AppGlobal.getDataBase(), TABLE, code).getFirst());
        
        return item;
    }
    
    public static Item get(String field, String value) throws ClassNotFoundException, SQLException {
        
        Item pm = MapToItem(select(AppGlobal.getDataBase(), TABLE, field, value, true, null).getFirst());
        
        return pm;
    }
    
    public static Items gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        
        Items pms = ListMapToItems(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return pms;
    }
    
    
    
    
    
    
    
    public static Items ListMapToItems(LinkedList<HashMap<String, Object>> datos) {
        Items items = new Items();
        
        for (HashMap<String, Object> dato : datos) {
            items.add(MapToItem(dato));
        }
        
        return items;
    }
    
    
    public static Item MapToItem(HashMap<String, Object> dato) {
        Item item = new Item();
        
        item.setId((int) dato.get("id"));
        item.setCode((String) dato.get("code"));
        item.setName((String) dato.get("name"));
        item.setBarcode((String) dato.get("barcode"));
        
        item.setIdType((int) dato.get("idtype"));
        
        item.setStock((double) dato.get("stock"));
        item.setCost((double) dato.get("cost"));
        item.setUtility((double) dato.get("utility"));
        item.setPrice((double) dato.get("price"));
        
        item.setObservations((String) dato.get("observations"));
        item.setActive(((int) dato.get("active")) == 1);
        item.setHasTax(((int) dato.get("hastax")) == 1);
        
        return item;
    }
}
