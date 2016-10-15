/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.edit;

import console.DknConsole;
import javax.swing.JDialog;
import javax.swing.JFrame;
import model.bank.PnlEditBankAccount;
import model.bank.PnlEditBankTrans;
import model.contract.PnlEditContract;
import model.ente.PnlEditEnte;
import model.invoice.PnlEditInvoice;
import model.item.PnlEditItem;
import model.local.PnlEditLocal;
import model.other.PnlEditTaxes;
import model.payment.PnlEditPayment;
import model.withholding.PnlEditWithholdingReceived;
import system.type.PnlEditType;
import system.window.PnlEditWindow;
import system.window.WindowData;

/**
 *
 * @author DickNeoM
 */
public class WEdit extends JDialog {

    private final WindowData window;
    private PnlEdit pnlEdit;
    private int idSel;
    
    /**
     * Muestra una ventana para edicion de registros.<br>
     * Dependiendo de los datos de la ventana se mostrará un panel de edicion diferente.
     * @param owner ventana desde la que se llama.
     * @param window datos de la ventana actual
     * @param parentCode Actualmente no usado
     * @param id id del registro a modificarse, -1 para crear un nuevo registro
     */
    public WEdit(JDialog owner, WindowData window, String parentCode, int id) {
        super(owner, window.isModal());
        this.window = window;
        
        init(id);
        
        pack();
        setLocationRelativeTo(owner);
    }
    
    /**
     * Muestra una ventana para edicion de registros.<br>
     * Dependiendo de los datos de la ventana se mostrará un panel de edicion diferente.
     * @param owner ventana desde la que se llama.
     * @param window datos de la ventana actual
     * @param parentCode Actualmente no usado
     * @param id id del registro a modificarse, -1 para crear un nuevo registro
     */
    public WEdit(JFrame owner, WindowData window, String parentCode, int id) {
        super(owner, window.isModal());
        this.window = window;
        
        init(id);
        
        pack();
        setLocationRelativeTo(owner);
    }
    
    
    /**
     * Muestra una ventana para edicion de registros.<br>
     * Dependiendo de los datos de la ventana se mostrará un panel de edicion diferente.
     * @param owner ventana desde la que se llama.
     * @param window datos de la ventana actual
     * @param parentCode Actualmente no usado
     * @param id id del registro a modificarse, -1 para crear un nuevo registro
     * @param idSel en caso de crear un registro que esta vinclulado con otro registro se puede llamar desde ese registro, este dato es el id del otro registro.
     */
    public WEdit(JDialog owner, WindowData window, String parentCode, int id, int idSel) {
        super(owner, window.isModal());
        this.window = window;
        this.idSel = idSel;
        
        init(id);
        
        pack();
        setLocationRelativeTo(owner);
    }
    
    
    
    private void init(int id) {
        if (window != null) {
            if (window.getWidth() > 0 && window.getHeight() > 0) {
                setSize(window.getWidth(), window.getHeight());
            }
            
            setTitle(window.getTitle());
            setResizable(false);
            
            switch (window.getCode()) {
                // Para editar datos TYPE
                case "WEDIT_BANKS":
                case "WEDIT_BANKACCOUNTTYPES":
                case "WEDIT_BANKTRANSTYPES":
                case "WEDIT_DNITYPES":
                case "WEDIT_ENTESCIRCLES":
                case "WEDIT_ENTESTYPES":
                case "WEDIT_INVOICESTYPES":
                case "WEDIT_ITEMSCATEGO":
                case "WEDIT_ITEMSTYPES":
                case "WEDIT_ITEMSUNIT":
                case "WEDIT_LOCALTYPES":
                case "WEDIT_PAYMENTTYPES":
                case "WEDIT_PAYMENTMETHODS":
                case "WEDIT_REGTYPES":
                    pnlEdit = new PnlEditType(this, this.window, id);
                    break;
                case "WEDIT_TAXES":
                    pnlEdit = new PnlEditTaxes(this, id);
                    break;
                case "WEDIT_BANKACCOUNTS":
                    pnlEdit = new PnlEditBankAccount(this, id);
                    break;
                case "WEDIT_BANKTRANS":
                    pnlEdit = new PnlEditBankTrans(this, id);
                    break;
                case "WEDIT_CONTRACTS":
                    pnlEdit = new PnlEditContract(this, id);
                    break;
                case "WEDIT_CUSTOMERS":
                    pnlEdit = new PnlEditEnte(this, id);
                    break;
                case "WEDIT_INVOICES":
                    pnlEdit = new PnlEditInvoice(this, id);
                    break;
                case "WEDIT_ITEMS":
                    pnlEdit = new PnlEditItem(this, id);
                    break;
                case "WEDIT_LOCAL":
                    pnlEdit = new PnlEditLocal(this, id);
                    break;
                case "WEDIT_PAYMENTS":
                    pnlEdit = new PnlEditPayment(this, id, idSel);
                    break;
                case "WEDIT_WITHHOLDINGRECEIVED":
                    pnlEdit = new PnlEditWithholdingReceived(this, id);
                    break;
                case "WEDIT_WINDOWS":
                    pnlEdit = new PnlEditWindow(this, id);
                    break;
                default:
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Código del PANEL no encontrado. Código: " + window.getCode());
                    break;
            }
            
            if (pnlEdit != null) {
                setContentPane(pnlEdit);
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Panel nulo. Código: " + window.getCode());
            }
        }
    }

    public int getRowsAffected() {
        if (pnlEdit != null) {
            return pnlEdit.getRowsAffected();
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Panel nulo. Código: " + window.getCode());
            return -1;
        }
    }
    
    public boolean isOk() {
        if (pnlEdit != null) {
            return pnlEdit.isOk();
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Panel nulo. Código: " + window.getCode());
            return false;
        }
    }
}
