/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.vMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

/**
 *
 * @author richneom
 */
public class TlbMain extends JToolBar implements ActionListener {

    private final WMainControl control;

    public TlbMain(WMainControl control) {
        this.control = control;
        
        
    }
    
    void addButton (JMenu menu, String text, String command) {
        JMenuItem mni = new JMenuItem();
        
        mni.setText(text);
        mni.setActionCommand(command);
        mni.addActionListener(this);
        
        menu.add(mni);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
