/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.vMain;

import console.DknConsole;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.report.WClosingCash;
import system.config.view.WEditOptions;
import system.edit.WEdit;
import system.list.WList;
import system.window.WindowDAO;
import system.window.WindowData;

/**
 *
 * @author DickNeoM
 */
public class WMainControl implements ActionListener {

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
                if (window.getName().toUpperCase().equals("WREPORT_CLOSINGCASH")) {
                    WClosingCash w = new WClosingCash(wMain, false, window);
                    w.setVisible(true);
                }
                else {
                    WList w = new WList(wMain, window);
                    w.setVisible(true);
                }
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Código de ventana no encontrado. Ventana nula. Código: " + code);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Hubo un error al cargar los datos de la ventana. Código: " + code);
            Logger.getLogger(WMainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void edit(String code) {
        try {
            WindowData window = WindowDAO.getWindow(code);
            if (window != null) {
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Mostranco ventana código: " + code);
                WEdit w = new WEdit(wMain, window, code, -1);
                w.setVisible(true);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Código de la ventana no encontrado. Ventana nula. Código: " + code);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Hubo un error al cargar los datos de la ventana. Código: " + code);
            Logger.getLogger(WMainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comand = e.getActionCommand();
            
        String action;
        if (comand.startsWith("LIST")) {
            action = "LIST";
        }
        else if(comand.startsWith("REPORT")) {
            action = "REPORT";
        }
        else if(comand.startsWith("EDIT")) {
            action = "EDIT";
        }
        else {
            action = comand;
        }

        switch (action) {
            case "ABOUT":
                about();
                break;
            case "EXIT":
                exit();
                break;
            case "OPTIONS":
                options();
                break;
            case "LIST":
                list("W" + comand);
                break;
            case "REPORT":
                report("W" + comand);
                break;
            case "EDIT":
                edit("W" + comand);
                break;
            default:
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Comando no encontrado: " + comand);
                break;
        }
    }
}
