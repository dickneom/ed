/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.config.view;

import DknFile.Archivo;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import system.config.AppConfig;
import system.config.AppGlobal;
import system.config.ConfigData;

/**
 *
 * @author DickNeoM
 */
public class PnlDatos extends javax.swing.JPanel {

    /**
     * Creates new form PnlDatos
     */
    public PnlDatos() {
        initComponents();
        
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        btnSave.setText(AppGlobal.getText("WOPTIONS_BTN_SAVE"));
        
        lblDirData.setText(AppGlobal.getText(ConfigData.DBASE_DIR_DATA.getTitle()) + ":");
        lblDBaseFile.setText(AppGlobal.getText(ConfigData.DBASE_DATA_FILE.getTitle()) + ":");
        lblUser.setText(AppGlobal.getText(ConfigData.DBASE_DATA_USER.getTitle()) + ":");
        lblPassword.setText(AppGlobal.getText(ConfigData.DBASE_DATA_PASSWORD.getTitle()) + ":");
        
        fill();
    }
    
    private void fill() {
        txtDirData.setText(AppConfig.getString(ConfigData.DBASE_DIR_DATA));
        txtDBaseFile.setText(AppConfig.getString(ConfigData.DBASE_DATA_FILE));
        txtUser.setText(AppConfig.getString(ConfigData.DBASE_DATA_USER));
        txtPassword.setText(AppConfig.getString(ConfigData.DBASE_DATA_PASSWORD));
    }
    
    private void save() {
        String valorStr = txtDirData.getText().trim();
        AppConfig.set(ConfigData.DBASE_DIR_DATA, valorStr);
        valorStr = txtDBaseFile.getText().trim();
        AppConfig.set(ConfigData.DBASE_DATA_FILE, valorStr);
        valorStr = txtUser.getText().trim();
        AppConfig.set(ConfigData.DBASE_DATA_USER, valorStr);
        valorStr = txtPassword.getText().trim();
        AppConfig.set(ConfigData.DBASE_DATA_PASSWORD, valorStr);
        
        try {
            AppConfig.save();
            
            JOptionPane.showMessageDialog(this, AppGlobal.getText("WOPTIONS_MSG_SAVED_TEXT"), AppGlobal.getText("WOPTIONS_MSG_SAVED_TITLE"), JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(PnlApariencia.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, AppGlobal.getText("WOPTIONS_MSG_NOSAVED_TEXT"), AppGlobal.getText("WOPTIONS_MSG_NOSAVED_TITLE"), JOptionPane.WARNING_MESSAGE);
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

        btnSave = new javax.swing.JButton();
        lblDirData = new javax.swing.JLabel();
        lblDBaseFile = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtDirData = new javax.swing.JTextField();
        txtDBaseFile = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        btnDataBase = new javax.swing.JButton();

        btnSave.setText("jButton1");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblDirData.setText("jLabel1");

        lblDBaseFile.setText("jLabel2");

        lblUser.setText("jLabel3");

        lblPassword.setText("jLabel4");

        txtDirData.setText("jTextField1");

        txtDBaseFile.setText("jTextField2");

        txtUser.setText("jTextField3");

        txtPassword.setText("jTextField4");

        btnDataBase.setText("...");
        btnDataBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataBaseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDBaseFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDBaseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPassword)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDirData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDirData, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDataBase)))
                        .addGap(0, 106, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblDBaseFile, lblDirData, lblPassword, lblUser});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirData)
                    .addComponent(txtDirData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDataBase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDBaseFile)
                    .addComponent(txtDBaseFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDataBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataBaseActionPerformed
        File file = Archivo.obtenerArchivo(this, null, AppConfig.getString(ConfigData.APP_DIR_WORK), Archivo.OpenMode.READ);
        
        if (file != null) {
            String fileName = file.getAbsolutePath();
            String name = file.getName();
            String dir = "";
            int pos = fileName.lastIndexOf("/");
            if (pos == 0)
                pos = fileName.lastIndexOf("\\");
            if (pos != 0)
                dir = fileName.substring(0, pos + 1);
            System.out.println("Dir: " + dir + ", Name: " + name);
            System.out.println("Dir: " + file.getPath() + ", Name: " + file.getName());
            txtDirData.setText(dir);
            txtDBaseFile.setText(name);
        }
    }//GEN-LAST:event_btnDataBaseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDataBase;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel lblDBaseFile;
    private javax.swing.JLabel lblDirData;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUser;
    private javax.swing.JTextField txtDBaseFile;
    private javax.swing.JTextField txtDirData;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
