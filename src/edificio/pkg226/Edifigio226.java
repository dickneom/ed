/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edificio.pkg226;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppConfig;
import system.vMain.WMain;
import system.window.WindowDAO;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class Edifigio226 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppConfig.setDefault();
        try {
            AppConfig.load();
        } catch (IOException ex) {
            Logger.getLogger(Edifigio226.class.getName()).log(Level.SEVERE, null, ex);
        }
        AppConfig.print();
        
        
//        try {
//            AppConfig.save();
//        } catch (IOException ex) {
//            Logger.getLogger(Edifigio226.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        WindowData window = null;
        try {
            window = WindowDAO.getWindow("WMAIN");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Edifigio226.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        WMain wMain = new WMain(window);
        wMain.setVisible(true);
    }
    
}
