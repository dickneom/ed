/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.window;

/**
 *
 * @author richneom
 */
public class WindowFieldSearch {
    // Campos para la tabla (visibles y ocultas) y campos para buscar y ordenar
    private String code;
    private String title;
    private String table; // nombre de la tabla del campo
    private String field; // nombre del campo
    private boolean search; // Este campo se usa en buscar?
    private boolean global; // Se utiliza en busqueda global?
    private boolean order; // Este campo se usa para ordenar?
    private int sequence; // secuencia en que aparecen los botones
    
    WindowFieldSearch() {
        this.code = "";
        this.title = "";
        this.table = "";
        this.field = "";
        this.search = true;
        this.global = true;
        this.order = true;
    }
    
    public WindowFieldSearch(String code, String title, String table, String field, boolean search, boolean global, boolean order, int sequence) {
        this.code = code;
        this.title = title;
        this.table = table;
        this.field = field;
        this.search = search;
        this.global = global;
        this.order = order;
        this.sequence = sequence;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }



}
