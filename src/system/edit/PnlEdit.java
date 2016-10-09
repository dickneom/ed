/*
 * Clase abastracta para extenderla con las clases de edición de registros.
 * Esto para poder utilizarza con WEdit, que necesita de una panel extendido de esta clase.
 */
package system.edit;

/**
 *
 * @author DickNeoM
 * 2016-08
 */
public abstract class PnlEdit extends javax.swing.JPanel {

    protected boolean ok;
    protected int rowsAffected;

    public PnlEdit() {
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }
    
    public boolean isOk() {
        return ok;
    }
    
    public int getRowsAffected() {
        return rowsAffected;
    }
    
    
    protected abstract void init();
    
    protected abstract void loadData();
    
    protected abstract void showData();
    
    protected abstract void save();
    
}
