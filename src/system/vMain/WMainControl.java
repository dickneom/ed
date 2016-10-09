/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.vMain;

import console.DknConsole;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.view.WEditOptions;
import system.list.WList;
import system.window.WindowDAO;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class WMainControl {

    private final WMain wMain;

    public WMainControl(WMain wMain) {
        this.wMain = wMain;
    }
    
    
    public void exit() {
        wMain.dispose();
    }
    
    public void about() {
        DknConsole.msg("About. No implementado.");
    }
    
    public void options() {
        WindowData window = null;
        try {
            window = WindowDAO.getWindow("WOPTIONS");
            if (window != null) {
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Mostranco ventana código: OPTIONS");
                WEditOptions w = new WEditOptions(wMain, true, window);
                w.setVisible(true);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Codigo de ventana Opciones (OPTIONS). Ventana nula.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Hubo un error al cargar los datos de la ventana de Opciones (OPTIONS).");
            Logger.getLogger(WMainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    public void list(String code) {
        try {
            WindowData window = WindowDAO.getWindow(code);
            if (window != null) {
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Mostranco ventana código: " + code);
                WList w = new WList(wMain, window);
                w.setVisible(true);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Codigo de ventana no encontrado. Ventana nula. Código: " + code);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Hubo un error al cargar los datos de la ventana. Código: " + code);
            Logger.getLogger(WMainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    
    public void report(String code) {
        try {
            WindowData window = WindowDAO.getWindow(code);
            if (window != null) {
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Mostranco ventana código: " + code);
                WList w = new WList(wMain, window);
                w.setVisible(true);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Codigo de ventana no encontrado. Ventana nula. Código: " + code);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Hubo un error al cargar los datos de la ventana. Código: " + code);
            Logger.getLogger(WMainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
