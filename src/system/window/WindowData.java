/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.window;

import java.util.LinkedList;

/**
 *
 * @author richneom
 */
public class WindowData {
    private int id;
    private String code;
    private String name;
    private String title;
    private int width;
    private int height;
    private boolean maximized;
    private boolean modal;
    private boolean search;
    
    private String dataBase;
    private String table;
    private boolean useSqlBase; // true si se utiliza la instruccion sqlBase
    private String sqlBase; // Instruccion sql, que realiza la busqueda de datos generales, a esta se le puede agregar funciones de busqueda, filtrado, agrupamiento, ordenamiento
    private boolean loadDataStart;
    
    private int colChangeColor;
    private int valueChangeColor;
    
    // CAMPOS PARA COLUMNAS DE LAS TABLAS
    private LinkedList<WindowButton> buttons;

    // CAMPOS PARA BUSCAR
    private LinkedList<WindowFieldSearch> fieldsSearch;
    
    // CAMPOS PARA COLUMNAS DE LAS TABLAS
    private LinkedList<WindowFieldTable> fieldsTable;
    
    public WindowData() {
        this.id = -1;
        this.search = false;
        
        this.buttons = new LinkedList<>();
        this.fieldsSearch = new LinkedList<>();
        this.fieldsTable = new LinkedList<>();
        
        this.colChangeColor = -1;
        this.valueChangeColor = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    public boolean isModal() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }
    
    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public boolean isUseSqlBase() {
        return useSqlBase;
    }

    public void setUseSqlBase(boolean useSqlBase) {
        this.useSqlBase = useSqlBase;
    }

    public String getSqlBase() {
        return sqlBase;
    }

    public void setSqlBase(String sqlBase) {
        this.sqlBase = sqlBase;
    }

    public boolean isLoadDataStart() {
        return loadDataStart;
    }

    public void setLoadDataStart(boolean loadDataStart) {
        this.loadDataStart = loadDataStart;
    }

    public int getColChangeColor() {
        return colChangeColor;
    }

    public void setColChangeColor(int colChangeColor) {
        this.colChangeColor = colChangeColor;
    }

    public int getValueChangeColor() {
        return valueChangeColor;
    }

    public void setValueChangeColor(int valueChangeColor) {
        this.valueChangeColor = valueChangeColor;
    }

    
    
    
    
    
    
    
    
    
    public LinkedList<WindowButton> getButtons() {
        return buttons;
    }
    
    public WindowButton getButton(String key) {
        for (WindowButton wb : buttons) {
            if (wb.getCode().equals(key)) {
                return wb;
            }
        }
        return null;
    }
    
    public WindowButton getButton(int index) {
        return buttons.get(index);
    }
    
    public void addButton(WindowButton button) {
        buttons.add(button);
    }
    
    
    
    
    
    
    
    
    
    
    public LinkedList<WindowFieldSearch> getFieldsSearch() {
        return fieldsSearch;
    }
    
    public WindowFieldSearch getFieldSearch(String key) {
        for (WindowFieldSearch wf : fieldsSearch) {
            if (wf.getCode().equals(key)) {
                return wf;
            }
        }
        return null;
    }
    
    public WindowFieldSearch getFieldSearch(int index) {
        return fieldsSearch.get(index);
    }
    
    public void addFieldSearch(WindowFieldSearch field) {
        fieldsSearch.add(field);
    }
    
    
    
    
    
    
    
    
    
    
    public LinkedList<WindowFieldTable> getFieldsTable() {
        return fieldsTable;
    }
    
    public WindowFieldTable getFieldTable(String key) {
        for (WindowFieldTable wf : fieldsTable) {
            if (wf.getCode().equals(key)) {
                return wf;
            }
        }
        return null;
    }
    
    public WindowFieldTable getFieldTable(int index) {
        return fieldsTable.get(index);
    }
    
    public void addFieldTable(WindowFieldTable field) {
        fieldsTable.add(field);
    }
    
    
    
    
    
    
    @Override
    public String toString() {
        String str;
        
        str = "WindowData: ";
        str += " Codigo: " + code;
        str += " Titulo: " + title;
        str += " Ancho: " + width;
        str += " Alto: " + height;
        
        return str;
    }
}
