/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.other;

import DknTime.DateTime;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppGlobal;
import system.edit.PnlEdit;
import system.edit.WEdit;

/**
 *
 * @author richneom
 */
public class PnlEditTaxes extends PnlEdit {

    private final WEdit wEdit;
    private final int id;

    /**
     * Creates new form PnlEditBank
     * @param wEdit
     * @param id
     */
    public PnlEditTaxes(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_TAXES_CODE_TEXT") + ":");
        lblName.setText(AppGlobal.getText("WEDIT_TAXES_NAME_TEXT") + ":");
        lblValue.setText(AppGlobal.getText("WEDIT_TAXES_VALUE_TEXT") + ":");
        lblDateInit.setText(AppGlobal.getText("WEDIT_TAXES_DATEINIT_TEXT") + ":");
        lblDateFinal.setText(AppGlobal.getText("WEDIT_TAXES_DATEFINAL_TEXT") + ":");
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            txtCode.setText("");
            txtName.setText("");
            txtValue.setText("0.00");
            jdcDateInit.setDate(DateTime.getNow());
            jdcDateFinal.setDate(DateTime.addDateYears(DateTime.getNow(), 1));
        }
        else {
            try {
                WindowData tax = TaxDAO.get(id);
                
                txtCode.setText(tax.getCode());
                txtName.setText(tax.getName());
                txtValue.setText(String.valueOf(tax.getValue()));
                jdcDateInit.setDate(tax.getDateInit());
                jdcDateFinal.setDate(tax.getDateFinal());
                
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("ERROR. PnlEditEnte.loadData(). No se encontrol el Ente. ID: " + id);
                Logger.getLogger(PnlEditTaxes.class.getName()).log(Level.SEVERE, null, ex);
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
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblValue = new javax.swing.JLabel();
        txtValue = new javax.swing.JTextField();
        lblDateInit = new javax.swing.JLabel();
        lblDateFinal = new javax.swing.JLabel();
        jdcDateInit = new com.toedter.calendar.JDateChooser();
        jdcDateFinal = new com.toedter.calendar.JDateChooser();

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

        lblName.setText("jLabel3");

        txtName.setText("jTextField2");

        lblValue.setText("jLabel1");

        txtValue.setText("jTextField1");

        lblDateInit.setText("jLabel1");

        lblDateFinal.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblValue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblDateFinal)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jdcDateFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblDateInit)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jdcDateInit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCode, lblDateFinal, lblDateInit, lblName, lblValue});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCode)
                                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblId))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblName)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblValue)
                                    .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDateInit))
                            .addComponent(jdcDateInit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDateFinal))
                    .addComponent(jdcDateFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private com.toedter.calendar.JDateChooser jdcDateFinal;
    private com.toedter.calendar.JDateChooser jdcDateInit;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblDateFinal;
    private javax.swing.JLabel lblDateInit;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblValue;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        WindowData tax = new WindowData();
        
        tax.setId(id);
        tax.setCode(txtCode.getText());
        tax.setName(txtName.getText());
        tax.setValue(Double.parseDouble(txtValue.getText().trim()));
        tax.setDateInit(jdcDateInit.getDate());
        tax.setDateFinal(jdcDateFinal.getDate());
        
        String error = TaxDAO.validate(tax);
        
        if (error == null) {
            try {
                rowsAffected = TaxDAO.update(tax);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditTaxes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}