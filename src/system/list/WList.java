/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.list;

import console.DknConsole;
import javax.swing.JDialog;
import javax.swing.JFrame;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class WList extends JDialog {

    protected final WindowData window;
    protected PnlList pnlList;
    
    
    public WList(JFrame parent, WindowData window) {
        super(parent, window.isModal());
        this.window = window;
        this.pnlList = null;
        
        if (window != null) {
            setSize(window.getWidth(), window.getHeight());
            
            setTitle(window.getTitle());
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. No hay datos de ventana.");
        }
        
        this.pnlList = new PnlList(this, window);
        setContentPane(this.pnlList);
            
        setLocationRelativeTo(parent);
    }
    
    public WList(JDialog parent, WindowData window) {
        super(parent, window.isModal());
        this.window = window;
        this.pnlList = null;
        
        if (window != null) {
            setSize(window.getWidth(), window.getHeight());
            
            setTitle(window.getTitle());
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. No hay datos de ventana.");
        }
        
        this.pnlList = new PnlList(this, window);
        setContentPane(this.pnlList);
            
        setLocationRelativeTo(parent);
    }

    public boolean isOk() {
        return this.pnlList.isOk();
    }

    public int getIdSelected() {
        return this.pnlList.getIdSelected();
    }
    
}
