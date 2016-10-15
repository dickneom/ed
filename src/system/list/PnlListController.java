/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.list;

import DknFile.Archivo;
import static DknFile.Archivo.obtenerArchivo;
import DknFile.ExcelDao;
import console.DknConsole;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import messages.VError;
import messages.VMessage;
import model.invoice.WShowInvoicePayments;
import system.config.AppGlobal;
import system.data.DAOSQL;
import system.edit.WEdit;
import system.edit.WImpExcel;
import system.window.WindowDAO;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class PnlListController implements ActionListener{

    private final WList wList;
    private final PnlList pnlList;
    private final WindowData window;

    public PnlListController(WList wList, PnlList pnlList, WindowData window) {
        this.wList = wList;
        this.pnlList = pnlList;
        this.window = window;
        
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Acciones de botones. Comando: " +com);
        switch (com.toUpperCase()) {
            case "INSERT":
                if (insert() > 0) {
                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Recargando datos");
                    pnlList.cargarDatos();
                };
                break;
            case "UPDATE":
                if (update() > 0) {
                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Recargando datos");
                    pnlList.cargarDatos();
                }
                break;
            case "DELETE":
                if (delete() > 0) {
                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Recargando datos");
                    pnlList.cargarDatos();
                }
                break;
            case "CANCEL":
                if (cancel() > 0) {
                    DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Recargando datos");
                    pnlList.cargarDatos();
                }
                break;
            case "SELECT":
                select();
                break;
            case "EXPEXCEL":
                exportExcel();
                break;
                
            case "INVOICE_PAY":
                invoicePay();
                break;
                
            case "INVOICE_PAYMENTS":
                invoicePayments();
                break;
                
            case "IMPEXCEL":
                if (importExcel()) {
                    pnlList.cargarDatos();
                }
                break;
                
                
                
                
            case "CLOSE":
                close();
                break;
        }
    }
    
    
    
    
    
    
    private int insert() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Insert");
        int rowsAffected = 0;
        
        int idSel = -1;
        String windowCode = window.getCode();
        windowCode = windowCode.replace("LIST", "EDIT");
        WindowData windowData = null;
        try {
            windowData = WindowDAO.getWindow(windowCode);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PnlList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (windowData != null) {
            DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Abriendo ventana. Código: " + windowCode);
            WEdit w = new WEdit(wList, windowData, window.getCode(), idSel);
            w.setVisible(true);
            rowsAffected = w.getRowsAffected();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_INSERT_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                if (w.isOk()) {
                    JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_ERROR_NOINSERT_TEXT"), AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Filas Afectadas: " + rowsAffected);
        
        return rowsAffected;
    }
    
    private int update() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Update");
        int rowsAffected = 0;
        
        int idSel = pnlList.getId();
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Registro actual. id: " + idSel);

        if (idSel > 0) {
            String windowCode = window.getCode().replace("LIST", "EDIT");
            WindowData windowData = null;
            try {
                windowData = WindowDAO.getWindow(windowCode);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(PnlList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (windowData != null) {
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Abriendo ventana. Código: " + windowCode);
                WEdit w = new WEdit(wList, windowData, window.getCode(), idSel);
                w.setVisible(true);
                rowsAffected = w.getRowsAffected();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_UPDATE_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    if (w.isOk()) {
                        JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_ERROR_NOUPDATE_TEXT"), AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), " Ventana no enconatrada. code: " + windowCode);
            }
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), " Ningún id seleccionado. id: " + idSel);
        }
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Filas Afectadas: " + rowsAffected);
        
        return rowsAffected;
    }
    
    private int delete() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Delete");
        int rowsAffected = 0;
        
        int idSel = pnlList.getId();
        
        if (idSel > 0) {
            int resp = JOptionPane.showConfirmDialog(wList, AppGlobal.getText("WLIST_MSG_CONFIRMDELETE_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    rowsAffected = DAOSQL.delete(window.getDataBase(), window.getTable(), idSel);

                    if (rowsAffected > 0) {
                        pnlList.cargarDatos();
                        JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_DELETE_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_ERROR_NODELETE_TEXT"), AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_ERROR_NODELETE_TEXT"), AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
                    Logger.getLogger(PnlList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), " Ningún id seleccionado. id: " + idSel);
        }
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Filas Afectadas: " + rowsAffected);
        
        return rowsAffected;
    }
    
    private int cancel() {
        int rowsAffected = 0;
        
        int idSel = pnlList.getId();

        if (idSel > 0) {
            // SE DEBE VALIDAR SI SE PUEDE ANULAR
            
            int resp = JOptionPane.showConfirmDialog(wList, AppGlobal.getText("WLIST_MSG_CONFIRMCANCEL_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    rowsAffected = DAOSQL.cancel(window.getDataBase(), window.getTable(), idSel);
                    
                    // SE DEBE LIBERAR EL DETALLE, ABONOS, Y OTROS.
                    
                    if (rowsAffected > 0) {
                        pnlList.cargarDatos();
                        JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_CANCEL_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_ERROR_NOCANCEL_TEXT"), AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(PnlList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), " Ningún id seleccionado. id: " + idSel);
        }
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Filas Afectadas: " + rowsAffected);
        
        return rowsAffected;
    }
    
    
    private void select() {
        pnlList.setIdSelected(pnlList.getId());
        
        if (pnlList.getIdSelected() > 0) {
            pnlList.setOk(true);
            DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Seleccionado: " + pnlList.getIdSelected());
            wList.dispose();
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), " ERROR. PnlList.select(). Ningun registro seleccionado.");
        }
    }
    
    private boolean exportExcel() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Exportando a excel.");
        
        File fileXls = obtenerArchivo(wList, ".xls", AppGlobal.getDirWorking(), Archivo.OpenMode.WRITE);
        
        if (fileXls != null) {
            AppGlobal.setDirWorking(Archivo.getRuta(fileXls));
            String sql = pnlList.getSql();

            try {
                int rowCount;
                try (Connection con = DAOSQL.getConection(window.getDataBase());
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql)) {
                    if (ExcelDao.exportResultSetToExcel(rs, fileXls.getAbsolutePath(), window.getTitle(), window.getTitle(), "")) {
                        VMessage.show(wList, "Exportado a: " + fileXls.getAbsolutePath());
                    }
                    else {
                        VError.show(wList, "No exportado");
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(WList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
    /**
     * Importa datos desde un archivo de excel
     * @return 
     */
    private boolean importExcel() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Importando a excel.");
        WImpExcel w = new WImpExcel(wList, null, window.getCode());
        
        w.setVisible(true);
        
        if (w.isOk()) {
            DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Importación exitosa.");
            return true;
        }
        else {
            DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Importación cancelada.");
        }
        return false;
    }
    
    
    private int invoicePay() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Generar pago a una factura.");
        int rowsAffected = 0;
        
        int idSel = pnlList.getId();
        if (idSel > 0) {
            String windowCode = "WEDIT_PAYMENTS";
            WindowData windowData = null;
            try {
                windowData = WindowDAO.getWindow(windowCode);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(PnlList.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (windowData != null) {
                DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Abriendo ventana. Código: " + windowCode + " idSel: " + idSel);
                WEdit w = new WEdit(wList, windowData, window.getCode(), -1, idSel);
                w.setVisible(true);
                rowsAffected = w.getRowsAffected();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_INSERT_TEXT"), AppGlobal.getText("WLIST_MSG_CONFIRMATION_TITLE"), JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    if (w.isOk()) {
                        JOptionPane.showMessageDialog(pnlList, AppGlobal.getText("WLIST_MSG_ERROR_NOINSERT_TEXT"), AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Filas Afectadas: " + rowsAffected);
        
        return rowsAffected;
    }
    
    private void invoicePayments() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Mostrar los pagos a una factura.");
        int idSel = pnlList.getId();
        if (idSel > 0) {
            WShowInvoicePayments w = new WShowInvoicePayments(wList, false, idSel);
            w.setVisible(true);
        }
    }
    
    
    private void close() {
        wList.dispose();
    }

}
