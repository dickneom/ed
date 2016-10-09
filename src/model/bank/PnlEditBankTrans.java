/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bank;

import console.DknConsole;
import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppGlobal;
import system.edit.PnlEdit;
import system.edit.WEdit;
import system.type.Type;
import system.type.TypeDAO;
import system.type.Types;

/**
 *
 * @author DickNeoM
 */
public class PnlEditBankTrans extends PnlEdit {

    private final WEdit wEdit;
    private final int id;

    /**
     * Creates new form PnlEditBankTrans
     * @param wEdit
     * @param id
     */
    public PnlEditBankTrans(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), " BANKTRANS INIT");
        lblBankAccount.setText(AppGlobal.getText("WEDIT_BANKTRANS_BANKACCOUNT_TEXT"));
        lblDate.setText(AppGlobal.getText("WEDIT_BANKTRANS_DATE_TEXT"));
        lblType.setText(AppGlobal.getText("WEDIT_BANKTRANS_TYPE_TEXT") + ":");
        lblNumber.setText(AppGlobal.getText("WEDIT_BANKTRANS_NUMBER_TEXT") + ":");
        lblValue.setText(AppGlobal.getText("WEDIT_BANKTRANS_VALUE_TEXT"));
        lblObservations.setText(AppGlobal.getText("WEDIT_BANKTRANS_OBSERVATIONS_TEXT") + ":");
        chkCanceled.setText(AppGlobal.getText("WEDIT_BANKTRANS_CANCELED_TEXT"));
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
        
        jdcDate.setDateFormatString(AppGlobal.getFormatDate());
        
        cmbBankAccount.removeAllItems();
        BankAccounts bas = null;
        try {
            bas = BankAccountDAO.gets("name");
            DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), " bas.size(): " + bas.size());
            if (bas != null && bas.size() > 0) {
                for (BankAccount ba : bas) {
                    cmbBankAccount.addItem(ba.getName());
                }
            }
            else {
                cmbBankAccount.addItem("Sin Ctas Registradas");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditBankTrans.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        cmbType.removeAllItems();
        Types types = null;
        try {
            types = TypeDAO.gets("banktranstypes", "name");
            if (types != null && types.size() > 0) {
                for (Type type : types) {
                    cmbType.addItem(type.getName());
                }
            }
            else {
                cmbType.addItem("Sin tipos Registrados");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditBankTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lblRegType.setText("");
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            jdcDate.setDate(DateTime.getNow());
            
            txtNumber.setText("");
            txtValue.setText("");
            txtObservations.setText("");
            chkCanceled.setSelected(false);
            
            lblRegType.setText(String.valueOf(1));
        }
        else {
            try {
                BankTrans bankTrans = BankTransDAO.get(id);
                
                BankAccount ba = BankAccountDAO.get(bankTrans.getIdBankAccount());
                if (ba != null) {
                    cmbBankAccount.setSelectedItem(ba.getName());
                }
                
                jdcDate.setDate(bankTrans.getDate());
                
                Type type = TypeDAO.get("banktranstypes", bankTrans.getIdType());
                if (type != null) {
                    cmbType.setSelectedItem(type.getName());
                }
                
                txtNumber.setText(bankTrans.getNumber());
                txtValue.setText(String.valueOf(bankTrans.getValue()));
                
                chkCanceled.setSelected(bankTrans.isCanceled());
                txtObservations.setText(bankTrans.getObservations());
                
                lblRegType.setText(String.valueOf(bankTrans.getIdRegType()));
                
                if (bankTrans.getIdRegType() == 2) {
                    btnOk.setEnabled(false);
                }
                
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString() + "No se encontro la transaccion bancaria. ID: " + id);
                Logger.getLogger(PnlEditBankTrans.class.getName()).log(Level.SEVERE, null, ex);
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
        lblType = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        lblNumber = new javax.swing.JLabel();
        txtValue = new javax.swing.JTextField();
        lblObservations = new javax.swing.JLabel();
        txtObservations = new javax.swing.JTextField();
        chkCanceled = new javax.swing.JCheckBox();
        lblBankAccount = new javax.swing.JLabel();
        cmbBankAccount = new javax.swing.JComboBox<>();
        lblDate = new javax.swing.JLabel();
        jdcDate = new com.toedter.calendar.JDateChooser();
        cmbType = new javax.swing.JComboBox<>();
        lblValue = new javax.swing.JLabel();
        lblRegType = new javax.swing.JLabel();

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

        lblType.setText("jLabel1");

        txtNumber.setText("jTextField1");

        lblId.setText("jLabel2");

        lblNumber.setText("jLabel3");

        txtValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValue.setText("jTextField2");

        lblObservations.setText("jLabel4");

        txtObservations.setText("jTextField3");

        chkCanceled.setText("jCheckBox1");

        lblBankAccount.setText("jLabel1");

        cmbBankAccount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDate.setText("jLabel1");

        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblValue.setText("jLabel1");

        lblRegType.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblRegType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(chkCanceled))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBankAccount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBankAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblObservations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservations)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblBankAccount, lblDate, lblNumber, lblObservations, lblType, lblValue});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(lblBankAccount)
                    .addComponent(cmbBankAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkCanceled)
                        .addComponent(lblDate))
                    .addComponent(jdcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblType)
                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnOk)
                    .addComponent(lblRegType))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox chkCanceled;
    private javax.swing.JComboBox<String> cmbBankAccount;
    private javax.swing.JComboBox<String> cmbType;
    private com.toedter.calendar.JDateChooser jdcDate;
    private javax.swing.JLabel lblBankAccount;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblObservations;
    private javax.swing.JLabel lblRegType;
    private javax.swing.JLabel lblType;
    private javax.swing.JLabel lblValue;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtObservations;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        BankTrans bankTrans = new BankTrans();
        
        bankTrans.setId(id);
        
        String baStr = (String) cmbBankAccount.getSelectedItem();
        BankAccount bankAccount = null;
        try {
            bankAccount = BankAccountDAO.get("name", baStr);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditBankTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (bankAccount != null) {
            bankTrans.setIdBankAccount(bankAccount.getId());
        }
        
        bankTrans.setDate(jdcDate.getDate());
        
        String typeStr = (String) cmbType.getSelectedItem();
        Type type = null;
        try {
            type = TypeDAO.get("banktranstypes", "name", typeStr);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditBankTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (type != null) {
            bankTrans.setIdType(type.getId());
            bankTrans.setType(type.getCode());
        }
        
        bankTrans.setNumber(txtNumber.getText());
        bankTrans.setValue(Double.parseDouble(txtValue.getText()));
        bankTrans.setCanceled(chkCanceled.isSelected());
        bankTrans.setIdRegType(Integer.parseInt(lblRegType.getText()));
        bankTrans.setObservations(txtObservations.getText());
        
        String error = BankTransDAO.validate(bankTrans);
        
        if (error == null) {
            try {
                rowsAffected = BankTransDAO.update(bankTrans);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditBankTrans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
