/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.vMain;

import javax.swing.JButton;
import javax.swing.JToolBar;
import system.config.AppGlobal;

/**
 *
 * @author DickNeoM
 */
public class TlbMain extends JToolBar {

    private final WMainControl control;

    public TlbMain(WMainControl control) {
        this.control = control;
        
        add(newButton(AppGlobal.getText("WMAIN_BTN_INVOICE_TEXT"), AppGlobal.getText("WMAIN_BTN_INVOICE_TIP"), "EDIT_INVOICES"));
        add(newButton(AppGlobal.getText("WMAIN_BTN_PAYMENT_TEXT"), AppGlobal.getText("WMAIN_BTN_PAYMENT_TIP"), "EDIT_PAYMENTS"));
    }
    
    private JButton newButton (String text, String tip, String command) {
        JButton btn = new JButton();
        
        btn.setText(text);
        
        if (tip != null && !tip.isEmpty()) {
            btn.setToolTipText(tip);
        }
        
        btn.setActionCommand(command);
        btn.addActionListener(control);
        
        return btn;
    }
}
