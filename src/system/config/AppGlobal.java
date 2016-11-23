/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.config;

import DknTime.DateTime;
import console.DknConsole;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import system.company.CompanyDAO;

/**
 *
 * @author DickNeoM
 */
public class AppGlobal {
    public final static String APP_VERSION = "2.26";
    
    
    public final static String APP_CONFIG_INI_FULL_PATH = "config.ini"; // ya
    public final static String APP_TEXTS_BASE_PROPERTIES_FILE = "Texts"; // ya
    public final static String APP_TEXTS_BASE_PROPERTIES_PATH = "system.languaje"; // ya
    
    private static Locale appLocale; // ya
    private static ResourceBundle appTexts; // ya
    
    private static DecimalFormat decimalFormatInt; // formato para numeros enteros
    private static DecimalFormat decimalFormatLong; // formato para cuatro decimales
    private static DecimalFormat decimalFormatShort; // formato para dos decimales
    
    
    
    
    
    
    
    /**
     * Configura el directorio de trabajo inicial.
     */
    public static void setDirWorking() {
        if (AppConfig.getString(ConfigData.APP_DIR_WORK).isEmpty()) {
            AppConfig.set(ConfigData.APP_DIR_WORK, System.getProperty("user.dir"));
        }
//        
//        String os = System.getProperty("os.name");
//        if (os.equals("Linux")) {
//            AppConfig.APP_RUTA_DATOS = AppConfig.APP_RUTA_DATOS_LX;
//            AppConfig.APP_RUTA_REPORTES = AppConfig.APP_RUTA_REPORTES_LX;
//        }
//        else {
//            AppConfig.APP_RUTA_DATOS = AppConfig.APP_RUTA_DATOS_WN;
//            AppConfig.APP_RUTA_REPORTES = AppConfig.APP_RUTA_REPORTES_WN;
//        }
//        AppConfig.BDATOS_SISTEMA_RUTA_ARCHIVO = AppConfig.APP_RUTA_DATOS + AppConfig.BDATOS_SISTEMA_ARCHIVO;
//        AppConfig.BDATOS_EDIFICIO_RUTA_ARCHIVO = AppConfig.APP_RUTA_DATOS + AppConfig.BDATOS_EDIFICIO_ARCHIVO;
//        System.out.println("bdatos_sistema_ruta_archivo: " + AppConfig.BDATOS_SISTEMA_RUTA_ARCHIVO);
//        System.out.println("bdatos_edificio_ruta_archivo: " + AppConfig.BDATOS_EDIFICIO_RUTA_ARCHIVO);
    }
    
    public static void setDirWorking(String pathDirWorking) {
        AppConfig.set(ConfigData.APP_DIR_WORK, pathDirWorking);
    }
    
    public static String getDirWorking() {
        return AppConfig.getString(ConfigData.APP_DIR_WORK);
    }
    
    
    
    
    
    
    
    public static String getDataBase() {
        String dBase = AppConfig.getString(ConfigData.DBASE_DIR_DATA) + AppConfig.getString(ConfigData.DBASE_DATA_FILE);
        return dBase;
    }
    
    public static String getAppTitle() {
        String title = AppConfig.getString(ConfigData.APP_TITLE) + " " + APP_VERSION;
        return title;
    }
    
    public static Double getIva(Date date) {
        Date dateInitIVA14 = DateTime.getDate(2016, 6, 1);
        Date dateFinIVA14 = DateTime.getDate(2017, 5, 31);
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "Fecha: " + DateTime.getDateUtilToString(dateInitIVA14, getFormatDate()));
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "Fecha: " + DateTime.getDateUtilToString(dateFinIVA14, getFormatDate()));
        double iva;
        if (date.before(dateInitIVA14) || date.after(dateFinIVA14)) {
            iva = 12.0;
        }
        else {
            iva = 14.0;
        }
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "********** IVA: " + iva + " - Fecha: " + DateTime.getDateUtilToString(date, getFormatDate()));
        
        return iva;
    }
    
    
    
    
    
    
    /**
     * Configura los datos locales y los textos.
     */
    public static void setTexts() {
        appLocale = new Locale(AppConfig.getString(ConfigData.APP_LANGUAJE), AppConfig.getString(ConfigData.APP_COUNTRY));
        appTexts = ResourceBundle.getBundle(APP_TEXTS_BASE_PROPERTIES_PATH + "." + APP_TEXTS_BASE_PROPERTIES_FILE, appLocale);
    }

    public static Locale getLocal() {
        return appLocale;
    }

    /**
     * Devuelve todos los textos configurados.
     * @return 
     */
    public static ResourceBundle getTexts() {
        return appTexts;
    }
    
    /**
     * Recupera un texto dado su key.
     * @param key
     * @return 
     */
    public static String getText(String key) {
        String texto = null;
        
        if (appTexts.containsKey(key)) {
            texto = appTexts.getString(key);
        }

        if (texto == null) {
            texto = key;
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), " Texto no encontrado. Key: " + key);
        }
        
        return texto;
    }
    
    
    
    
    
    
    
    public static void setDecimalFormat(String formatStrLong, String formatStrShort, char decimalSeparator, String formatStrInt) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(decimalSeparator);
        decimalFormatLong = new DecimalFormat(formatStrLong, symbols);
        
        decimalFormatShort = new DecimalFormat(formatStrShort, symbols);
        
        decimalFormatInt = new DecimalFormat(formatStrInt, symbols);
    }
    
    public static DecimalFormat getFormatDecimalLong() {
        return decimalFormatLong;
    }
    
    public static DecimalFormat getFormatDecimalShort() {
        return decimalFormatShort;
    }
    
    public static DecimalFormat getFormatInteger() {
        return decimalFormatInt;
    }
    
    
    
    
    public static String getFormatDate() {
        return AppConfig.getString(ConfigData.FORMAT_DATE);
    }
    
    
    
    /**
     * Devuelve el último número de factura guardado.
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public static int getLastInvoiceNumber() throws ClassNotFoundException, SQLException {
        int number;
        
        number = CompanyDAO.get(1).getLastInvoiceNumber();
        
        return number;
    }
    
    /**
     * Devuelve el siguiente número de factura al que esta guardado.
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public static int getNextInvoiceNumber() throws ClassNotFoundException, SQLException {
        return getLastInvoiceNumber() + 1;
    }
    
    /**
     * Guarda el número de factura indicaco
     * @param number <code>int</code> con el número de factura a guardar
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void setLastInvoiceNumber(int number) throws ClassNotFoundException, SQLException {
        int rowsAffected = CompanyDAO.updateLastInvoiceNumber(1, number);
    }
    
    
    
    
    
    
    
    /**
     * Aplica el look and feel indicado al programa .
     * Si no se indica o no se lo encuentra, usara el que está por defecto en el sistema.
     * @param lookName String con el nombre del tema visual.
     */
    public static void setLookAndFeel(String lookName) {
        // Buscar la clase correspondiente al nombre look
        String className = null;
        if (lookName != null && !lookName.isEmpty()) {
            UIManager.LookAndFeelInfo[] lista = UIManager.getInstalledLookAndFeels();
            for (int i = 0; i < lista.length; i++) {
                if (lookName.equals(lista[i].getName())) {
                    className = lista[i].getClassName();
                    break;
                }
            }
        }
        
        // Aplicar el lookandfeel de acuerdo a la clase encontrada
        try {
            if (className == null) { // Si la clase es null se usa la del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                AppConfig.set(ConfigData.APP_LOOK_AND_FEEL, UIManager.getLookAndFeel().getName());
                
                System.out.println("LookAndFeel. Por defecto: " + AppConfig.getString(ConfigData.APP_LOOK_AND_FEEL));
            }
            else {
                UIManager.setLookAndFeel(className);
                System.out.println("LookAndFeel. " + lookName + " - " + className);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AppGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
