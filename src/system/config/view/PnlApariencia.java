/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.config.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import system.config.AppConfig;
import system.config.AppGlobal;
import system.config.ConfigData;

/**
 *
 * @author richneom
 */
public class PnlApariencia extends javax.swing.JPanel {

    /**
     * Creates new form PnlApariencia
     */
    public PnlApariencia() {
        initComponents();
        
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        btnSave.setText(AppGlobal.getText("WOPTIONS_BTN_SAVE"));
        
        lblLookAndFeel.setText(AppGlobal.getText(ConfigData.APP_LOOK_AND_FEEL.getTitle()));
        cmbLookAndFeel.removeAllItems();
        UIManager.LookAndFeelInfo[] lista = UIManager.getInstalledLookAndFeels();
        for (int i = 0; i < lista.length; i++) {
            cmbLookAndFeel.addItem(lista[i].getName());
//            System.out.println(lista[i].getClassName());
        }
        
        lblListaColumnaID.setText(AppGlobal.getText(ConfigData.COLUMN_WIDTH_ID.getTitle()) + ":");
        lblListaColumnaFecha.setText(AppGlobal.getText(ConfigData.COLUMN_WIDTH_DATE.getTitle()) + ":");
        lblListaColumnaEntero.setText(AppGlobal.getText(ConfigData.COLUMN_WIDTH_INT.getTitle()) + ":");
        lblListaColumnaBoolean.setText(AppGlobal.getText(ConfigData.COLUMN_WIDTH_BOOL.getTitle()) + ":");
        lblListaColumnaDecCorto.setText(AppGlobal.getText(ConfigData.COLUMN_WIDTH_DOUBLE_SHORT.getTitle()) + ":");
        lblListaColumnaDecLargo.setText(AppGlobal.getText(ConfigData.COLUMN_WIDTH_DOUBLE_LONG.getTitle()) + ":");
        
        fill();
    }
    
    private void fill() {
        cmbLookAndFeel.setSelectedItem(AppConfig.getString(ConfigData.APP_LOOK_AND_FEEL));
        
        txtListaColumnaID.setText(AppConfig.getInt(ConfigData.COLUMN_WIDTH_ID) + "");
        txtListaColumnaFecha.setText(AppConfig.getInt(ConfigData.COLUMN_WIDTH_DATE) + "");
        txtListaColumnaEntero.setText(AppConfig.getInt(ConfigData.COLUMN_WIDTH_INT) + "");
        txtListaColumnaBoolean.setText(AppConfig.getInt(ConfigData.COLUMN_WIDTH_BOOL) + "");
        txtListaColumnaDecCorto.setText(AppConfig.getInt(ConfigData.COLUMN_WIDTH_DOUBLE_SHORT) + "");
        txtListaColumnaDecLargo.setText(AppConfig.getInt(ConfigData.COLUMN_WIDTH_DOUBLE_LONG) + "");
    }
    
    private void save() {
        String lookAndFeel = (String) cmbLookAndFeel.getSelectedItem();
        AppConfig.set(ConfigData.APP_LOOK_AND_FEEL, lookAndFeel);
        
        int valor = Integer.parseInt(txtListaColumnaID.getText().trim());
        AppConfig.set(ConfigData.COLUMN_WIDTH_ID, valor);
        valor = Integer.parseInt(txtListaColumnaFecha.getText().trim());
        AppConfig.set(ConfigData.COLUMN_WIDTH_DATE, valor);
        valor = Integer.parseInt(txtListaColumnaEntero.getText().trim());
        AppConfig.set(ConfigData.COLUMN_WIDTH_INT, valor);
        valor = Integer.parseInt(txtListaColumnaBoolean.getText().trim());
        AppConfig.set(ConfigData.COLUMN_WIDTH_BOOL, valor);
        valor = Integer.parseInt(txtListaColumnaDecCorto.getText().trim());
        AppConfig.set(ConfigData.COLUMN_WIDTH_DOUBLE_SHORT, valor);
        valor = Integer.parseInt(txtListaColumnaDecLargo.getText().trim());
        AppConfig.set(ConfigData.COLUMN_WIDTH_DOUBLE_LONG, valor);
        
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
        lblLookAndFeel = new javax.swing.JLabel();
        cmbLookAndFeel = new javax.swing.JComboBox<>();
        lblListaColumnaID = new javax.swing.JLabel();
        lblListaColumnaFecha = new javax.swing.JLabel();
        lblListaColumnaEntero = new javax.swing.JLabel();
        lblListaColumnaBoolean = new javax.swing.JLabel();
        lblListaColumnaDecCorto = new javax.swing.JLabel();
        lblListaColumnaDecLargo = new javax.swing.JLabel();
        txtListaColumnaID = new javax.swing.JTextField();
        txtListaColumnaFecha = new javax.swing.JTextField();
        txtListaColumnaEntero = new javax.swing.JTextField();
        txtListaColumnaBoolean = new javax.swing.JTextField();
        txtListaColumnaDecCorto = new javax.swing.JTextField();
        txtListaColumnaDecLargo = new javax.swing.JTextField();

        btnSave.setText("jButton1");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblLookAndFeel.setText("jLabel1");

        cmbLookAndFeel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblListaColumnaID.setText("jLabel1");

        lblListaColumnaFecha.setText("jLabel1");

        lblListaColumnaEntero.setText("jLabel1");

        lblListaColumnaBoolean.setText("jLabel1");

        lblListaColumnaDecCorto.setText("jLabel1");

        lblListaColumnaDecLargo.setText("jLabel1");

        txtListaColumnaID.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtListaColumnaID.setText("jTextField1");

        txtListaColumnaFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtListaColumnaFecha.setText("jTextField2");

        txtListaColumnaEntero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtListaColumnaEntero.setText("jTextField3");

        txtListaColumnaBoolean.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtListaColumnaBoolean.setText("jTextField4");

        txtListaColumnaDecCorto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtListaColumnaDecCorto.setText("jTextField5");

        txtListaColumnaDecLargo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtListaColumnaDecLargo.setText("jTextField6");

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
                                .addComponent(lblListaColumnaID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtListaColumnaID, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblListaColumnaFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtListaColumnaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblListaColumnaEntero)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtListaColumnaEntero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblListaColumnaBoolean)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtListaColumnaBoolean, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblListaColumnaDecCorto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtListaColumnaDecCorto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblListaColumnaDecLargo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtListaColumnaDecLargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLookAndFeel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbLookAndFeel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 193, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtListaColumnaBoolean, txtListaColumnaDecCorto, txtListaColumnaDecLargo, txtListaColumnaEntero, txtListaColumnaFecha, txtListaColumnaID});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblListaColumnaBoolean, lblListaColumnaDecCorto, lblListaColumnaDecLargo, lblListaColumnaEntero, lblListaColumnaFecha, lblListaColumnaID});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLookAndFeel)
                    .addComponent(cmbLookAndFeel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListaColumnaID)
                    .addComponent(txtListaColumnaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListaColumnaFecha)
                    .addComponent(txtListaColumnaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListaColumnaEntero)
                    .addComponent(txtListaColumnaEntero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListaColumnaBoolean)
                    .addComponent(txtListaColumnaBoolean, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListaColumnaDecCorto)
                    .addComponent(txtListaColumnaDecCorto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListaColumnaDecLargo)
                    .addComponent(txtListaColumnaDecLargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbLookAndFeel;
    private javax.swing.JLabel lblListaColumnaBoolean;
    private javax.swing.JLabel lblListaColumnaDecCorto;
    private javax.swing.JLabel lblListaColumnaDecLargo;
    private javax.swing.JLabel lblListaColumnaEntero;
    private javax.swing.JLabel lblListaColumnaFecha;
    private javax.swing.JLabel lblListaColumnaID;
    private javax.swing.JLabel lblLookAndFeel;
    private javax.swing.JTextField txtListaColumnaBoolean;
    private javax.swing.JTextField txtListaColumnaDecCorto;
    private javax.swing.JTextField txtListaColumnaDecLargo;
    private javax.swing.JTextField txtListaColumnaEntero;
    private javax.swing.JTextField txtListaColumnaFecha;
    private javax.swing.JTextField txtListaColumnaID;
    // End of variables declaration//GEN-END:variables
}
