/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.withholding;

import console.DknConsole;
import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.contract.Contract;
import model.contract.ContractDAO;
import model.ente.Ente;
import model.ente.EnteDAO;
import model.invoice.Invoice;
import model.invoice.InvoiceDAO;
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
 * @author richneom
 */
public class PnlEditWithholdingReceived extends PnlEdit {

    private final WEdit wEdit;
    private final int id;
    private int idInvoiceType;
    private int idInvoice;
    private boolean isLoad;

    /**
     * Creates new form PnlEditLocalType
     * @param wEdit
     * @param id
     */
    public PnlEditWithholdingReceived(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        this.isLoad = false;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        lblNumber.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_NUMBER_TEXT") + ":");
        lblInvoiceType.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_INVOICETYPE_TEXT") + ":");
        lblInvoiceNumber.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_INVOICENUMBER_TEXT") + ":");
        lblDate.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_DATE_TEXT") + ":");
        lblValue.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_VALUE_TEXT") + ":");
        lblObservations.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_OBSERVATIONS_TEXT") + ":");
        chkCanceled.setText(AppGlobal.getText("WEDIT_WITHHOLDINGRECEIVED_CANCELED_TEXT"));
        
        cmbInvoiceType.removeAllItems();
        Types types = null;
        try {
            types = TypeDAO.gets("invoicestypes", "name");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Type type : types) {
            cmbInvoiceType.addItem(type.getName());
        }
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
        
        jdcDate.setDateFormatString(AppGlobal.getFormatDate());
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            txtSerie.setText("");
            txtNumber.setText("");
            txtInvoiceType.setText("");
            txtInvoiceCustomer.setText("");
            jdcDate.setDate(DateTime.getNow());
            txtValue.setText("0.00");
            chkCanceled.setSelected(false);
            
            txtObservations.setText("");
            
            this.isLoad = true;
            cmbInvoiceType.setSelectedIndex(1);
            
            idInvoice = -1;
        }
        else {
            WithholdingReceived wr = null;
            try {
                wr = WithholdingReceivedDAO.get(id);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (wr != null) {
                txtSerie.setText(wr.getSerie());
                txtNumber.setText(wr.getNumber());
                txtInvoiceCustomer.setText(String.valueOf(wr.getIdInvoice()));
                jdcDate.setDate(wr.getDate());
                txtValue.setText(AppGlobal.getFormatDecimalShort().format(wr.getValue()));
                txtObservations.setText(wr.getObservations());
                chkCanceled.setSelected(wr.isCanceled());

                Type invoiceType;
                try {
                    invoiceType = TypeDAO.get("invoicestypes", wr.getIdInvoiceType());

                    if (invoiceType != null) {
                        cmbInvoiceType.setSelectedItem(invoiceType.getName());
                        idInvoiceType = wr.getIdInvoiceType();
                        txtInvoiceType.setText(invoiceType.getName());
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró el tipo de factura. ID: " + id);
                    Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Invoice invoice = InvoiceDAO.get(wr.getIdInvoice());
                    if (invoice != null) {
                        Contract contract = ContractDAO.get(invoice.getIdContract());
                        if (contract != null) {
                            Ente customer = EnteDAO.get(contract.getIdCustomer());
                            if (customer != null) {
                                txtInvoiceCustomer.setText(customer.getDni() + " - " + customer.getFullName());
                                idInvoice = wr.getIdInvoice();
                                txtInvoiceNumber.setText(invoice.getFullNumber(AppConfig.getInt(ConfigData.INVOICES_NUMBER_TAM)));
                                txtInvoiceDate.setText(DateTime.getDateUtilToString(invoice.getDate(), AppGlobal.getFormatDate()));
                            }
                            else {
                                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró el cliente. ID: " + contract.getIdCustomer());
                            }
                        }
                        else {
                            DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró el contrato. ID: " + invoice.getIdContract());
                        }
                    }
                    else {
                        DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró la factura. ID: " + id);
                    }
                } catch (ClassNotFoundException | SQLException | ParseException ex) {
                    DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró la factura. ID: " + id);
                    Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                this.isLoad = true;
            }
            else {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró la Retención. ID: " + id);
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
        lblNumber = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        lblInvoiceType = new javax.swing.JLabel();
        txtInvoiceType = new javax.swing.JTextField();
        lblInvoiceNumber = new javax.swing.JLabel();
        txtInvoiceCustomer = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        lblValue = new javax.swing.JLabel();
        lblObservations = new javax.swing.JLabel();
        txtObservations = new javax.swing.JTextField();
        jdcDate = new com.toedter.calendar.JDateChooser();
        txtValue = new javax.swing.JTextField();
        chkCanceled = new javax.swing.JCheckBox();
        btnInvoice = new javax.swing.JButton();
        cmbInvoiceType = new javax.swing.JComboBox<>();
        lblInvoice = new javax.swing.JLabel();
        txtInvoiceNumber = new javax.swing.JTextField();
        lblInvoiceDate = new javax.swing.JLabel();
        txtInvoiceDate = new javax.swing.JTextField();

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

        lblNumber.setText("jLabel1");

        txtSerie.setText("jTextField1");

        lblId.setText("jLabel2");

        txtNumber.setText("jTextField2");

        lblInvoiceType.setText("jLabel4");

        txtInvoiceType.setEditable(false);
        txtInvoiceType.setText("jTextField3");

        lblInvoiceNumber.setText("jLabel1");

        txtInvoiceCustomer.setEditable(false);
        txtInvoiceCustomer.setText("jTextField1");

        lblDate.setText("jLabel1");

        lblValue.setText("jLabel2");

        lblObservations.setText("jLabel2");

        txtObservations.setText("jTextField1");

        txtValue.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtValue.setText("jTextField1");

        chkCanceled.setText("jCheckBox1");

        btnInvoice.setText("...");
        btnInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceActionPerformed(evt);
            }
        });

        cmbInvoiceType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbInvoiceType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbInvoiceTypeActionPerformed(evt);
            }
        });

        lblInvoice.setText("jLabel1");

        txtInvoiceNumber.setEditable(false);
        txtInvoiceNumber.setText("jTextField1");

        lblInvoiceDate.setText("jLabel1");

        txtInvoiceDate.setEditable(false);
        txtInvoiceDate.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblObservations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservations))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkCanceled))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblInvoiceNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInvoice))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblValue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblInvoiceType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(txtInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblInvoiceDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblInvoice)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtInvoiceCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 122, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblDate, lblInvoice, lblInvoiceDate, lblInvoiceNumber, lblInvoiceType, lblNumber, lblValue});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkCanceled)
                        .addComponent(lblDate))
                    .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoiceType)
                    .addComponent(cmbInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoiceNumber)
                    .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvoice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoice)
                    .addComponent(txtInvoiceCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoiceDate)
                    .addComponent(txtInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValue)
                    .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObservations)
                    .addComponent(txtObservations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceActionPerformed
        selectInvoice();
    }//GEN-LAST:event_btnInvoiceActionPerformed

    private void cmbInvoiceTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbInvoiceTypeActionPerformed
        selectInvoiceType();
    }//GEN-LAST:event_cmbInvoiceTypeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnInvoice;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox chkCanceled;
    private javax.swing.JComboBox<String> cmbInvoiceType;
    private com.toedter.calendar.JDateChooser jdcDate;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblInvoice;
    private javax.swing.JLabel lblInvoiceDate;
    private javax.swing.JLabel lblInvoiceNumber;
    private javax.swing.JLabel lblInvoiceType;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblObservations;
    private javax.swing.JLabel lblValue;
    private javax.swing.JTextField txtInvoiceCustomer;
    private javax.swing.JTextField txtInvoiceDate;
    private javax.swing.JTextField txtInvoiceNumber;
    private javax.swing.JTextField txtInvoiceType;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtObservations;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        WithholdingReceived wr = new WithholdingReceived();
        
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), " idInvoiceType: " + idInvoiceType);
        
        wr.setId(id);
        wr.setSerie(txtSerie.getText());
        wr.setNumber(txtNumber.getText());
        wr.setIdInvoiceType(idInvoiceType);
        wr.setIdInvoice(idInvoice);
        wr.setDate(jdcDate.getDate());
        wr.setValue(Double.parseDouble(txtValue.getText().trim()));
        wr.setObservations(txtObservations.getText());
        wr.setCanceled(chkCanceled.isSelected());
        
//        String typeStr = (String) cmbIdType.getSelectedItem();
//        Type localType = null;
//        try {
//            localType = TypeDAO.get("localtypes", "name", typeStr);
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (localType != null) {
//            wr.setIdType(localType.getId());
//        }
        
        String error = WithholdingReceivedDAO.validate(wr);
        
        if (error == null) {
            try {
                rowsAffected = WithholdingReceivedDAO.update(wr);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void selectInvoice() {
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "Selecciona factura");
        if (idInvoiceType > 0) {
            WindowData window = null;
            try {
                window = WindowDAO.getWindow("WSEARCH_INVOICES");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (window != null) {
                WList w = new WList(wEdit, window);
                
                w.setVisible(true);
                if (w.isOk()) {
                    int idSel = w.getIdSelected();
                    
                    try {
                        Invoice invoice = InvoiceDAO.get(idSel);
                        Contract contract = ContractDAO.get(invoice.getIdContract());
                        Ente customer = EnteDAO.get(contract.getIdCustomer());
                        txtInvoiceCustomer.setText(customer.getDni() + " - " + customer.getFullName());
                        idInvoice = invoice.getId();
                        txtInvoiceNumber.setText(invoice.getFullNumber(AppConfig.getInt(ConfigData.INVOICES_NUMBER_TAM)));
                        txtInvoiceDate.setText(DateTime.getDateUtilToString(invoice.getDate(), AppGlobal.getFormatDate()));
                    } catch (ClassNotFoundException | SQLException | ParseException ex) {
                        DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró la factura. ID: " + id);
                        Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void selectInvoiceType() {
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "Selecciona tipo de factura");
        if (isLoad) {
            String typeStr = (String) cmbInvoiceType.getSelectedItem();
            try {
                Type type = TypeDAO.get("invoicestypes", "name", typeStr);
                cmbInvoiceType.setSelectedItem(type.getName());
                txtInvoiceType.setText(type.getName());
                idInvoiceType = type.getId();
            } catch (ClassNotFoundException | SQLException ex) {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontró el tipo de factura. ID: " + id);
                Logger.getLogger(PnlEditWithholdingReceived.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
