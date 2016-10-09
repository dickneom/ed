/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.type;

import console.DknConsole;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppGlobal;
import system.edit.PnlEdit;
import system.edit.WEdit;
import system.window.WindowData;

/**
 *
 * @author richneom
 */
public class PnlEditType extends PnlEdit {

    private final WEdit wEdit;
    private final WindowData window;
    private final int id;

    /**
     * Creates new form PnlEditLocalType
     * @param wEdit
     * @param window
     * @param id
     */
    public PnlEditType(WEdit wEdit, WindowData window, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.window = window;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_TYPES_CODE_TEXT") + ":");
        lblName.setText(AppGlobal.getText("WEDIT_TYPES_NAME_TEXT") + ":");
        lblDescription.setText(AppGlobal.getText("WEDIT_TYPES_DESCRIPTION_TEXT") + ":");
        chkActive.setText(AppGlobal.getText("WEDIT_TYPES_ACTIVE_TEXT"));
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            txtCode.setText("");
            txtName.setText("");
            txtDescription.setText("");
            chkActive.setSelected(true);
        }
        else {
            try {
                Type dt = TypeDAO.get(window.getTable(), id);
                
                txtCode.setText(dt.getCode());
                txtName.setText(dt.getName());
                txtDescription.setText(dt.getDescription());
                chkActive.setSelected(dt.isActive());
                
            } catch (ClassNotFoundException | SQLException ex) {
                DknConsole.print(Thread.currentThread().getStackTrace()[1].toString());
                DknConsole.error(" ERROR. No se encontro el Tipo. Tabla: " + window.getTable() + " ID: " + id);
                Logger.getLogger(PnlEditType.class.getName()).log(Level.SEVERE, null, ex);
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
        lblDescription = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        chkActive = new javax.swing.JCheckBox();

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

        lblDescription.setText("jLabel4");

        txtDescription.setText("jTextField3");

        chkActive.setText("jCheckBox1");

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
                        .addComponent(lblCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(chkActive))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescription)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCode, lblDescription, lblName});

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
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescription)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JCheckBox chkActive;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        Type type = new Type();
        
        type.setId(id);
        type.setCode(txtCode.getText());
        type.setName(txtName.getText());
        type.setDescription(txtDescription.getText());
        type.setActive(chkActive.isSelected());
        
        String error = TypeDAO.validate(window.getTable(), type);
        
        if (error == null) {
            try {
                rowsAffected = TypeDAO.update(window.getTable(), type);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditType.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
