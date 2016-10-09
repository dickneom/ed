/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ente;

import console.DknConsole;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author DickNeoM
 */
public class EnteDAO extends DAOSQL {
    
    private static final String TABLE = "entes";
    private static final String TABLE_ADDRESS = "entesaddress";
    
    public static String validate(Ente ente) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(Ente ente) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[9];
        
        values[0] = ente.getId();
        values[1] = ente.getCode();
        values[2] = ente.getName();
        values[3] = ente.getSurname();
        values[4] = ente.getIdDniType();
        values[5] = ente.getDni();
        values[6] = ente.getIdType();
        values[7] = ente.getNote();
        values[8] = (ente.isActive()? 1 : 0);
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, Ente.FIELDS, values);
        
        if (rowsAffected > 0 && ente.getAddress() != null) {
            int idEnte;
            if (ente.getId() < 0) {
                idEnte = EnteDAO.get("code", ente.getCode()).getId();
            }
            else {
                idEnte = ente.getId();
            }
            
            ente.getAddress().setIdEnte(idEnte);
            int addressRowsAffected = updateAddress(ente.getAddress());
        }
        
        return rowsAffected;
    }
    
    
    private static int updateAddress(EnteAddress enteAddress) throws ClassNotFoundException, SQLException {
        DknConsole.println(Thread.currentThread().getStackTrace()[1].toString() + " Filas afectadas: " + enteAddress.getId());
        
        Object[] reg = enteAddress.toArray();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE_ADDRESS, enteAddress.FIELDS, reg);
        
        return rowsAffected;
    }
    
    
    
    /**
     * Devuelve el ente con el id indicado.
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Ente get(int id) throws ClassNotFoundException, SQLException {
        Ente ente = get("id", id);
        
        return ente;
    }
    
    /**
     * Devuelve el ente con el código indicado.
     * @param code
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Ente get(String code) throws ClassNotFoundException, SQLException {
        Ente ente = get("code", code);
        
        return ente;
    }
    
    /**
     * Devuelve el ente que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value cadena
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Ente get(String field, String value) throws ClassNotFoundException, SQLException {
        Ente ente = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        if (map != null && map.size() > 0) {
            ente = MapToEnte(map.getFirst());
        }
        
        return ente;
    }
    
    /**
     * Devuelve el ente que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value valor entero
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Ente get(String field, int value) throws ClassNotFoundException, SQLException {
        Ente ente = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, null);
        if (map != null && map.size() > 0) {
            ente = MapToEnte(map.getFirst());
        }
        
        return ente;
    }
    
    public static Entes gets(String fieldOrder) throws ClassNotFoundException, SQLException {
        
        Entes dts = ListMapToEntes(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return dts;
    }
    
    
    
    
    

    public static Entes ListMapToEntes(LinkedList<HashMap<String, Object>> datos) throws ClassNotFoundException, SQLException {
        Entes ente = new Entes();
        
        for (HashMap<String, Object> dato : datos) {
            ente.add(MapToEnte(dato));
        }
        
        return ente;
    }
    
    
    public static Ente MapToEnte(HashMap<String, Object> dato) throws ClassNotFoundException, SQLException {
        Ente ente = new Ente();
        
        ente.setId((int) dato.get("id"));
        ente.setCode((String) dato.get("code"));
        ente.setName((String) dato.get("name"));
        ente.setSurname((String) dato.get("surname"));
        ente.setIdDniType((int) dato.get("iddnitype"));
        ente.setDni((String) dato.get("dni"));
        ente.setIdType((int) dato.get("idtype"));
        ente.setNote((String) dato.get("notes"));
        ente.setActive(((int) dato.get("active")) == 1);
        
        fillAddress(ente);
        
        return ente;
    }
    
    
    
    
    
    private static void fillAddress(Ente ente) throws ClassNotFoundException, SQLException {
        LinkedList<EnteAddress> address = getsAddresses(ente.getId());
        if (address != null && address.size() > 0) {
            ente.setAddress(address.getFirst());
        }
    }
    
    
    private static LinkedList<EnteAddress> getsAddresses(int idEnte) throws ClassNotFoundException, SQLException {
        LinkedList<EnteAddress> addresses = ListMapToAddresses(select(AppGlobal.getDataBase(), TABLE_ADDRESS, "idEnte", idEnte, null));
        
        return addresses;
    }
    
    private static LinkedList<EnteAddress> ListMapToAddresses(LinkedList<HashMap<String, Object>> datos) {
        LinkedList<EnteAddress> addresses = new LinkedList<>();
        
        for (HashMap<String, Object> dato : datos) {
            addresses.add(MapToEnteAddress(dato));
        }
        
        return addresses;
    }
    
    private static EnteAddress MapToEnteAddress(HashMap<String, Object> dato) {
        EnteAddress address = new EnteAddress();
        
        address.setId((int) dato.get("id"));
        address.setIdEnte((int) dato.get("idente"));
        address.setIdCountry((int) dato.get("idcountry"));
        address.setState((String) dato.get("state"));
        address.setCity((String) dato.get("city"));
        address.setMainStreet((String) dato.get("mainstreet"));
        address.setNumber((String) dato.get("number"));
        address.setStreet((String) dato.get("street"));
        address.setReference((String) dato.get("reference"));
        address.setActive(((int) dato.get("active") == 1));
        address.setBilling(((int) dato.get("billing") == 1));
        
        return address;
    }
}
