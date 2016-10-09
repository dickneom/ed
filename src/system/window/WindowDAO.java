/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.window;

import console.DknConsole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import system.config.AppGlobal;
import system.data.DAOSQL;
import static system.data.DAOSQL.getConection;

/**
 *
 * @author DickNeoM
 */
public class WindowDAO extends DAOSQL {
    
    private static final String TABLE = "windows";
    private static final String[] FIELDS = {"id", "code", "name", "title", "width", "height", "maximized", "modal",
        "dataBase", "table", "useSqlBase", "sqlBase", "loadDataStart", "colchangecolor", "valchangecolor"};
    
    public static String validate(WindowData window) {
        return null;
    }
    
    /**
     * Actualiza los datos de una ventana.<br>
     * Si el id es menor a 1 inserta (<code>INSERT</code>) una nueva ventana, caso contrario la actualizar (<code>UPDATE</code>)
     * @param window
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public static int update(WindowData window) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[FIELDS.length];
        
        values[0] = window.getId();
        values[1] = window.getCode();
        values[2] = window.getName();
        values[3] = window.getTitle();
        values[4] = window.getWidth();
        values[5] = window.getHeight();
        values[6] = window.isMaximized();
        values[7] = window.isModal();
        values[8] = window.getDataBase();
        values[9] = window.getTable();
        values[10] = window.isUseSqlBase();
        values[11] = window.getSqlBase();
        values[12] = window.isLoadDataStart();
        values[13] = window.getColChangeColor();
        values[14] = window.getValueChangeColor();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Retorna los datos de una ventana dado su codigo de ventana.<br>
     * Los datos pueden estar en una función o en la base de datos.
     * @param code codigo de la ventana
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static WindowData getWindow(String code) throws SQLException, ClassNotFoundException {
        WindowData window;
        
        switch (code.toUpperCase()) {
            case "WMAIN":
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Código de Ventana declarado. Cargando datos. codigo: " + code);
                return getWMain();
                
//            case "WLIST_BANKACCOUNTS":
//                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Código de Ventana declarado. Cargando datos. codigo: " + code);
//                return getWListBankAccounts();
//            case "WLIST_PAYMENTMETHODS":
//                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Código de Ventana declarado. Cargando datos. codigo: " + code);
//                return getWListPaymentMethods();
                
            case "WSEARCH_BANKTRANS":
                return getWSearchBankTrans();
            case "WSEARCH_CONTRACTS":
                return getWSearchContracts();
            case "WSEARCH_CUSTOMERS":
                return getWSearchCustomers();
            case "WSEARCH_INVOICES":
                return getWSearchInvoices();
            case "WSEARCH_LOCAL":
                return getWSearchLocal();
            case "WSEARCH_WITHHOLDINGRECEIVED":
                return getWSearchWithholdingReceived();
                
            default:
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Código de Ventana NO declarado. Buscando en base de datos. codigo: " + code);
                window = get(code);
                if (window != null) {
                    return window;
                }
                else {
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR: Código de Ventana no existe en la base de datos. Ventana nula. codigo: " + code);
                }
                break;
        }
        
        return null;
    }
    
    /**
     * Devuelve los datos de una ventana dado su código.<br>
     * Los datos deben estar en la base de datos.
     * @param code
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private static WindowData get(String code) throws SQLException, ClassNotFoundException {
        WindowData window;
        
        String sql = "SELECT * FROM windows WHERE code = '" + code + "'";
        window = getWindowData(sql);
        
        if (window != null) {
            getWindowButtons(window);
            
            getWindowFieldSearch(window);
            
            getWindowFieldTables(window);
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Código de Ventana no existe en la base de datos. Ventana nula. codigo: " + code);
        }
        
        return window;
    }
    
    /**
     * Devuelve los datos de una ventana dado su id.<br>
     * Los datos deben estar en la base de datos.
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static WindowData get(int id) throws SQLException, ClassNotFoundException {
        WindowData window;
        
        String sql = "SELECT * FROM windows WHERE id = " + id;
        window = getWindowData(sql);
        
        if (window != null) {
            getWindowButtons(window);
            
            getWindowFieldSearch(window);
            
            getWindowFieldTables(window);
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Id de Ventana no existe en la base de datos. Ventana nula. id: " + id);
        }
        
        return window;
    }
    
    
    
    private static WindowData getWindowData(String sql) throws SQLException, ClassNotFoundException {
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " Cargando datos de ventana. SQL: " + sql);
        
        WindowData window = null;
        
        try (Connection con = getConection(AppGlobal.getDataBase());
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                window = new WindowData();
                
                window.setId(rs.getInt("id"));
                window.setCode(rs.getString("code"));
                window.setName(rs.getString("name"));
                window.setTitle(AppGlobal.getText(rs.getString("title")));
                window.setWidth(rs.getInt("width"));
                window.setHeight(rs.getInt("height"));
                window.setMaximized(rs.getInt("maximized") != 0);
                window.setModal(rs.getInt("modal") != 0);

                window.setDataBase(AppGlobal.getDataBase());
//                window.setDataBase(rs.getString("database"));
                window.setTable(rs.getString("dbtable"));
                
                window.setLoadDataStart(rs.getInt("loaddatastart") == 1);
                window.setSqlBase(rs.getString("sqlbase"));
                window.setUseSqlBase(rs.getInt("usesqlbase") == 1);
                
                window.setColChangeColor(rs.getInt("colchangecolor"));
                window.setValueChangeColor(rs.getInt("valchangecolor"));
            }
        }
        
        return window;
    }
    
    private static void getWindowButtons(WindowData window) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM windowsbuttons WHERE idwindow = " + window.getId() + " ORDER BY sequence";
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " Cargando datos de botones. SQL: " + sql);
        
        try (Connection con = getConection(AppGlobal.getDataBase());
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            WindowButton button;
            window.getButtons().clear();
            while (rs.next()) {
                String c = rs.getString("code");
                if (!c.toUpperCase().equals("SEPARATOR")) {
                    String title = AppGlobal.getText(rs.getString("title"));

                    String command = rs.getString("command");
                    int width = rs.getInt("width");
                    int height = rs.getInt("height");
                    boolean visible = rs.getInt("visible") != 0;

                    button = new WindowButton(c, title, command, width, height, visible);
                }
                else {
                    button = new WindowButton(c, "", "", 0, 0, true);
                }

                window.addButton(button);
            }
        }
    }
    
    private static void getWindowFieldSearch(WindowData window) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM windowsfieldsearch WHERE idwindow = " + window.getId() + " ORDER BY sequence";
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " Cargando datos de campos de búsqueda. SQL: " + sql);
        
        try (Connection con = getConection(AppGlobal.getDataBase());
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            WindowFieldSearch field;
            window.getFieldsSearch().clear();
            while (rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                String table = rs.getString("dbtable");
                String fieldStr = rs.getString("field");
                boolean search = rs.getInt("search") != 0;
                boolean global = rs.getInt("global") != 0;
                boolean order = rs.getInt("field") != 0;
                int sequence = rs.getInt(("sequence"));

                field = new WindowFieldSearch(code, title, table, fieldStr, search, global, order, sequence);

                window.addFieldSearch(field);
            }
        }
    }
    
    private static void getWindowFieldTables(WindowData window) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM windowsfieldtable WHERE idwindow = " + window.getId() + " ORDER BY sequence";
        DknConsole.dBase(Thread.currentThread().getStackTrace()[1].toString(), " Cargando datos de columnas de tabla. SQL: " + sql);
        
        try (Connection con = getConection(AppGlobal.getDataBase());
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            WindowFieldTable field;
            window.getFieldsTable().clear();
            while (rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                String table = rs.getString("dbtable");
                String fieldStr = rs.getString("field");
                int colWidth = rs.getInt("columnwidth");
                int colWidthMax = rs.getInt("columnwidthmax");
                int colWidthMin = rs.getInt("columnwidthmin");
                boolean visible = rs.getInt("visible") != 0;
                int sequence = rs.getInt("sequence");

                field = new WindowFieldTable(code, title, table, fieldStr, colWidth, colWidthMax, colWidthMin, visible, sequence);

                window.addFieldTable(field);
            }
        }
    }
    
    
    
    private static WindowData getWMain() {
        WindowData wd = new WindowData();
        
        wd.setId(0);
        wd.setCode("WMAIN");
        wd.setTitle(AppGlobal.getAppTitle());
        wd.setWidth(800);
        wd.setHeight(600);
        wd.setMaximized(true);
        wd.setModal(false);
        
        return wd;
    }
    
    
    
    private static WindowData getWSearchBankTrans() throws SQLException, ClassNotFoundException {
        String code = "WLIST_BANKTRANS";
        
        WindowData w = get(code);
        
        w.setTitle(AppGlobal.getText("WSEARCH_BANKTRANS_TITLE"));
        w.setModal(true);
        w.setSearch(true);
        
        return w;
    }
    
    private static WindowData getWSearchContracts() throws SQLException, ClassNotFoundException {
        String code = "WLIST_CONTRACTS";
        
        WindowData w = get(code);
        
        w.setTitle(AppGlobal.getText("WSEARCH_CONTRACTS_TITLE"));
        w.setModal(true);
        w.setSearch(true);
        
        return w;
    }
    
    private static WindowData getWSearchCustomers() throws SQLException, ClassNotFoundException {
        String code = "WLIST_CUSTOMERS";
        
        WindowData w = get(code);
        
        w.setTitle(AppGlobal.getText("WSEARCH_CUSTOMERS_TITLE"));
        w.setModal(true);
        w.setSearch(true);
        
        return w;
    }
    
    private static WindowData getWSearchInvoices() throws SQLException, ClassNotFoundException {
        String code = "WLIST_INVOICES";
        
        WindowData w = get(code);
        
        w.setTitle(AppGlobal.getText("WSEARCH_INVOICES_TITLE"));
        w.setModal(true);
        w.setSearch(true);
        
        return w;
    }
    
    private static WindowData getWSearchLocal() throws SQLException, ClassNotFoundException {
        String code = "WLIST_LOCAL";
        
        WindowData w = get(code);
        
        w.setTitle(AppGlobal.getText("WSEARCH_LOCAL_TITLE"));
        w.setModal(true);
        w.setSearch(true);
        
        return w;
    }
    
    private static WindowData getWSearchWithholdingReceived() throws SQLException, ClassNotFoundException {
        String code = "WLIST_WITHHOLDINGRECEIVED";
        
        WindowData w = get(code);
        
        w.setTitle(AppGlobal.getText("WSEARCH_WITHHOLDINGRECEIVED_TITLE"));
        w.setModal(true);
        w.setSearch(true);
        
        return w;
    }
    
    
}
