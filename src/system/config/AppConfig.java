/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.config;

import console.DknConsole;
import DknFile.FileTxt;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DickNeoM
 */
public class AppConfig {
    
    private static Map<ConfigData, Object> config;
    
    public static void setDefault() {
        config = new HashMap<>();
        
        for (ConfigData g: ConfigData.values()) {
            switch (g.getType()) {
                case "String":
                    String valorS = g.getValue();
                    set(g,valorS);
                    break;
                case "Integer":
                    int valorI = Integer.parseInt(g.getValue());
                    set(g,valorI);
                    break;
                case "Double":
                    double valorD = Double.parseDouble(g.getValue());
                    set(g,valorD);
                    break;
                case "Boolean":
                    boolean valorB = Boolean.parseBoolean(g.getValue());
                    set(g,valorB);
                    break;
                default:
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Tipo no reconocido: " + g.getType());
                    break;
            }
        }
        
        AppGlobal.setDirWorking();
        AppGlobal.setTexts();
        AppGlobal.setDecimalFormat(AppConfig.getString(ConfigData.FORMAT_NUMBER_DECIMAL_LONG), AppConfig.getString(ConfigData.FORMAT_NUMBER_DECIMAL_SHORT), AppConfig.getChar(ConfigData.FORMAT_SEPARATOR_DECIMAL), AppConfig.getString(ConfigData.FORMAT_NUMBER_INT));
    }
    
    /**
     * Carga los datos de configuracion desde un archivo de texto.
     * @throws IOException 
     */
    public static void load() throws IOException {
//        AppConfig.historial(Thread.currentThread().getStackTrace()[1].toString(), Debug.NivelDebug.NIVEL_AMARILLO, Log.NivelLog.NIVEL_AMARILLO);
        FileTxt fileTxt = new FileTxt(AppGlobal.APP_CONFIG_INI_FULL_PATH);
        DknConsole.msg("Leyendo archivo: " + fileTxt.getFile().getAbsolutePath() + "...");
        if (fileTxt.exist()) {
            fileTxt.open(FileTxt.OpenMode.READ);
            
            String linea = fileTxt.readLine();
            int pos;
            String variable;
            while (linea != null && !linea.isEmpty()) {
//                System.out.println(linea);
                pos = linea.indexOf("=");
                variable = linea.substring(0, pos);
                for (ConfigData g : ConfigData.values()) {
//                    System.out.println(variable);
                    if (variable.equals(g.getCode())) {
                        linea = linea.substring(pos+1);
//                        System.out.println(variable + "-" + linea);
                        switch (g.getType()) {
                            case "String":
                                String valorS = linea;
                                set(g,valorS);
                                break;
                            case "Integer":
                                int valorI = Integer.parseInt(linea);
                                set(g,valorI);
                                break;
                            case "Double":
                                double valorD = Double.parseDouble(linea);
                                set(g,valorD);
                                break;
                            case "Boolean":
                                boolean valorB = Boolean.parseBoolean(linea);
                                set(g,valorB);
                                break;
                            default:
                                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Tipo no reconocido: " + g.getType());
                                break;
                        }
                        break;
                    }
                }
                
                linea = fileTxt.readLine();
            }
            
            fileTxt.close();
            
            AppGlobal.setDirWorking();
            AppGlobal.setDecimalFormat(AppConfig.getString(ConfigData.FORMAT_NUMBER_DECIMAL_LONG), AppConfig.getString(ConfigData.FORMAT_NUMBER_DECIMAL_SHORT), AppConfig.getChar(ConfigData.FORMAT_SEPARATOR_DECIMAL), AppConfig.getString(ConfigData.FORMAT_NUMBER_INT));
            AppGlobal.setTexts();
            
            DknConsole.msg(" Hecho.");
        }
    }
    
    /**
     * Graba los datos de configuracion en un archivo de texto.
     * @throws IOException 
     */
    public static void save() throws IOException {
//        AppConfig.historial(Thread.currentThread().getStackTrace()[1].toString(), Debug.NivelDebug.NIVEL_AMARILLO, Log.NivelLog.NIVEL_AMARILLO);
        FileTxt fileTxt = new FileTxt(AppGlobal.APP_CONFIG_INI_FULL_PATH);
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Grabando archivo: " + fileTxt.getFile().getAbsolutePath() + "...");
        fileTxt.open(FileTxt.OpenMode.WRITE);
        
        for (ConfigData g : ConfigData.values()) {
            String variable = g.getCode();
            String valor = String.valueOf(getObject(g));
            
            String texto = variable + "=" + valor;
            
            fileTxt.writeLine(texto);
        }
        
        fileTxt.close();
        DknConsole.msg(" Hecho.");
    }
    
    /**
     * Imprime los datos de configuracion
     */
    public static void print() {
        for (ConfigData g : ConfigData.values()) {
            System.out.println(g + " = " + String.valueOf(get(g)));
        }
    }
    
    
    
    
    
    
    /**
     * Agrega un valor de configuracion al Map.
     * @param key
     * @param valor 
     */
    public static void set(ConfigData key, Object valor) {
        //System.out.println(key + ", " + valor);
        if (config == null) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No inicializado config. Falta utilizar AppConfig.setDefault");
        }
        config.put(key, valor);
    }
    
    /**
     * Resupera un valor con tipo Object.
     * @param key
     * @return 
     */
    public static Object get(ConfigData key) {
        if (config == null) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No inicializado config. Falta utilizar AppConfig.setDefault");
        }
        return config.get(key);
    }
    
    public static String getString(ConfigData key) {
        return (String) config.get(key);
    }    

    public static double getDouble(ConfigData key) {
        return (double) config.get(key);
    }
    
    public static int getInt(ConfigData key) {
        return (int) config.get(key);
    }
    
    public static boolean getBoolean(ConfigData key) {
        return (boolean) config.get(key);
    }
    
    public static char getChar(ConfigData key) {
        return ((String) config.get(key)).charAt(0);
    }
    
    /**
     * Resupera un valor con tipo Object. Igual a get(Config key).
     * @param key
     * @return 
     */
    public static Object getObject(ConfigData key) {
        return config.get(key);
    }
}
