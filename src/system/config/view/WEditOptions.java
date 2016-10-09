/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.config.view;

import console.DknConsole;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import system.window.WindowData;

/**
 *
 * @author DickNeoM
 */
public class WEditOptions extends JDialog {

    public WEditOptions(JFrame parent, boolean modal, WindowData window) {
        super(parent, modal);
        
        if (window != null) {
            setTitle(window.getTitle());
            Dimension d = new Dimension(window.getWidth(), window.getHeight());
            setMaximumSize(d);
            setMinimumSize(d);
            setSize(d);
            
            setLocationRelativeTo(parent);
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. Datos de ventana nulos.");
        }
        
        setContentPane(new PnlEditOptions(this));
    }
    
    public void close() {
        dispose();
    }
}
