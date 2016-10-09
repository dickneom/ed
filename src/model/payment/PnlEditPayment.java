/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.payment;

import model.contract.*;
import console.DknConsole;
import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import messages.VError;
import messages.VMessage;
import model.bank.BankTrans;
import model.bank.BankTransDAO;
import model.ente.Ente;
import model.ente.EnteDAO;
import model.invoice.Invoice;
import model.invoice.InvoiceDAO;
import model.withholding.WithholdingReceived;
import model.withholding.WithholdingReceivedDAO;
import system.config.AppConfig;
import system.config.AppGlobal;
import system.config.ConfigData;
import system.edit.PnlEdit;
import system.edit.WEdit;
import system.list.WList;
import system.type.Type;
import system.type.TypeDAO;
import system.type.Types;
import system.window.WindowDAO;
import system.window.WindowData;


/**
 *
 * @author DickNeoM
 */
public class PnlEditPayment extends PnlEdit {

    private final WEdit wEdit;
    private final int id;
    private Payment payment;
    private double payValueRecorded;

    /**
     * Creates new form PnlEditBank
     * @param wEdit
     * @param id
     */
    public PnlEditPayment(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
        
        showData();
    }
    
    @Override
    protected final void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_PAYMENTS_CODE_TEXT") + ":");
        lblDate.setText(AppGlobal.getText("WEDIT_PAYMENTS_DATE_TEXT") + ":");
        lblType.setText(AppGlobal.getText("WEDIT_PAYMENTS_TYPE_TEXT") + ":");
        lblInvoice.setText(AppGlobal.getText("WEDIT_PAYMENTS_INVOICENUMBER_TEXT") + ":");
        lblInvoiceDate.setText(AppGlobal.getText("WEDIT_PAYMENTS_INVOICEDATE_TEXT") + ":");
        lblInvoiceName.setText(AppGlobal.getText("WEDIT_PAYMENTS_INVOICECUSTOMER_TEXT") + ":");
        lblInvoiceTotal.setText(AppGlobal.getText("WEDIT_PAYMENTS_INVOICETOTAL_TEXT") + ":");
        lblInvoicePayment.setText(AppGlobal.getText("WEDIT_PAYMENTS_INVOICEPAYMENT_TEXT") + ":");
        lblInvoiceBalance.setText(AppGlobal.getText("WEDIT_PAYMENTS_INVOICEBALANCE_TEXT") + ":");
        lblPaymentMethod.setText(AppGlobal.getText("WEDIT_PAYMENTS_PAYMENTMETHOD_TEXT") + ":");
        lblPayment.setText(AppGlobal.getText("WEDIT_PAYMENTS_PAYMENT_TEXT") + ":");
        lblPaymentDate.setText(AppGlobal.getText("WEDIT_PAYMENTS_PAYMENTDATE_TEXT") + ":");
        lblPaymentValue.setText(AppGlobal.getText("WEDIT_PAYMENTS_PAYMENTVALUE_TEXT") + ":");
        lblPaymentUsed.setText(AppGlobal.getText("WEDIT_PAYMENTS_PAYMENTUSED_TEXT") + ":");
        lblPaymentBalance.setText(AppGlobal.getText("WEDIT_PAYMENTS_PAYMENTBALANCE_TEXT") + ":");
        lblValue.setText(AppGlobal.getText("WEDIT_PAYMENTS_VALUE_TEXT") + ":");
        lblFinalBalance.setText(AppGlobal.getText("WEDIT_PÁYMENTS_FINALBALANCE_TEXT") + ":");
        lblObservations.setText(AppGlobal.getText("WEDIT_PAYMENTS_OBSERVATIONS_TEXT") + ":");
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
        
        cmbType.removeAllItems();
        try {
            Types paymentTypes = TypeDAO.gets("paymenttypes", "name");
            
            if (paymentTypes != null) {
                for (Type paymentType : paymentTypes) {
                    cmbType.addItem(paymentType.getName());
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cmbPaymentMethod.removeAllItems();
        try {
            Types paymentMethods = TypeDAO.gets("paymentmethods", "name");
            
            if (paymentMethods != null) {
                for (Type paymentMethod : paymentMethods) {
                    cmbPaymentMethod.addItem(paymentMethod.getName());
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jdcDate.setDateFormatString(AppGlobal.getFormatDate());
    }
    
    @Override
    protected final void loadData() {
        if (id < 0) {
            payment = new Payment();
            payment.setDate(DateTime.getNow());
            
            payValueRecorded = 0;
            
            changeType();
            changeMethod();
        }
        else {
            try {
                payment = PaymentDAO.get(id);
                
                payValueRecorded = payment.getValue();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected final void showData() {
        lblId.setText(String.valueOf(id));

        if (payment == null) {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "El pago es nulo.");
        }
        else {
            txtCode.setText(payment.getCode());
            jdcDate.setDate(payment.getDate());
            
            try {
                Type payType = TypeDAO.get("paymenttypes", payment.getIdType());
                if (payType != null) {
                    cmbType.setSelectedItem(payType.getName());
                    
                    showInvoice();
                }
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                Type payMethod = TypeDAO.get("paymentmethods", payment.getIdPaymentMethod());
                if (payMethod != null) {
                    cmbPaymentMethod.setSelectedItem(payMethod.getName());
                    
                    showPay();
                }
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            txtPayValue.setText(AppGlobal.getFormatDecimalShort().format(payment.getValue()));
            txtObservations.setText(payment.getObservations());
            
            double invoiceBalance = 0;
            if (!txtInvoiceBalance.getText().isEmpty())
                invoiceBalance = Double.parseDouble(txtInvoiceBalance.getText());
            
            txtFinalBalance.setText(AppGlobal.getFormatDecimalShort().format(invoiceBalance - payment.getValue()));
        }
    }
    
    private void showInvoice() throws ClassNotFoundException, SQLException, ParseException {
        switch (payment.getIdType()) {
            case 1:
                Invoice invoice = InvoiceDAO.get(payment.getIdInvoice());
                showDataInvoice(invoice);
                break;
            case 2:
                Contract contract = ContractDAO.get(payment.getIdInvoice());
                showDataContract(contract);
                break;
            default:
                break;
        }
    }

    private void showPay() throws ClassNotFoundException, SQLException, ParseException {
        switch (payment.getIdPaymentMethod()) {
            case 5:
            case 8:
                BankTrans bankTrans = BankTransDAO.get(payment.getIdPaymentDoc());
                showDataBankTrans(bankTrans);
                break;
            case 4:
                WithholdingReceived wr = WithholdingReceivedDAO.get(payment.getIdPaymentDoc());
                showDataWithholding(wr);
                break;
            default:
                showDataBankTrans(null);
                DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Metodo de pago no definido.");
                break;
        }
    }
    
    private void showDataInvoice(Invoice invoice) throws ClassNotFoundException, SQLException, ParseException {
        if (invoice != null) {
            txtInvoice.setText(invoice.getFullNumber(AppConfig.getInt(ConfigData.INVOICES_NUMBER_TAM)));
            txtInvoiceDate.setText(DateTime.getDateUtilToString(invoice.getDate(), AppGlobal.getFormatDate()));
            Contract contract = ContractDAO.get(invoice.getIdContract());
            if (contract != null) {
                Ente ente = EnteDAO.get(contract.getIdCustomer());
                if (ente != null) {
                    txtInvoiceName.setText(ente.getFullName());
                }
            }
            double total = InvoiceDAO.getTotal(invoice);

            double pay = InvoiceDAO.getPayment(invoice) - payValueRecorded;
            txtInvoiceTotal.setText(AppGlobal.getFormatDecimalShort().format(total));
            txtInvoicePayment.setText(AppGlobal.getFormatDecimalShort().format(pay));
            txtInvoiceBalance.setText(AppGlobal.getFormatDecimalShort().format(total - pay));
        }
        else {
            txtInvoice.setText("");
            txtInvoiceDate.setText("");
            txtInvoiceName.setText("");
            txtInvoiceTotal.setText("");
            txtInvoicePayment.setText("");
            txtInvoiceBalance.setText("");
        }
    }
    
    private void showDataContract(Contract contract) throws ClassNotFoundException, SQLException {
        if (contract != null) {
            txtInvoice.setText(contract.getCode());
            txtInvoiceDate.setText(DateTime.getDateUtilToString(contract.getDate(), AppGlobal.getFormatDate()));
            Ente ente = EnteDAO.get(contract.getIdCustomer());
            if (ente != null) {
                txtInvoiceName.setText(ente.getFullName());
            }
            double total = contract.getWarranty();

            double pay = ContractDAO.getPayment(contract) - payValueRecorded;
            txtInvoiceTotal.setText(AppGlobal.getFormatDecimalShort().format(total));
            txtInvoicePayment.setText(AppGlobal.getFormatDecimalShort().format(pay));
            txtInvoiceBalance.setText(AppGlobal.getFormatDecimalShort().format(total - pay));
        }
        else {
            txtInvoice.setText("");
            txtInvoiceDate.setText("");
            txtInvoiceName.setText("");
            txtInvoiceTotal.setText("");
            txtInvoicePayment.setText("");
            txtInvoiceBalance.setText("");
        }
    }
    
    private void showDataBankTrans(BankTrans bankTrans) throws ClassNotFoundException, SQLException {
        if (bankTrans != null) {
            txtPayment.setText(bankTrans.getNumber());
            txtPaymentDate.setText(DateTime.getDateUtilToString(bankTrans.getDate(), AppGlobal.getFormatDate()));
            double total = bankTrans.getValue();

            double used = BankTransDAO.getUsed(bankTrans) - payValueRecorded;
            txtPaymentValue.setText(AppGlobal.getFormatDecimalShort().format(total));
            txtPaymentUsed.setText(AppGlobal.getFormatDecimalShort().format(used));
            txtPaymentBalance.setText(AppGlobal.getFormatDecimalShort().format(total - used));
        }
        else {
            txtPayment.setText("");
            txtPaymentDate.setText("");
            txtPaymentValue.setText("");
            txtPaymentUsed.setText("");
            txtPaymentBalance.setText("");
        }
    }
    
    private void showDataWithholding(WithholdingReceived wr) throws ClassNotFoundException, SQLException {
        if (wr != null) {
            txtPayment.setText(wr.getFullNumber());
            txtPaymentDate.setText(DateTime.getDateUtilToString(wr.getDate(), AppGlobal.getFormatDate()));
            double total = wr.getValue();

            double used = WithholdingReceivedDAO.getUsed(wr) - payValueRecorded;
            txtPaymentValue.setText(AppGlobal.getFormatDecimalShort().format(total));
            txtPaymentUsed.setText(AppGlobal.getFormatDecimalShort().format(used));
            txtPaymentBalance.setText(AppGlobal.getFormatDecimalShort().format(total - used));
        }
        else {
            txtPayment.setText("");
            txtPaymentDate.setText("");
            txtPaymentValue.setText("");
            txtPaymentUsed.setText("");
            txtPaymentBalance.setText("");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        lblCode = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        lblValue = new javax.swing.JLabel();
        txtPayValue = new javax.swing.JTextField();
        lblObservations = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jdcDate = new com.toedter.calendar.JDateChooser();
        txtObservations = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblInvoice = new javax.swing.JLabel();
        txtInvoice = new javax.swing.JTextField();
        btnInvoice = new javax.swing.JButton();
        lblInvoiceName = new javax.swing.JLabel();
        txtInvoiceName = new javax.swing.JTextField();
        lblInvoiceTotal = new javax.swing.JLabel();
        cmbType = new javax.swing.JComboBox<>();
        lblType = new javax.swing.JLabel();
        txtInvoiceTotal = new javax.swing.JTextField();
        lblInvoicePayment = new javax.swing.JLabel();
        txtInvoicePayment = new javax.swing.JTextField();
        lblInvoiceBalance = new javax.swing.JLabel();
        txtInvoiceBalance = new javax.swing.JTextField();
        lblInvoiceDate = new javax.swing.JLabel();
        txtInvoiceDate = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblPaymentMethod = new javax.swing.JLabel();
        cmbPaymentMethod = new javax.swing.JComboBox<>();
        lblPayment = new javax.swing.JLabel();
        lblPaymentDate = new javax.swing.JLabel();
        txtPaymentDate = new javax.swing.JTextField();
        txtPayment = new javax.swing.JTextField();
        btnPaymenDoc = new javax.swing.JButton();
        lblPaymentValue = new javax.swing.JLabel();
        txtPaymentValue = new javax.swing.JTextField();
        lblPaymentBalance = new javax.swing.JLabel();
        txtPaymentBalance = new javax.swing.JTextField();
        lblPaymentUsed = new javax.swing.JLabel();
        txtPaymentUsed = new javax.swing.JTextField();
        txtFinalBalance = new javax.swing.JTextField();
        lblFinalBalance = new javax.swing.JLabel();

        btnCancel.setText("jButton1");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOk.setText("jButton2");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        lblCode.setText("jLabel1");

        lblId.setText("jLabel2");

        lblValue.setText("jLabel1");

        txtPayValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPayValue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPayValueFocusLost(evt);
            }
        });
        txtPayValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPayValueActionPerformed(evt);
            }
        });

        lblObservations.setText("jLabel1");

        lblDate.setText("jLabel1");

        lblInvoice.setText("jLabel2");

        txtInvoice.setEditable(false);

        btnInvoice.setText("...");
        btnInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceActionPerformed(evt);
            }
        });

        lblInvoiceName.setText("jLabel1");

        txtInvoiceName.setEditable(false);

        lblInvoiceTotal.setText("jLabel1");

        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypeActionPerformed(evt);
            }
        });

        lblType.setText("jLabel1");

        txtInvoiceTotal.setEditable(false);
        txtInvoiceTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblInvoicePayment.setText("jLabel7");

        txtInvoicePayment.setEditable(false);
        txtInvoicePayment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblInvoiceBalance.setText("jLabel8");

        txtInvoiceBalance.setEditable(false);
        txtInvoiceBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblInvoiceDate.setText("jLabel1");

        txtInvoiceDate.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblInvoiceName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoiceName))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblInvoiceTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblInvoicePayment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoicePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblInvoiceBalance)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoiceBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblInvoice)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblInvoiceDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblInvoice, lblInvoiceName, lblInvoiceTotal, lblType});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblType)
                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoice)
                    .addComponent(txtInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvoice)
                    .addComponent(lblInvoiceDate)
                    .addComponent(txtInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoiceName)
                    .addComponent(txtInvoiceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoiceTotal)
                    .addComponent(txtInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInvoicePayment)
                    .addComponent(txtInvoicePayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInvoiceBalance)
                    .addComponent(txtInvoiceBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblPaymentMethod.setText("jLabel3");

        cmbPaymentMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPaymentMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaymentMethodActionPerformed(evt);
            }
        });

        lblPayment.setText("jLabel1");

        lblPaymentDate.setText("jLabel3");

        txtPaymentDate.setEditable(false);

        txtPayment.setEditable(false);

        btnPaymenDoc.setText("...");
        btnPaymenDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymenDocActionPerformed(evt);
            }
        });

        lblPaymentValue.setText("jLabel4");

        txtPaymentValue.setEditable(false);
        txtPaymentValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblPaymentBalance.setText("jLabel5");

        txtPaymentBalance.setEditable(false);
        txtPaymentBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblPaymentUsed.setText("jLabel6");

        txtPaymentUsed.setEditable(false);
        txtPaymentUsed.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblPaymentMethod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblPayment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPayment)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPaymenDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPaymentDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblPaymentValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPaymentValue, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPaymentUsed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPaymentUsed, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPaymentBalance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPaymentBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblPayment, lblPaymentMethod, lblPaymentValue});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentMethod)
                    .addComponent(cmbPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPayment)
                    .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPaymenDoc)
                    .addComponent(lblPaymentDate)
                    .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentValue)
                    .addComponent(txtPaymentValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentUsed)
                    .addComponent(txtPaymentUsed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentBalance)
                    .addComponent(txtPaymentBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtFinalBalance.setEditable(false);
        txtFinalBalance.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        lblFinalBalance.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCode)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPayValue, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFinalBalance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFinalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblObservations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservations))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtFinalBalance, txtPayValue});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCode, lblDate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode)
                    .addComponent(lblId)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDate)
                    .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValue)
                    .addComponent(txtPayValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFinalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFinalBalance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObservations)
                    .addComponent(txtObservations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        save();
        
        if (rowsAffected > 0) {
            this.ok = true;
            wEdit.dispose();
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.ok = false;
        
        wEdit.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPaymenDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymenDocActionPerformed
        WindowData window = null;
        String windowCode = null;
        try {
            switch (payment.getIdPaymentMethod()) {
                case 5: // Deposito
                case 8: // Transaccion
                    windowCode = "WSEARCH_BANKTRANS";
                    break;
                case 4: // Retencion
                    windowCode = "WSEARCH_WITHHOLDINGRECEIVED";
                    break;
                default:
                    DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Forma de Pago no registrada en el progama. Forma de pago: " + payment.getIdPaymentMethod());
                    break;
            }
            if (windowCode != null) {
                window = WindowDAO.getWindow(windowCode);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (window != null) {
            WList w = new WList(wEdit, window);
            w.setVisible(true);
            if (w.isOk()) {
                payment.setIdPaymentDoc(w.getIdSelected());
                try {
                    showPay();
                    
                    double invoiceBalance = 0;
                    double paymentBalance = 0;
                    if (!txtInvoiceBalance.getText().isEmpty())
                        invoiceBalance = Double.parseDouble(txtInvoiceBalance.getText());
                    if (!txtPaymentBalance.getText().isEmpty())
                        paymentBalance = Double.parseDouble(txtPaymentBalance.getText());

                    double pay = invoiceBalance;
                    if (pay > paymentBalance) {
                        pay = paymentBalance;
                    }

                    txtPayValue.setText(AppGlobal.getFormatDecimalShort().format(pay));
                    txtFinalBalance.setText(AppGlobal.getFormatDecimalShort().format(invoiceBalance - pay));
                    
                    if (paymentBalance <= 0) {
                        JOptionPane.showMessageDialog(wEdit, AppGlobal.getText("WEDIT_PAYMENTS_PAYDOCBALANCEZERO_TEXT"), AppGlobal.getText("WMSG_WARNING_TITLE"), JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (ClassNotFoundException | SQLException | ParseException ex) {
                    Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnPaymenDocActionPerformed

    private void btnInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceActionPerformed
        WindowData window = null;
        String windowCode = null;
        switch (payment.getIdType()) {
            case 1:
                windowCode = "WSEARCH_INVOICES";
                break;
            case 2:
                windowCode = "WSEARCH_CONTRACTS";
                break;
            default:
                DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Tipo no registrado en el progama. Tipo: " + payment.getIdPaymentMethod());
                break;
        }
        try {
            if (windowCode != null) {
                window = WindowDAO.getWindow(windowCode);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (window != null) {
            DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Abriendo ventana. Código: " + windowCode);
            WList w = new WList(wEdit, window);

            w.setVisible(true);
            if (w.isOk()) {
                payment.setIdInvoice(w.getIdSelected());
                try {
                    showInvoice();
                    
                    double invoiceBalance = 0;
                    double paymentBalance = 0;
                    if (!txtInvoiceBalance.getText().isEmpty())
                        invoiceBalance = Double.parseDouble(txtInvoiceBalance.getText());
                    if (!txtPaymentBalance.getText().isEmpty())
                        paymentBalance = Double.parseDouble(txtPaymentBalance.getText());

                    double pay = invoiceBalance;
                    if (pay > paymentBalance) {
                        pay = paymentBalance;
                    }

                    txtPayValue.setText(AppGlobal.getFormatDecimalShort().format(pay));
                    txtFinalBalance.setText(AppGlobal.getFormatDecimalShort().format(invoiceBalance - pay));
                    
                    if (invoiceBalance <= 0) {
                        JOptionPane.showMessageDialog(wEdit, AppGlobal.getText("WEDIT_PAYMENTS_INVOICEBALANCEZERO_TEXT"), AppGlobal.getText("WMSG_WARNING_TITLE"), JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                } catch (ClassNotFoundException | SQLException | ParseException ex) {
                    Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnInvoiceActionPerformed

    private void cmbTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypeActionPerformed
        changeType();
    }//GEN-LAST:event_cmbTypeActionPerformed

    private void changeType() {
        if (cmbType.getItemCount() > 0 && payment != null) {
            String typeStr = (String) cmbType.getSelectedItem();
            try {
                Type type = TypeDAO.get("paymenttypes", "name", typeStr);
                if (type != null) {
                    if (type.getId() != payment.getIdType()) {
                        payment.setIdType(type.getId());
                        payment.setIdInvoice(-1);
                        showInvoice();
                    }
                }
                else {
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Tipo de documento a pagar nulo: " + typeStr);
                }
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void cmbPaymentMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaymentMethodActionPerformed
        changeMethod();
    }//GEN-LAST:event_cmbPaymentMethodActionPerformed

    private void changeMethod() {
        if (cmbPaymentMethod.getItemCount() > 0 && payment != null) {
            String pmStr = (String) cmbPaymentMethod.getSelectedItem();
            try {
                Type pm = TypeDAO.get("paymentmethods", "name", pmStr);
                if (pm != null) {
                    if (pm.getId() != payment.getIdPaymentMethod()) {
                        payment.setIdPaymentMethod(pm.getId());
                        payment.setIdPaymentDoc(-1);
                        showPay();
                    }
                }
                else {
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Metodo de pago nulo: " + pmStr);
                }
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void txtPayValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPayValueActionPerformed
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "txtpayvalueactionperformenr-----------------------");
        
    }//GEN-LAST:event_txtPayValueActionPerformed

    private void txtPayValueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPayValueFocusLost
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "txtpayvalueLOSTFOCUS-----------------------");
        
        double pay = Double.parseDouble(txtPayValue.getText());
        
        double invoiceBalance = 0;
        double paymentBalance = 0;
        if (!txtInvoiceBalance.getText().isEmpty())
            invoiceBalance = Double.parseDouble(txtInvoiceBalance.getText());
        if (!txtPaymentBalance.getText().isEmpty())
            paymentBalance = Double.parseDouble(txtPaymentBalance.getText());
        
        if (pay > invoiceBalance || pay > paymentBalance) {
            pay = invoiceBalance;
            if (pay > paymentBalance) {
                pay = paymentBalance;
            }
            String text = "El pago no puede se mayor a: " + AppGlobal.getFormatDecimalShort().format(pay);
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), text);
            VMessage.show(wEdit, text, "Error");
            
            txtPayValue.setText(AppGlobal.getFormatDecimalShort().format(pay));
            txtFinalBalance.setText(AppGlobal.getFormatDecimalShort().format(invoiceBalance - pay));
        }
        else {
            txtFinalBalance.setText(AppGlobal.getFormatDecimalShort().format(invoiceBalance - pay));
        }
    }//GEN-LAST:event_txtPayValueFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnInvoice;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPaymenDoc;
    private javax.swing.JComboBox<String> cmbPaymentMethod;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser jdcDate;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblFinalBalance;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblInvoice;
    private javax.swing.JLabel lblInvoiceBalance;
    private javax.swing.JLabel lblInvoiceDate;
    private javax.swing.JLabel lblInvoiceName;
    private javax.swing.JLabel lblInvoicePayment;
    private javax.swing.JLabel lblInvoiceTotal;
    private javax.swing.JLabel lblObservations;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblPaymentBalance;
    private javax.swing.JLabel lblPaymentDate;
    private javax.swing.JLabel lblPaymentMethod;
    private javax.swing.JLabel lblPaymentUsed;
    private javax.swing.JLabel lblPaymentValue;
    private javax.swing.JLabel lblType;
    private javax.swing.JLabel lblValue;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtFinalBalance;
    private javax.swing.JTextField txtInvoice;
    private javax.swing.JTextField txtInvoiceBalance;
    private javax.swing.JTextField txtInvoiceDate;
    private javax.swing.JTextField txtInvoiceName;
    private javax.swing.JTextField txtInvoicePayment;
    private javax.swing.JTextField txtInvoiceTotal;
    private javax.swing.JTextField txtObservations;
    private javax.swing.JTextField txtPayValue;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtPaymentBalance;
    private javax.swing.JTextField txtPaymentDate;
    private javax.swing.JTextField txtPaymentUsed;
    private javax.swing.JTextField txtPaymentValue;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        payment.setCode(txtCode.getText());
        payment.setDate(jdcDate.getDate());
        payment.setValue(Double.parseDouble(txtPayValue.getText().trim()));
        payment.setObservations(txtObservations.getText().trim());
        
        String error = PaymentDAO.validate(payment);
        if (error == null) {
            try {
                Date dateInv = DateTime.getStringToDateUtil(txtInvoiceDate.getText(), AppGlobal.getFormatDate());
                Date datePay = DateTime.getStringToDateUtil(txtPaymentDate.getText(), AppGlobal.getFormatDate());
                
                error = PaymentDAO.validateDate(payment.getDate(), dateInv, datePay);
            } catch (ParseException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (error == null) {
            double invoiceBalance = Double.parseDouble(txtInvoiceBalance.getText());
            double paymentBalance = Double.parseDouble(txtPaymentBalance.getText());
            
            if (payment.getValue() > paymentBalance) {
                error = "El valor debe ser menor al saldo del documento de pago.";
            }
            if (payment.getValue() > invoiceBalance) {
                error = "El valor debe ser menor al saldo del valor a pagar.";
            }
        }

        if (error == null) {
            try {
                rowsAffected = PaymentDAO.update(payment);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditPayment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), error);
            VError.show(error);
        }
    }

}
