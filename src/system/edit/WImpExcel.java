/*
 * Importa transacciones bancarias desde estados de cuenta en archivos excel emitidos por el banco internacional
 */
package system.edit;

import console.DknConsole;
import javax.swing.JDialog;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class WImpExcel extends JDialog {

    private final WindowData window;
    private PnlImpExcel pnlImpExcel;
    private final String parentCode;
    
    public WImpExcel(JDialog owner, WindowData window, String parentCode) {
        super(owner, true);
        this.window = window;
        this.parentCode = parentCode;
        
        init();
        
        pack();
        setLocationRelativeTo(owner);
    }
    
    private void init() {
//        if (window != null) {
//            if (window.getWidth() > 0 && window.getHeight() > 0) {
//                setSize(window.getWidth(), window.getHeight());
//            }
//            setSize(400, 400);
            
//            setTitle(window.getTitle());
            setTitle("Importar");
            setResizable(false);
            
//            switch (window.getCode()) {
            switch (parentCode) {
                // Para editar datos TYPE
                case "WLIST_BANKTRANS":
                    pnlImpExcel = new PnlImpExcel(this);
                    break;
                default:
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Código del PANEL no encontrado. Código: " + window.getCode());
                    break;
            }
            
            if (pnlImpExcel != null) {
                setContentPane(pnlImpExcel);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Panel nulo. Código: " + window.getCode());
            }
//        }
    }

    public boolean isOk() {
        if (pnlImpExcel != null) {
            return pnlImpExcel.isOk();
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Panel nulo. Código: " + window.getCode());
            return false;
        }
    }
    
    public int getRowsImported() {
        if (pnlImpExcel != null) {
            return pnlImpExcel.getRowsImported();
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Panel nulo. Código: " + window.getCode());
            return -1;
        }
    }
}
