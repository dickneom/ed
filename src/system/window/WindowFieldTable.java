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
public class WindowFieldTable {
    
    // Campos para la tabla (visibles y ocultas)
    private String code;
    private String title;
    private String table; // nombre de la tabla del campo
    private String field; // nombre del campo
    private int columnWidth;
    private int columnWidthMax;
    private int columnWidthMin;
    private boolean visible; // Indica si la columna es visible en la tabla?
    private int sequence;

    public WindowFieldTable(String code, String title, String table, String field, int columnWidth, boolean visible) {
        this(code, title, table, field, columnWidth, columnWidth * 4, 0, visible, 0);
    }

    public WindowFieldTable(String code, String title, String table, String field, int columnWidth, int columnWidthMax, int columnWidthMin, boolean visible, int sequence) {
        this.code = code;
        this.title = title;
        this.table = table;
        this.field = field;
        this.columnWidth = columnWidth;
        this.columnWidthMax = columnWidthMax;
        this.columnWidthMin = columnWidthMin;
        this.visible = visible;
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

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public int getColumnWidthMax() {
        return columnWidthMax;
    }

    public void setColumnWidthMax(int columnWidthMax) {
        this.columnWidthMax = columnWidthMax;
    }

    public int getColumnWidthMin() {
        return columnWidthMin;
    }

    public void setColumnWidthMin(int columnWidthMin) {
        this.columnWidthMin = columnWidthMin;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }


    

}
