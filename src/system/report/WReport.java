/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.report;

import console.DknConsole;
import javax.swing.JDialog;
import javax.swing.JFrame;
import system.window.WindowData;

/**
 *
 * @author DickNeoM
 */
public class WReport extends JDialog {

    protected final WindowData window;
    protected PnlReport pnlReport;
    
    
    public WReport(JFrame parent, WindowData window) {
        super(parent, window.isModal());
        this.window = window;
        this.pnlReport = null;
        
        if (window != null) {
            setSize(window.getWidth(), window.getHeight());
            
            setTitle(window.getTitle());
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. No hay datos de ventana.");
        }
        
        this.pnlReport = new PnlReport(this, window);
        setContentPane(this.pnlReport);
            
        setLocationRelativeTo(parent);
    }
    
    public WReport(JDialog parent, WindowData window) {
        super(parent, window.isModal());
        this.window = window;
        this.pnlReport = null;
        
        if (window != null) {
            setSize(window.getWidth(), window.getHeight());
            
            setTitle(window.getTitle());
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. No hay datos de ventana.");
        }
        
        this.pnlReport = new PnlReport(this, window);
        setContentPane(this.pnlReport);
            
        setLocationRelativeTo(parent);
    }

    public boolean isOk() {
        return this.pnlReport.isOk();
    }

    public int getIdSelected() {
        return this.pnlReport.getIdSelected();
    }
    
}
