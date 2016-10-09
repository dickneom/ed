/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.contract;

import console.DknConsole;
import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ente.Ente;
import model.ente.EnteDAO;
import model.local.Local;
import model.local.LocalDAO;
import system.config.AppGlobal;
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
 * @author richneom
 */
public class PnlEditContract extends PnlEdit {

    private final WEdit wEdit;
    private final int id;
    private int idCustomer;
    private int idLocal;

    /**
     * Creates new form PnlEditBank
     * @param wEdit
     * @param id
     */
    public PnlEditContract(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_CONTRACTS_CODE_TEXT") + ":");
        lblDate.setText(AppGlobal.getText("WEDIT_CONTRACTS_DATE_TEXT") + ":");
        lblCustomer.setText(AppGlobal.getText("WEDIT_CONTRACTS_CUSTOMER_TEXT") + ":");
        lblLocal.setText(AppGlobal.getText("WEDIT_CONTRACTS_LOCAL_TEXT") + ":");
        lblRent.setText(AppGlobal.getText("WEDIT_CONTRACTS_RENT_TEXT") + ":");
        lblDiscount.setText(AppGlobal.getText("WEDIT_CONTRACTS_DISCOUNT_TEXT") + ":");
        lblDiscountDay.setText(AppGlobal.getText("WEDIT_CONTRACTS_DISCOUNTDAYS_TEXT") + ":");
        lblWarranty.setText(AppGlobal.getText("WEDIT_CONTRACTS_WARRANTY_TEXT") + ":");
        lblDateInit.setText(AppGlobal.getText("WEDIT_CONTRACTS_DATEINIT_TEXT") + ":");
        lblDateFinal.setText(AppGlobal.getText("WEDIT_CONTRACTS_DATEFINAL_TEXT") + ":");
        lblDateFinished.setText(AppGlobal.getText("WEDIT_CONTRACTS_DATEFINISHED_TEXT") + ":");
        lblPurpose.setText(AppGlobal.getText("WEDIT_CONTRACTS_PURPOSE_TEXT") + ":");
        chkBackWarranty.setText(AppGlobal.getText("WEDIT_CONTRACTS_BACKWARRANTY_TEXT"));
        lblNotes.setText(AppGlobal.getText("WEDIT_CONTRACTS_NOTE_TEXT") + ":");
        chkActive.setText(AppGlobal.getText("WEDIT_CONTRACTS_ACTIVE_TEXT"));
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
        
        cmbWarrantyPayMethod.removeAllItems();
        try {
            Types paymentMethods = TypeDAO.gets("paymentmethods", "name");
            
            if (paymentMethods != null) {
                for (Type paymentMethod : paymentMethods)
                cmbWarrantyPayMethod.addItem(paymentMethod.getName());
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jdcDate.setDateFormatString(AppGlobal.getFormatDate());
        jdcDateInit.setDateFormatString(AppGlobal.getFormatDate());
        jdcDateFinal.setDateFormatString(AppGlobal.getFormatDate());
        jdcDateFinished.setDateFormatString(AppGlobal.getFormatDate());
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            txtCode.setText("");
            jdcDate.setDate(DateTime.getNow());
            txtCustomer.setText("");
            txtLocal.setText("");
            jdcDateInit.setDate(DateTime.getNow());
            jdcDateFinal.setDate(DateTime.addDateYears(DateTime.getNow(), 1));
            jdcDateFinished.setDate(null);
            txtRent.setText("0.00");
            txtDiscount.setText("0.00");
            spnDiscountDay.setValue(5);
            txtWarranty.setText("0.00");
            txtPurpose.setText("");
            txtNote.setText("");
            chkActive.setSelected(true);
        }
        else {
            try {
                Contract contract = ContractDAO.get(id);
                
                if (contract != null) {
                    txtCode.setText(contract.getCode());
                    jdcDate.setDate(contract.getDate());
                    
                    int id = contract.getIdCustomer();
                    Ente customer = EnteDAO.get(id);
                    if (customer != null) {
                        idCustomer = id;
                        txtCustomer.setText(customer.getDni() + " - " + customer.getSurname() + " " +  customer.getName());
                    }
                    else {
                        DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Cliente no encontrado. id: " + id);
                    }
                    
                    id = contract.getIdLocal();
                    Local local = LocalDAO.get(id);
                    if (local != null) {
                        idLocal = id;
                        txtLocal.setText(local.getCode());
                    }
                    else {
                        DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Local no encontrado. id: " + id);
                    }
                    
                    jdcDateInit.setDate(contract.getDateInit());
                    jdcDateFinal.setDate(contract.getDateFinal());
                    txtRent.setText(AppGlobal.getFormatDecimalShort().format(contract.getRent()));
                    txtDiscount.setText(AppGlobal.getFormatDecimalShort().format(contract.getDiscount()));
                    spnDiscountDay.setValue(contract.getDiscountDays());
                    txtWarranty.setText(AppGlobal.getFormatDecimalShort().format(contract.getWarranty()));
                    chkBackWarranty.setSelected(contract.isBackWarranty());
                    
                    int idWpm = contract.getIdPayMethodWarranty();
                    Type pm = TypeDAO.get("paymentMethods", idWpm);
                    if (pm != null) {
                        cmbWarrantyPayMethod.setSelectedItem(pm.getName());
                    }
                    else {
                        DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "Forma de pago no encontrado. id: " + idWpm);
                    }
                    
                    txtPurpose.setText(contract.getPurpose());
                    if (contract.getDateFinish() != null) {
                        jdcDateFinished.setDate(contract.getDateFinish());
                    }
                    else {
                        jdcDateFinished.setDate(null);
                    }
                    
                    txtNote.setText(contract.getNotes());
                    chkActive.setSelected(contract.isActive());
                }
                
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontrol el Contrato. ID: " + id);
                Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        lblRent = new javax.swing.JLabel();
        txtRent = new javax.swing.JTextField();
        lblNotes = new javax.swing.JLabel();
        chkActive = new javax.swing.JCheckBox();
        lblDiscount = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        lblDiscountDay = new javax.swing.JLabel();
        txtWarranty = new javax.swing.JTextField();
        cmbWarrantyPayMethod = new javax.swing.JComboBox<>();
        lblWarranty = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jdcDate = new com.toedter.calendar.JDateChooser();
        lblCustomer = new javax.swing.JLabel();
        txtCustomer = new javax.swing.JTextField();
        btnCustomer = new javax.swing.JButton();
        lblLocal = new javax.swing.JLabel();
        txtLocal = new javax.swing.JTextField();
        btnLocal = new javax.swing.JButton();
        lblDateInit = new javax.swing.JLabel();
        jdcDateInit = new com.toedter.calendar.JDateChooser();
        lblDateFinal = new javax.swing.JLabel();
        jdcDateFinal = new com.toedter.calendar.JDateChooser();
        spnDiscountDay = new javax.swing.JSpinner();
        chkBackWarranty = new javax.swing.JCheckBox();
        lblPurpose = new javax.swing.JLabel();
        txtPurpose = new javax.swing.JTextField();
        lblDateFinished = new javax.swing.JLabel();
        jdcDateFinished = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();

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

        txtCode.setText("jTextField1");

        lblId.setText("jLabel2");

        lblRent.setText("jLabel3");

        txtRent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRent.setText("jTextField2");

        lblNotes.setText("jLabel4");

        chkActive.setText("jCheckBox1");

        lblDiscount.setText("jLabel1");

        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setText("jTextField1");

        lblDiscountDay.setText("jLabel1");

        txtWarranty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtWarranty.setText("jTextField1");

        cmbWarrantyPayMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblWarranty.setText("jLabel1");

        lblDate.setText("jLabel1");

        lblCustomer.setText("jLabel1");

        txtCustomer.setEditable(false);
        txtCustomer.setText("jTextField1");
        txtCustomer.setMaximumSize(new java.awt.Dimension(420, 420));
        txtCustomer.setMinimumSize(new java.awt.Dimension(420, 420));

        btnCustomer.setText("...");
        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerActionPerformed(evt);
            }
        });

        lblLocal.setText("jLabel1");

        txtLocal.setEditable(false);
        txtLocal.setText("jTextField1");

        btnLocal.setText("...");
        btnLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocalActionPerformed(evt);
            }
        });

        lblDateInit.setText("jLabel1");

        lblDateFinal.setText("jLabel1");

        chkBackWarranty.setText("jCheckBox1");

        lblPurpose.setText("jLabel1");

        txtPurpose.setText("jTextField1");

        lblDateFinished.setText("jLabel1");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane1.setViewportView(txtNote);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblId, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chkActive, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDiscountDay)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnDiscountDay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDateFinished)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcDateFinished, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblRent)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRent, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDiscount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblWarranty)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbWarrantyPayMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkBackWarranty))
                            .addComponent(lblNotes)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPurpose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCustomer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCustomer))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDateInit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcDateInit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDateFinal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcDateFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLocal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLocal)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCode, lblCustomer, lblDate, lblDateFinal, lblDateFinished, lblDateInit, lblDiscount, lblDiscountDay, lblLocal, lblPurpose, lblRent, lblWarranty});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkActive)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblDate)
                        .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCustomer)
                                    .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCustomer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblLocal)
                                            .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnLocal))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblDateInit))
                                    .addComponent(lblDateFinal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jdcDateFinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jdcDateInit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRent)
                            .addComponent(txtRent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiscount)
                            .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiscountDay)
                            .addComponent(spnDiscountDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblWarranty)
                            .addComponent(txtWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbWarrantyPayMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkBackWarranty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPurpose)
                            .addComponent(txtPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(lblDateFinished))
                    .addComponent(jdcDateFinished, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
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

    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerActionPerformed
        WindowData window = null;
        try {
            window = (WindowData) WindowDAO.getWindow("WSEARCH_CUSTOMERS");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        WList w = new WList(wEdit, window);
        w.setVisible(true);
        if (w.isOk()) {
            try {
                int idCus = w.getIdSelected();
                Ente customer = EnteDAO.get(idCus);
                if (customer != null) {
                    idCustomer = idCus;
                    txtCustomer.setText(customer.getDni() + " - " + customer.getSurname() + " " + customer.getName());
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnCustomerActionPerformed

    private void btnLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalActionPerformed
        WindowData window = null;
        try {
            window = (WindowData) WindowDAO.getWindow("WSEARCH_LOCAL");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        WList w = new WList(wEdit, window);
        
        w.setVisible(true);
        if (w.isOk()) {
            try {
                int idLoc = w.getIdSelected();
                Local local = LocalDAO.get(idLoc);
                if (local != null) {
                    idLocal = idLoc;
                    txtLocal.setText(local.getCode());
                    txtRent.setText(AppGlobal.getFormatDecimalShort().format(local.getRent()));
                    txtDiscount.setText(AppGlobal.getFormatDecimalShort().format(local.getDiscount()));
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnLocalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnLocal;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox chkActive;
    private javax.swing.JCheckBox chkBackWarranty;
    private javax.swing.JComboBox<String> cmbWarrantyPayMethod;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcDate;
    private com.toedter.calendar.JDateChooser jdcDateFinal;
    private com.toedter.calendar.JDateChooser jdcDateFinished;
    private com.toedter.calendar.JDateChooser jdcDateInit;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDateFinal;
    private javax.swing.JLabel lblDateFinished;
    private javax.swing.JLabel lblDateInit;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblDiscountDay;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLocal;
    private javax.swing.JLabel lblNotes;
    private javax.swing.JLabel lblPurpose;
    private javax.swing.JLabel lblRent;
    private javax.swing.JLabel lblWarranty;
    private javax.swing.JSpinner spnDiscountDay;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtLocal;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtPurpose;
    private javax.swing.JTextField txtRent;
    private javax.swing.JTextField txtWarranty;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        Contract contract = new Contract();
        
        contract.setId(id);
        contract.setCode(txtCode.getText());
        contract.setDate(jdcDate.getDate());
        contract.setIdCustomer(idCustomer);
        contract.setIdLocal(idLocal);
        contract.setDateInit(jdcDateInit.getDate());
        contract.setDateFinal(jdcDateFinal.getDate());
        
        double valor = 0;
        if (txtRent.getText() != null && !txtRent.getText().isEmpty()) {
            valor = Double.parseDouble(txtRent.getText());
        }
        contract.setRent(valor);
        
        valor = 0;
        if (txtDiscount.getText() != null && !txtDiscount.getText().isEmpty() ) {
            valor = Double.parseDouble(txtDiscount.getText());
        }
        contract.setDiscount(valor);
        
        contract.setDiscountDays((int) spnDiscountDay.getValue());
        
        if (txtWarranty.getText() != null && !txtWarranty.getText().isEmpty()) {
            valor = Double.parseDouble(txtWarranty.getText());
        }
        contract.setWarranty(valor);
        
        contract.setBackWarranty(chkBackWarranty.isSelected());
        
        String PayMethodStr = (String) cmbWarrantyPayMethod.getSelectedItem();
        Type pm = null;
        try {
            pm = TypeDAO.get("paymentMethods", "name", PayMethodStr);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pm != null) {
            contract.setIdPayMethodWarranty(pm.getId());
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), " ERROR. Forma de pago no encontrado. Name: " + PayMethodStr);
        }
        
        contract.setPurpose(txtPurpose.getText());
        contract.setDateFinish(jdcDateFinished.getDate());
        contract.setNote(txtNote.getText());
        contract.setActive(chkActive.isSelected());
        
        String error = ContractDAO.validate(contract);
        
        if (error == null) {
            try {
                rowsAffected = ContractDAO.update(contract);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditContract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
