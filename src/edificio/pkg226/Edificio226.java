/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edificio.pkg226;

import console.DknConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppConfig;
import system.config.AppGlobal;
import system.config.ConfigData;
import system.vMain.WMain;
import system.window.WindowDAO;
import system.window.WindowData;

/**
 *
 * @author DickNeoM
 */
public class Edificio226 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppConfig.setDefault();
        try {
            AppConfig.load();
        } catch (IOException ex) {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "No se pudo cargar los datos de configuración, utilizando los datos por defecto");
            Logger.getLogger(Edificio226.class.getName()).log(Level.SEVERE, null, ex);
        }
        AppConfig.print();
        
//        try {
//            AppConfig.save();
//        } catch (IOException ex) {
//            Logger.getLogger(Edifigio226.class.getName()).log(Level.SEVERE, null, ex);
//        }

        AppGlobal.setLookAndFeel(AppConfig.getString(ConfigData.APP_LOOK_AND_FEEL));
        
        WindowData window = null;
        try {
            window = WindowDAO.getWindow("WMAIN");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Edificio226.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        WMain wMain = new WMain(window);
        wMain.setVisible(true);
    }
    
}
