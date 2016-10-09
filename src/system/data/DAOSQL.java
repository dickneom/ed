/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.data;

import console.DknConsole;
import DknTime.DateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import system.config.AppGlobal;

/**
 *
 * @author DickNeoM
 */
public abstract class DAOSQL {
    
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String URL_BASE = "jdbc:sqlite:";
    
    
    public static Connection getConection(String dataBase) throws ClassNotFoundException, SQLException {
        String url = URL_BASE + dataBase;
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), "Conectando a: " + url + "...");
        
        Class.forName(DRIVER);
        
        Connection con = DriverManager.getConnection(url);
        
        if (con != null)
            DknConsole.msgIsOk(true);
        else
            DknConsole.msgIsOk(false);
        
        return con;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * Sirve para insertar o actualizar un registro.<br>
     * Si el "id" es menor que 0 (-1) se inserta caso contrario se actualiza.
     * El id es el primer valor de arreglo values.
     * @param dataBase
     * @param table
     * @param fieldsName arreglo con el nombre de los fields, el primer field deber ser "id"
     * @param values arreglo con los valores para cada campo en el arreglo <b>fieldsName</b>, el primer valor es el del "id"
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static int update(String dataBase, String table, String[] fieldsName, Object[] values) throws ClassNotFoundException, SQLException {
        int rowsAffected;
        
        int id = (int) values[0];
        
        String fields = "", variables = "";
        for (String field : fieldsName) {
            if (!field.equals("id")) { // En la lista de campos a actualizar no se toma en cuenta el id.
                if (id < 0) { // Para el caso de insertar (insert)
                    fields += field + ", ";
                    variables += "?, ";
                }
                else { // Para el caso de actualizar (update)
                    fields += field + " = ?, ";
                }
            }
        }
        fields = fields.substring(0, fields.length() - 2);
        if (id < 0) {
            variables = variables.substring(0, variables.length() - 2);
        }
        
        String sql;
        if (id < 0) {
            sql = "INSERT INTO table (" + fields + ") VALUES (" + variables + ")";
        }
        else {
            sql = "UPDATE table SET " + fields + " WHERE id = ?";
        }
        
        sql = sql.replace("table", table);
        
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sql)) {
            
            DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), "SQL: " + sql);
            
            for (int i = 1; i < values.length; i++) {
                String clase;
                
                if (values[i] != null) {
                    clase = values[i].getClass().getName();
                    
                    DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), "Campo: " + fieldsName[i] + " Nombre de clase: " + clase + " Valor: " + values[i]);
                    
                    switch (clase) {
                        case "java.lang.String":
                            ps.setString(i, (String) values[i]);
                            break;
                        case "java.lang.Integer":
                            ps.setInt(i, (int) values[i]);
                            break;
                        case "java.lang.Double":
                            ps.setDouble(i, (double) values[i]);
                            break;
                        case "java.lang.Boolean":
                            ps.setDouble(i, ((boolean) values[i])? 1 : 0);
                            break;
                        case "java.util.Date":
                            ps.setString(i, DateTime.getDateUtilToString((java.util.Date) values[i], AppGlobal.getFormatDate()));
                            break;
                        default:
                            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "DAOSQL.update(). Clase no conocida. clase: " + clase + " field: " + fieldsName[i]);
                            break;
                    }
                }
                else {
                    DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "DAOSQL.update(). Valor nulo. field: " + fieldsName[i]);
                }
                
            }
            
            int numCampoId = fieldsName.length;
            if (id >= 0) {
                ps.setInt(numCampoId, id); // el ultimo valor del la sentencia SQL es el id, despues del where.
            }
            
            rowsAffected = ps.executeUpdate();
        }
        
        return rowsAffected;
    }
    
    
    
    public static int delete(String dataBase, String table, int id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM table WHERE id = ?";
        sql = sql.replace("table", table);
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " SQL: " + sql + " id: " + id);
        
        int rowsAffected;
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            rowsAffected = ps.executeUpdate();
        }
        
        return rowsAffected;
    }
    
    public static int delete(String dataBase, String table, String field, int value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM table WHERE field = ?";
        sql = sql.replace("table", table);
        sql = sql.replace("field", field);
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " SQL: " + sql + " Valor: " + value);
        
        int rowsAffected;
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, value);
            rowsAffected = ps.executeUpdate();
        }
        
        return rowsAffected;        
    }
    
    
    /**
     * Anula un registro.<br><br>
     * <b>La tabla debe contener un campo "canceled" para poder anular un registro.</b><br>
     * @param dataBase
     * @param table
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static int cancel(String dataBase, String table, int id) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE table SET canceled = 1 WHERE id = ?";
        sql = sql.replace("table", table);
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " SQL: " + sql + " id: " + id);
        
        int rowsAffected;
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            rowsAffected = ps.executeUpdate();
        }
        
        return rowsAffected;
    }    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    protected static LinkedList<HashMap<String, Object>> selectAll(String dataBase, String table, String fieldOrder) throws ClassNotFoundException, SQLException {
        return DAOSQL.select(dataBase, table, null, null, true, fieldOrder);
    }
    
    
    
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, String code) throws ClassNotFoundException, SQLException {
        return DAOSQL.select(dataBase, table, "code", code, true, "code");
    }
    
    
    /**
     * Devuelve un <code>List.Map.String, Object..</code> con los datos seleccionado de la base de datos.
     * @param dataBase
     * @param table
     * @param fieldSearch
     * @param valueSearch <code>String</code> con el valor a buscar
     * @param exact <code>true</code> para busqueda exacta con "=", <code>false</code> para busqueda aproximada con <code>LIKE</code>
     * @param fieldOrder
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, String fieldSearch, String valueSearch, boolean exact, String fieldOrder) throws ClassNotFoundException, SQLException {
        return DAOSQL.select(dataBase, table, "*", fieldSearch, valueSearch, exact, fieldOrder);
    }
    
    /**
     * Devuelve un <code>List.Map.String, Object..</code> con los datos seleccionado de la base de datos.
     * @param dataBase
     * @param table
     * @param fields
     * @param fieldSearch
     * @param valueSearch <code>String</code> con el valor a buscar
     * @param exact <code>true</code> para busqueda exacta con "=", <code>false</code> para busqueda aproximada con <code>LIKE</code>
     * @param fieldOrder
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, String fields, String fieldSearch, String valueSearch, boolean exact, String fieldOrder) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM table";
        if (fields != null && !fields.isEmpty() && !fields.equals("*")) {
            sql = sql.replace("*", fields);
        }
        sql = sql.replace("table", table);
        
        if (fieldSearch != null && !fieldSearch.isEmpty()) {
            if (exact) {
                sql = sql + " WHERE " + fieldSearch + " = ?";
            }
            else {
                sql = sql + " WHERE " + fieldSearch + " LIKE ?";
            }
        }
        
        if (fieldOrder != null && !fieldOrder.isEmpty()) {
            sql = sql + " ORDER BY fieldOrder";
            sql = sql.replace("fieldOrder", fieldOrder);
        }
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " SQL: " + sql + " - " + fieldSearch + ": " + valueSearch);
        
        LinkedList<HashMap<String, Object>> datos;
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sql)) {
            if (fieldSearch != null && !fieldSearch.isEmpty()) {
                if (exact ) {
                    ps.setString(1, valueSearch);
                }
                else {
                    ps.setString(1, "%" + valueSearch + "%");
                }
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                datos = getResultSetToListMap(rs);
            }
        }
        
        return datos;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, int id) throws ClassNotFoundException, SQLException {
        return DAOSQL.select(dataBase, table, "id", id, "");
    }
    
    
    /**
     * Devuelve un <code>List.Map.String, Object..</code> con los datos seleccionado de la base de datos.
     * @param dataBase
     * @param table
     * @param fieldSearch
     * @param valueSearch <code>int</code> con el valor a buscar
     * @param fieldOrder
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, String fieldSearch, int valueSearch, String fieldOrder) throws ClassNotFoundException, SQLException {
        return DAOSQL.select(dataBase, table, "*", fieldSearch, valueSearch, fieldOrder);
    }
    
    /**
     * Devuelve un <code>List.Map.String, Object..</code> con los datos seleccionado de la base de datos.
     * @param dataBase
     * @param table
     * @param fields
     * @param fieldSearch
     * @param valueSearch <code>int</code> con el valor a buscar
     * @param fieldsOrder
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, String fields, String fieldSearch, int valueSearch, String fieldsOrder) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM table";
        if (fields != null && !fields.isEmpty() && !fields.equals("*")) {
            sql = sql.replace("*", fields);
        }
        sql = sql.replace("table", table);
        
        String where = null;
        if (fieldSearch != null && !fieldSearch.isEmpty()) {
            sql = sql + " WHERE " + fieldSearch + " = ?";
        }
        
        if (fieldsOrder != null && !fieldsOrder.isEmpty()) {
            sql = sql + " ORDER BY fieldOrder";
            sql = sql.replace("fieldOrder", fieldsOrder);
        }
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " SQL: " + sql + " id: " + valueSearch);
        
        LinkedList<HashMap<String, Object>> datos;
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sql)) {
            if (fieldSearch != null && !fieldSearch.isEmpty()) {
                ps.setInt(1, valueSearch);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                datos = getResultSetToListMap(rs);
            }
        }
        
        return datos;
    }
    
    
    
    
    /**
     * Devuelve un <code>List.Map.String, Object..</code> con los datos seleccionado de la base de datos.
     * @param dataBase archivo de la base de datas (ruta completa)
     * @param table nombre de la table o vista donde se realiza la consulta
     * @param fields fields que se consultaran
     * @param where condicion para la busqueda
     * @param fieldsOrder expresion de fieldOrder
     * @param group expresion de agrupamiento
     * @return un List Map con los registros resultantes
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String table, String fields, String where, String fieldsOrder, String group) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM table";
        
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " SQL: " + sql);
        
        sql = sql.replace("*", fields);
        sql = sql.replace("table", table);
        if (where != null && !where.isEmpty()) {
            sql += " WHERE " + where;
        }
        if (fieldsOrder != null && !fieldsOrder.isEmpty()) {
            sql += " ORDER BY " + fieldsOrder;
        }
        
        if (group != null && !group.isEmpty()) {
            sql += " GROUP BY " + group;
        }
        
        return select(dataBase, sql);
    }
    
    /**
     * Ejecuta un select en la base de datos indicada
     * @param dataBase
     * @param sqlSelect <code>String</code> con la instrucción <code>SELECT</code>
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected static LinkedList<HashMap<String, Object>> select(String dataBase, String sqlSelect) throws ClassNotFoundException, SQLException {
        LinkedList<HashMap<String, Object>> datos;
        try (Connection con = getConection(dataBase); PreparedStatement ps = con.prepareStatement(sqlSelect); ResultSet rs = ps.executeQuery()) {
            datos = getResultSetToListMap(rs);
        }
        
        return datos;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Convierte un ResultSet en un HashMap
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static LinkedList<HashMap<String, Object>> getResultSetToListMap(ResultSet rs) throws SQLException {
        LinkedList<HashMap<String, Object>> datos = new LinkedList<>();
        HashMap<String, Object> dato;
        String key;
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumnas = rsmd.getColumnCount();
        while (rs.next()) {
            
            dato = new HashMap<>();
            for (int i = 1; i <= numColumnas; i++) {
                key = rsmd.getColumnName(i);
                dato.put(key, rs.getObject(key));
            }
            datos.add(dato);
        }
        
        return datos;
    }
    
    
 }
