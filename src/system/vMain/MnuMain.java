/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.vMain;

import console.DknConsole;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import system.config.AppGlobal;

/**
 *
 * @author richneom
 */
public class MnuMain extends JMenuBar {

    private final WMainControl control;
    private final ListenerMenuMain listener;

    public MnuMain(WMainControl control) {
        this.control = control;
        this.listener = new ListenerMenuMain(control);
        
        // Menu Sistema
        JMenu mnuFile = crearMenu(AppGlobal.getText("WMAIN_MNU_SYSTEM_TEXT"), AppGlobal.getText("WMAIN_MNU_SYSTEM_TIP"));
            addItem(mnuFile, AppGlobal.getText("WMAIN_MNI_OPTIONS"), "", "OPTIONS");
            mnuFile.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuFile, AppGlobal.getText("WMAIN_MNI_QUIT"), "", "EXIT");
        add(mnuFile);
        
        JMenu mnuData = crearMenu(AppGlobal.getText("WMAIN_MNU_DATA_TEXT"), AppGlobal.getText("WMAIN_MNU_DATA_TIP"));
            addItem(mnuData, AppGlobal.getText("WMAIN_MNI_CUSTOMERS_TEXT"), AppGlobal.getText("WMAIN_MNI_CUSTOMERS_TIP"), "LIST_CUSTOMERS");
            addItem(mnuData, AppGlobal.getText("WMAIN_MNI_LOCAL_TEXT"), AppGlobal.getText("WMAIN_MNI_LOCAL_TIP"), "LIST_LOCAL");
            addItem(mnuData, AppGlobal.getText("WMAIN_MNI_CONTRACTS_TEXT"), AppGlobal.getText("WMAIN_MNI_CONTRACTS_TIP"), "LIST_CONTRACTS");
        add(mnuData);
        
        JMenu mnuInvoices = crearMenu(AppGlobal.getText("WMAIN_MNU_INVOICE_TEXT"), AppGlobal.getText("WMAIN_MNU_INVOICE_TIP"));
            addItem(mnuInvoices, AppGlobal.getText("WMAIN_MNI_INVOICE_TEXT"), AppGlobal.getText("WMAIN_MNI_INVOICE_TIP"), "LIST_INVOICES");
            mnuInvoices.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuInvoices, AppGlobal.getText("WMAIN_MNI_PAYMENTS_TEXT"), AppGlobal.getText("WMAIN_MNI_PAYMENTS_TIP"), "LIST_PAYMENTS");
            mnuInvoices.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuInvoices, AppGlobal.getText("WMAIN_MNI_ITEMS_TEXT"), AppGlobal.getText("WMAIN_MNI_ITEMS_TIP"), "LIST_ITEMS");
            addItem(mnuInvoices, AppGlobal.getText("WMAIN_MNI_ITEMSCATEGO_TEXT"), AppGlobal.getText("WMAIN_MNI_ITEMSCATEGO_TIP"), "LIST_ITEMSCATEGO");
        add(mnuInvoices);
        
        JMenu mnuBanks = crearMenu(AppGlobal.getText("WMAIN_MNU_BANKS_TEXT"), AppGlobal.getText("WMAIN_MNU_BANKS_TIP"));
            addItem(mnuBanks, AppGlobal.getText("WMAIN_MNI_BANKTRANS_TEXT"), AppGlobal.getText("WMAIN_MNI_BANKTRANS_TIP"), "LIST_BANKTRANS");
            mnuBanks.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuBanks, AppGlobal.getText("WMAIN_MNI_BANKACCOUNTS_TEXT"), AppGlobal.getText("WMAIN_MNI_BANKACCOUNTS_TIP"), "LIST_BANKACCOUNTS");
            mnuBanks.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuBanks, AppGlobal.getText("WMAIN_MNI_WITHHOLDINGRECEIVED_TEXT"), AppGlobal.getText("WMAIN_MNI_WITHHOLDINGRECEIVED_TIP"), "LIST_WITHHOLDINGRECEIVED");
        add(mnuBanks);
        
        JMenu mnuReports = crearMenu(AppGlobal.getText("WMAIN_MNU_REPORTS_TEXT"), AppGlobal.getText("WMAIN_MNU_REPORTS_TIP"));
            addItem(mnuReports, AppGlobal.getText("WMAIN_MNI_CLOSINGCASH_TEXT"), AppGlobal.getText("WMAIN_MNI_CLOSINGCASH_TIP"), "REPORT_CLOSINGCASH");
        add(mnuReports);
        
        JMenu mnuOthers = crearMenu(AppGlobal.getText("WMAIN_MNU_OTHERS_TEXT"), AppGlobal.getText("WMAIN_MNU_OTHERS_TIP"));
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_ENTESTYPES"), "Lista de Tipos de entes", "LIST_ENTESTYPES");
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_ENTESCIRCLES"), "Lista de Circulo de entes", "LIST_ENTESCIRCLES");
            mnuOthers.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_LOCALTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_LOCALTYPES_TIP"), "LIST_LOCALTYPES");
            mnuFile.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_ITEMSTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_ITEMSUNIT_TIP"), "LIST_ITEMSTYPES");
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_ITEMSUNIT_TEXT"), AppGlobal.getText("WMAIN_MNI_ITEMSUNIT_TIP"), "LIST_ITEMSUNIT");
            mnuOthers.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_INVOICESTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_INVOICESTYPES_TIP"), "LIST_INVOICESTYPES");
            mnuOthers.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_BANKTRANTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_BANKTRANTYPES_TIP"), "LIST_BANKTRANSTYPES");
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_BANKACCOUNTTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_BANKACCOUNTTYPES_TIP"), "LIST_BANKACCOUNTTYPES");
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_BANKS_TEXT"), AppGlobal.getText("WMAIN_MNI_BANKS_TIP"), "LIST_BANKS");
            mnuOthers.add(new JSeparator(JSeparator.HORIZONTAL));
            
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_PAYMENTMETHODS_TEXT"), AppGlobal.getText("WMAIN_MNI_PAYMENTMETHODS_TIP"), "LIST_PAYMENTMETHODS");
            mnuOthers.add(new JSeparator(JSeparator.HORIZONTAL));
            addItem(mnuOthers, AppGlobal.getText("WMAIN_MNI_TAXES_TEXT"), AppGlobal.getText("WMAIN_MNI_TAXES_TIP"), "LIST_TAXES");
        add(mnuOthers);
        
        JMenu mnuSystem = crearMenu(AppGlobal.getText("WMAIN_MNU_SYSTEM_TEXT"),AppGlobal.getText("WMAIN_MNU_SYSTEM_TIP"));
            addItem(mnuSystem, AppGlobal.getText("WMAIN_MNI_DNITYPES"), "Lista de tipos de DNI", "LIST_DNITYPES");
            addItem(mnuSystem, AppGlobal.getText("WMAIN_MNI_REGTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_REGTYPES_TIP"), "LIST_REGTYPES");
            addItem(mnuSystem, AppGlobal.getText("WMAIN_MNI_PAYMENTTYPES_TEXT"), AppGlobal.getText("WMAIN_MNI_PAYMENTTYPES_TIP"), "LIST_PAYMENTTYPES");
        add(mnuSystem);
        
        JMenu mnuGlobal = crearMenu(AppGlobal.getText("WMAIN_MNU_GLOBAL"), "");
            JMenu mnuEntesGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_ENTES"), "Listado y edición de Entes.");
                addItem(mnuEntesGlobal, AppGlobal.getText("WMAIN_MNI_CUSTOMERS"), "Lista clientes.", "LIST_CUSTOMERS");
                addItem(mnuEntesGlobal, AppGlobal.getText("WMAIN_MNI_ENTESTYPES"), "Lista de Tipos de entes", "LIST_ENTESTYPES");
                addItem(mnuEntesGlobal, AppGlobal.getText("WMAIN_MNI_ENTESCIRCLES"), "Lista de Circulo de entes", "LIST_ENTESCIRCLES");
                addItem(mnuEntesGlobal, AppGlobal.getText("WMAIN_MNI_DNITYPES"), "Lista de tipos de DNI", "LIST_DNITYPES");
            JMenu mnuLocalGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_LOCAL"), "Listado y edición de Locales.");
                addItem(mnuLocalGlobal, AppGlobal.getText("WMAIN_MNI_LOCAL"), "Lista de Locales.", "LIST_LOCAL");
                addItem(mnuLocalGlobal, AppGlobal.getText("WMAIN_MNI_LOCALTYPES"), "", "LIST_LOCALTYPES");
            JMenu mnuItemsGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_ITEMS"), "Listado y edición de Items.");
                addItem(mnuItemsGlobal, AppGlobal.getText("WMAIN_MNI_ITEMS"), "Lista de Items.", "LIST_ITEMS");
                addItem(mnuItemsGlobal, AppGlobal.getText("WMAIN_MNI_ITEMSTYPES"), "Lista de tipos de Items.", "LIST_ITEMSTYPES");
                addItem(mnuItemsGlobal, AppGlobal.getText("WMAIN_MNI_ITEMSUNIT"), "Lista de Unidades.", "LIST_ITEMSUNIT");
                addItem(mnuItemsGlobal, AppGlobal.getText("WMAIN_MNI_ITEMSCATEGO"), "Lista de Categorías.", "LIST_ITEMSCATEGO");
            JMenu mnuContractsGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_CONTRACTS"), "Listado y edición de Contratos.");
                addItem(mnuContractsGlobal, AppGlobal.getText("WMAIN_MNI_CONTRACTS"), "Lista de contratos.", "LIST_CONTRACTS");
            JMenu mnuInvoicesGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_INVOICES"), "Listado y edición de Facturas.");
                addItem(mnuInvoicesGlobal, AppGlobal.getText("WMAIN_MNI_INVOICES"), "Lista de facturas.", "LIST_INVOICES");
                addItem(mnuInvoicesGlobal, AppGlobal.getText("WMAIN_MNI_INVOICESTYPES"), "Lista de tipos de facturas.", "LIST_INVOICESTYPES");
            JMenu mnuBanksGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_BANKS"), "Listado y edición de Transacciones bancarias.");
                addItem(mnuBanksGlobal, AppGlobal.getText("WMAIN_MNI_BANKTRANS"), "Lista de transacciones bancarias.", "LIST_BANKTRANS");
                addItem(mnuBanksGlobal, AppGlobal.getText("WMAIN_MNI_BANKTRANTYPES"), "Lista de transacciones bancarias.", "LIST_BANKTRANSTYPES");
                addItem(mnuBanksGlobal, AppGlobal.getText("WMAIN_MNI_BANKACCOUNTS"), "Lista de cuentas bancarias.", "LIST_BANKACCOUNTS");
                addItem(mnuBanksGlobal, AppGlobal.getText("WMAIN_MNI_BANKACCOUNTTYPES"), "Lista de tipos de cuentas bancarias.", "LIST_BANKACCOUNTTYPES");
                addItem(mnuBanksGlobal, AppGlobal.getText("WMAIN_MNI_BANKS"), "Lista de Bancos.", "LIST_BANKS");
            JMenu mnuWithholdingsGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_WITHHOLDINGS"), "Listado y edición de Retenciones.");
                addItem(mnuWithholdingsGlobal, AppGlobal.getText("WMAIN_MNI_WITHHOLDINGRECEIVED"), "Lista de retenciones.", "LIST_WITHHOLDINGRECEIVED");
            JMenu mnuPaymentsGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_PAYMENTS"), "Listado y edición de pagos.");
                addItem(mnuPaymentsGlobal, AppGlobal.getText("WMAIN_MNI_PAYMENTS"), "Lista de pagos.", "LIST_PAYMENTS");
                addItem(mnuPaymentsGlobal, AppGlobal.getText("WMAIN_MNI_PAYMENTTYPES"), "Lista de tipos de pagos.", "LIST_PAYMENTTYPES");
                addItem(mnuPaymentsGlobal, AppGlobal.getText("WMAIN_MNI_PAYMENTMETHODS"), "Lista de métodos de pagos.", "LIST_PAYMENTMETHODS");
            JMenu mnuOthersGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_OTHERS"), "Listado y edición otras tablas.");
                addItem(mnuOthersGlobal, AppGlobal.getText("WMAIN_MNI_REGTYPES"), "Lista de Tipos de Registro.", "LIST_REGTYPES");
                addItem(mnuOthersGlobal, AppGlobal.getText("WMAIN_MNI_TAXES"), "Lista de tipos de Impuestos.", "LIST_TAXES");
            JMenu mnuSystemGlobal = addMenu(mnuGlobal, AppGlobal.getText("WMAIN_MNU_SYSTEM"), "Listado y edición de Contratos.");
                addItem(mnuSystemGlobal, AppGlobal.getText("WMAIN_MNI_WINDOWS"), "Lista de ventanas.", "LIST_WINDOWS");
        add(mnuGlobal);
        
        JMenu mnuHelp = crearMenu(AppGlobal.getText("WMAIN_MNU_HELP"), "");
            addItem(mnuHelp, AppGlobal.getText("WMAIN_MNI_ABOUT_TEXT"), AppGlobal.getText("WMAIN_MNI_ABOUT_TIP"), "ABOUT");
        add(mnuHelp);
    }
    
    private JMenu crearMenu(String text, String tip) {
        JMenu menu = new JMenu();
        menu.setText(text);
        if (tip != null && !tip.isEmpty()) {
            menu.setToolTipText(tip);
        }
        
        return menu;
    }
    
    private JMenu addMenu(JMenu menuParent, String text, String tip) {
        JMenu menu = new JMenu();
        menu.setText(text);
        if (tip != null && !tip.isEmpty()) {
            menu.setToolTipText(tip);
        }
        
        menuParent.add(menu);
        
        return menu;
    }
    
    /**
     * Agrega un JMenuItem a un JMenu.
     * @param menu
     * @param text
     * @param tip
     * @param comand
     * @return 
     */
    private JMenuItem addItem(JMenu menu, String text, String tip, String comand) {
        JMenuItem item = new JMenuItem();
        item.setText(text);
        if (tip != null && !tip.isEmpty()) {
            item.setToolTipText(tip);
        }
        item.setActionCommand(comand);
        
        item.addActionListener(listener);
        
        menu.add(item);
        
        return item;
    }
    
    
    
    
    
    /**
     * Acciones a tomar cuando se selecciona algun item del menu
     */
    private class ListenerMenuMain implements ActionListener {
        
        private final WMainControl control;

        public ListenerMenuMain(WMainControl control) {
            this.control = control;
        }
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String comand = e.getActionCommand();
//            System.out.println("Menu. Accion. Origen. " + e.getSource() + " Accion: " + comand);
            
            String action;
            if (comand.startsWith("LIST")) {
                action = "LIST";
            }
            else if(comand.startsWith("REPORT")) {
                action = "REPORT";
            }
            else {
                action = comand;
            }

            switch (action) {
                case "ABOUT":
                    control.about();
                    break;
                case "EXIT":
                    control.exit();
                    break;
                case "OPTIONS":
                    control.options();
                    break;
                case "LIST":
                    control.list("W" + comand);
                    break;
                case "REPORT":
                    control.report("W" + comand);
                    break;
                default:
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. Comando no encontrado: " + comand);
                    break;
            }
        }
        
    }
    
}
