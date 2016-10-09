/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.local;

import console.DknConsole;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import system.config.AppGlobal;
import system.edit.PnlEdit;
import system.edit.WEdit;
import system.type.Type;
import system.type.TypeDAO;
import system.type.Types;

/**
 *
 * @author richneom
 */
public class PnlEditLocal extends PnlEdit {

    private final WEdit wEdit;
    private final int id;

    /**
     * Creates new form PnlEditLocalType
     * @param wEdit
     * @param id
     */
    public PnlEditLocal(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_LOCAL_CODE_TEXT") + ":");
        lblProperty.setText(AppGlobal.getText("WEDIT_LOCAL_PROPERTY_TEXT") + ":");
        lblArea.setText(AppGlobal.getText("WEDIT_LOCAL_AREA_TEXT") + ":");
        lblRent.setText(AppGlobal.getText("WEDIT_LOCAL_RENT_TEXT") + ":");
        lblDiscount.setText(AppGlobal.getText("WEDIT_LOCAL_DISCOUNT_TEXT") + ":");
        lblFloor.setText(AppGlobal.getText("WEDIT_LOCAL_FLOOR_TEXT") + ":");
        lblIdType.setText(AppGlobal.getText("WEDIT_LOCAL_IDTYPE_TEXT") + ":");
        lblNotes.setText(AppGlobal.getText("WEDIT_LOCAL_NOTES_TEXT") + ":");
        
        cmbIdType.removeAllItems();
        Types localTypes = null;
        try {
            localTypes = TypeDAO.gets("localtypes", "name");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Type localType : localTypes) {
            cmbIdType.addItem(localType.getName());
        }
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            txtCode.setText("");
            txtProperty.setText("");
            txtArea.setText("0.00");
            txtRent.setText("0.00");
            txtDiscount.setText("0.00");
            spnFloor.setValue(1);
            
            txtNotes.setText("");
        }
        else {
            try {
                Local local = LocalDAO.get(id);
                
                txtCode.setText(local.getCode());
                txtProperty.setText(local.getProperty());
                txtArea.setText(AppGlobal.getFormatDecimalShort().format(local.getArea()));
                txtRent.setText(AppGlobal.getFormatDecimalShort().format(local.getRent()));
                txtDiscount.setText(AppGlobal.getFormatDecimalShort().format(local.getDiscount()));
                spnFloor.setValue(local.getFloor());
                
                Type localType = TypeDAO.get("localtypes", local.getIdType());
                if (localType != null) {
                    cmbIdType.setSelectedItem(localType.getName());
                }
                
                txtNotes.setText(local.getNotes());
            } catch (ClassNotFoundException | SQLException ex) {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se encontrol el Local. ID: " + id);
                Logger.getLogger(PnlEditLocal.class.getName()).log(Level.SEVERE, null, ex);
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
        lblProperty = new javax.swing.JLabel();
        txtProperty = new javax.swing.JTextField();
        lblArea = new javax.swing.JLabel();
        txtArea = new javax.swing.JTextField();
        lblRent = new javax.swing.JLabel();
        txtRent = new javax.swing.JTextField();
        lblDiscount = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        lblFloor = new javax.swing.JLabel();
        lblIdType = new javax.swing.JLabel();
        cmbIdType = new javax.swing.JComboBox<>();
        lblNotes = new javax.swing.JLabel();
        spnFloor = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();

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

        lblProperty.setText("jLabel3");

        txtProperty.setText("jTextField2");

        lblArea.setText("jLabel4");

        txtArea.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtArea.setText("jTextField3");

        lblRent.setText("jLabel1");

        txtRent.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtRent.setText("jTextField1");

        lblDiscount.setText("jLabel1");

        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtDiscount.setText("jTextField1");

        lblFloor.setText("jLabel2");

        lblIdType.setText("jLabel2");

        cmbIdType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblNotes.setText("jLabel2");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        jScrollPane1.setViewportView(txtNotes);

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCode))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblProperty)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProperty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIdType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbIdType, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblArea)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtArea))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblFloor)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(spnFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblRent)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtRent, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblDiscount)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDiscount)))
                            .addComponent(lblNotes))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblArea, lblCode, lblDiscount, lblFloor, lblIdType, lblProperty, lblRent});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProperty)
                    .addComponent(txtProperty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArea)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRent)
                    .addComponent(txtRent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiscount)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFloor)
                    .addComponent(spnFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdType)
                    .addComponent(cmbIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cmbIdType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblFloor;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIdType;
    private javax.swing.JLabel lblNotes;
    private javax.swing.JLabel lblProperty;
    private javax.swing.JLabel lblRent;
    private javax.swing.JSpinner spnFloor;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextArea txtNotes;
    private javax.swing.JTextField txtProperty;
    private javax.swing.JTextField txtRent;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), "Grabando local.");
        Local local = new Local();
        
        local.setId(id);
        local.setCode(txtCode.getText());
        local.setProperty(txtProperty.getText());
        if (txtArea.getText() != null && !txtArea.getText().isEmpty()) {
            local.setArea(Double.parseDouble(txtArea.getText()));
        }
        else {
            local.setArea(0);
        }
        if (txtRent.getText() != null && !txtRent.getText().isEmpty()) {
            local.setRent(Double.parseDouble(txtRent.getText()));
        }
        else {
            local.setRent(0);
        }
        if (txtDiscount.getText() != null && !txtDiscount.getText().isEmpty()) {
            local.setDiscount(Double.parseDouble(txtDiscount.getText()));
        }
        else {
            local.setDiscount(0);
        }
        
        local.setFloor((int) spnFloor.getValue());
        local.setNotes(txtNotes.getText());
        
        String typeStr = (String) cmbIdType.getSelectedItem();
        Type localType = null;
        try {
            localType = TypeDAO.get("localtypes", "name", typeStr);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (localType != null) {
            local.setIdType(localType.getId());
        }
        
        String error = null;
        try {
            error = LocalDAO.validate(local);
            if (error == null) {
                rowsAffected = LocalDAO.update(local);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(wEdit, error, AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PnlEditLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}