/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bank;

import DknFile.FileTxt;
import DknTime.DateTime;
import console.DknConsole;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.VMessage;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.payment.Payments;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import system.config.AppGlobal;
import system.data.DAOSQL;
import system.type.Type;
import system.type.TypeDAO;

/**
 *
 * @author richneom
 */
public class BankTransDAO extends DAOSQL {
    
    private static final String TABLE = "banktrans";
    private static final String[] FIELDS = {"id", "date", "idbankaccount", "idtype", "number", "value", "observations", "canceled", "idregtype", "type"};
    
    public static String validate(BankTrans bankTrans) {
        String error = null;
        
        return error;
    }
    
    
    
    public static int update(BankTrans bankTrans) throws ClassNotFoundException, SQLException {
        Object[] values = new Object[10];
        
        values[0] = bankTrans.getId();
        values[1] = bankTrans.getDate();
        values[2] = bankTrans.getIdBankAccount();
        values[3] = bankTrans.getIdType();
        values[4] = bankTrans.getNumber();
        values[5] = bankTrans.getValue();
        values[6] = bankTrans.getObservations();
        values[7] = (bankTrans.isCanceled()? 1 : 0);
        values[8] = bankTrans.getIdRegType();
        values[9] = bankTrans.getType();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, FIELDS, values);
        
        return rowsAffected;
    }
    
    
    
    
    
    
    
    public static BankTrans get(int id) throws ClassNotFoundException, SQLException, ParseException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, id);
        
        BankTrans bankTrans = null;
        if (map != null && map.size() > 0) {
            bankTrans = MapToBankTrans(map.getFirst());
        }
        
        return bankTrans;
    }
    
    public static BankTrans get(String code) throws ClassNotFoundException, SQLException, ParseException {
        return get("code", code);
    }
    
    public static BankTrans get(String field, String value) throws ClassNotFoundException, SQLException, ParseException {
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        
        BankTrans bankTrans = null;
        if (map != null && map.size() > 0) {
            bankTrans = MapToBankTrans(map.getFirst());
        }
        
        return bankTrans;
    }
    
    public static BankTranss gets(String fieldOrder) throws ClassNotFoundException, SQLException, ParseException {
        
        BankTranss bankTranss = ListMapToBankTranss(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return bankTranss;
    }
    
    
    
    
    
    
    
    public static BankTranss ListMapToBankTranss(LinkedList<HashMap<String, Object>> datos) throws ParseException {
        BankTranss bankTranss = new BankTranss();
        
        for (HashMap<String, Object> dato : datos) {
            bankTranss.add(MapToBankTrans(dato));
        }
        
        return bankTranss;
    }
    
    
    public static BankTrans MapToBankTrans(HashMap<String, Object> dato) throws ParseException {
        BankTrans bankTrans = new BankTrans();
        
        bankTrans.setId((int) dato.get("id"));
        String dateStr = (String) dato.get("date");
        if (dateStr != null && !dateStr.isEmpty())
            bankTrans.setDate(DateTime.getStringToDateUtil(dateStr , AppGlobal.getFormatDate()));
        
        bankTrans.setIdBankAccount((int) dato.get("idbankaccount"));
        bankTrans.setIdType((int) dato.get("idtype"));
        bankTrans.setNumber((String) dato.get("number"));
        bankTrans.setValue((double) dato.get("value"));
        bankTrans.setObservations((String) dato.get("observations"));
        bankTrans.setCanceled(((int) dato.get("canceled")) == 1);
        bankTrans.setIdRegType((int) dato.get("idregtype"));
        bankTrans.setType((String) dato.get("type"));
        
        return bankTrans;
    }
    
    
    
    
    
    /**
     * Devuelve la cantidad que ha sido utilizada en pagos.
     * @param bankTrans
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static double getUsed(BankTrans bankTrans) throws ClassNotFoundException, SQLException {
        Payments ps = PaymentDAO.getsBankTrans(bankTrans.getId());
        
        double sum = 0;
        if (ps != null) {
            for (Payment p : ps) {
                sum += p.getValue();
            }
        }
        
        return sum;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Metodo que permiete importar las transacciones o movimientos de una cuenta bancaria
     * desde un archivo de excel emitido por el Banco Internacional
     * @param idBankAccount numero de id de la cuenta a la que se importaran los datos
     * @param fileXLS <b>File</b> del archivo excel con los datos a importar
     * @return el numero de registros agregados
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static int importExcel(int idBankAccount, File fileXLS) throws ClassNotFoundException, SQLException {
        return importExcel(idBankAccount, fileXLS, false);
    }
    
    /**
     * Metodo que permiete importar las transacciones o movimientos de una cuenta bancaria
     * desde un archivo de excel emitido por el Banco Internacional
     * @param idBankAccount numero de id de la cuenta a la que se importaran los datos
     * @param fileXls <b>File</b> del archivo excel con los datos a importar
     * @param hacerArchivoLog
     * @return el numero de registros agregados
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static int importExcel(int idBankAccount, File fileXls, boolean hacerArchivoLog) throws ClassNotFoundException, SQLException {
        int rowsImported = 0;
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Importando datos desde: " + fileXls.getAbsolutePath() + " - a idcuenta: " + idBankAccount);
        if (idBankAccount > 0 && fileXls != null) {
            int numRegImportados = 0;
            FileTxt archLog = null;
            if (fileXls.exists()) {
//                if (hacerArchivoLog) {
//                    String nombreArchLog = fileXls.getAbsolutePath();
//                    nombreArchLog.replace(".xlsx", ".log");
//                    nombreArchLog.replace(".xls", ".log");
//                    archLog = new FileTxt(nombreArchLog);
//                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Importando desde Excel. Archivo: " + archLog.getFile().getAbsolutePath() + "*******************");
//                }
                try {
                    // crear un stream
                    POIFSFileSystem poiFS;
                    poiFS = new POIFSFileSystem(new FileInputStream(fileXls));
//                    if (hacerArchivoLog) {
//                        archLog.open(FileTxt.OpenMode.WRITE);
//                    }
                    
                    // crear una hoja de excel
                    HSSFWorkbook libro = new HSSFWorkbook(poiFS);
                    HSSFSheet sheet = libro.getSheetAt(0);
                    HSSFRow row;
                    HSSFCell cell;
                    
                    Iterator itr = sheet.rowIterator();
                    // extraer la informacion a un arrayList
                    int rowsCount = 0;
                    BankTrans trans = new BankTrans();
                    
                    while (itr.hasNext()) { // reviso fila por fila
                        row = (HSSFRow) itr.next();
                        
                        if (rowsCount >= 4) { // si la fila es la 4 o mayor importo los datos
                            Iterator itc = row.cellIterator();
                            
                            trans.setIdBankAccount(idBankAccount);
                            trans.setValue(0.0);
                            trans.setIdRegType(2); // <2> = registro importado
                            
                            int colCount = 0;
                            double value = 0;
                            while (itc.hasNext()) { // reviso celda por celda
                                cell = (HSSFCell) itc.next(); // leo la informacion de la celda
                                if (cell != null) { // si la celda no es nula
                                    switch (colCount) {
                                        case 0: // columna 0, nada
                                            break;
                                        case 1: // columna 1, fecha
                                            trans.setDate(DateTime.getStringToDateUtil(cell.getStringCellValue(), AppGlobal.getFormatDate()));
                                            break;
                                        case 2: // columna 2, tipo de transaccion
                                            String codTipo = cell.getStringCellValue().trim();
                                            trans.setType(codTipo);
                                                
                                            Type btt = TypeDAO.get("banktranstypes", codTipo);
                                            if (btt != null) {
                                                trans.setIdType(btt.getId());
                                            }
                                            else {
                                                String texto = "Tipo de transanccion bancaria no encontrada: " + codTipo + ". Creandola";
                                                DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), texto);
                                                VMessage.show(texto);

                                                btt = new Type();
                                                btt.setCode(codTipo);
                                                btt.setName(codTipo);
                                                btt.setDescription(codTipo);
                                                btt.setActive(true);

                                                if (TypeDAO.update("banktranstypes", btt) > 0 ) {
                                                    Type btt1 = TypeDAO.get("banktranstypes", codTipo);
                                                    if (btt1 != null) {
                                                        trans.setIdType(btt1.getId());
                                                        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Creada y utilizando el Tipo de transacción bancaria id: " + btt1.getId() + " trans.id: " + trans.getIdType());
                                                    }
                                                }
                                                else {
                                                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Tipo de transacción bancaria No se pudo crear. Código: " + codTipo);
                                                }
                                            }
                                            break;
                                        case 3: // columna 3, numero de transacción
                                            trans.setNumber(cell.getStringCellValue().trim());
                                            break;
                                        case 4: // columna 4, nada
                                            
                                            break;
                                        case 5: // columna 5, observaciones
                                            trans.setObservations(cell.getStringCellValue().trim());
                                            break;
                                        case 6: // columna 6, nada
                                            
                                            break;
                                        case 7: // columna 7, valor debito
                                            if (cell.getNumericCellValue() > 0) {
                                                value = cell.getNumericCellValue();
                                                value *= -1;
                                                trans.setValue(value);
                                            }
                                            break;
                                        case 8: // columna 8, valor credito
                                            if (cell.getNumericCellValue() > 0) {
                                                value = cell.getNumericCellValue();
                                                trans.setValue(value);
                                            }
                                            break;
                                        default:
                                            break;
                                    } // fin del switch
                                } // Fin celda nula
                                colCount++;
                            } // Fin while de celdas
                            
//                            System.out.println("Agregando: " + trans);
                            String error = BankTransDAO.validate(trans);
                            if (error == null) {
                                if (BankTransDAO.update(trans) >= 0) {
                                    numRegImportados++;
                                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Agregado: " + trans.getNumber());
                                    if (hacerArchivoLog) {
                                        archLog.writeLine("Agregado: " + trans);
                                    }
                                }
                                else {
                                    DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "NO agregado: " + trans.getNumber());
                                    if (hacerArchivoLog) {
                                        archLog.writeLine("NO agregado: " + trans);
                                    }
                                }
                            }
                            else {
                                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "NO paso la validacion: " + error + " - " + trans.getNumber());
                                if (hacerArchivoLog) {
                                    archLog.writeLine("NO paso la validacion: " + error + " - " + trans);
                                }
                            }
                        }
                        rowsCount++;
                    } // Fin while de filas
                    rowsImported = numRegImportados;
                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Importacion completa. Total " + numRegImportados + " registros importados.");
                // grabar los datos en la base de datos
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BankTransDAO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(BankTransDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (hacerArchivoLog) {
                        try {
                            archLog.close();
                        } catch (IOException ex) {
                            Logger.getLogger(BankTransDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
//                JOptionPane.showMessageDialog(this, AppConfig.IMPORTACION_TEXTO, AppConfig.IMPORTACION_TITULO, JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Archivo no existe.");
            }
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Archivo o cuenta no indicadas.");
        }
        
        return rowsImported;
    }
    
}
