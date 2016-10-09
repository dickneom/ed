/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.data;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author richneom
 * @param <T>
 * @param <TS> 
 */
public abstract class DAOAbstract<T, TS> extends DAOSQL {
    public abstract String validate(T t);
    
    public abstract int update(T t);
    
    public abstract int delete(int id);
    
    public abstract T get(int id);
    
    public abstract T get(String code);
    
    public abstract T get(String field, String value);
    
    public abstract TS gets(String fieldOrder);
    
    public abstract TS extractListMap(LinkedList<HashMap<String, Object>> datos);
    
    public abstract T extractMap(HashMap<String, Object> dato);
}
