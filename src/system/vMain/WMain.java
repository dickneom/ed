/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.vMain;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class WMain extends JFrame {

    private final WMainControl control;

    public WMain(WindowData window) throws HeadlessException {
        control = new WMainControl(this);
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                control.exit();
            }
        });
        
        if (window != null) {
            setSize(window.getWidth(), window.getHeight());
            if (window.isMaximized()) {
                setExtendedState(MAXIMIZED_BOTH);
            }
            setTitle(window.getTitle());
        }
        else {
            System.out.println("ERROR. WMain. Datos de ventana nula.");
        }
        setLayout(new BorderLayout());
        
        setJMenuBar(new MnuMain(control));
        add(new TlbMain(control), BorderLayout.NORTH);
//        setContentPane(new PnlMain(control));
//        add(new PnlMain(), BorderLayout.CENTER);
        
//        pack();
        
        setLocationRelativeTo(null);
    }
    
    
}
