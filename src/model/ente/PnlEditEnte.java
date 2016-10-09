/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ente;

import console.DknConsole;
import java.sql.SQLException;
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
public class PnlEditEnte extends PnlEdit {

    private final WEdit wEdit;
    private final int id;
    private boolean addressActive;
    private boolean addressBilling;
    private int addressId;

    /**
     * Creates new form PnlEditBank
     * @param wEdit
     * @param id
     */
    public PnlEditEnte(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_CUSTOMERS_CODE_TEXT") + ":");
        lblName.setText(AppGlobal.getText("WEDIT_CUSTOMERS_NAME_TEXT") + ":");
        lblSurname.setText(AppGlobal.getText("WEDIT_CUSTOMERS_SURNAME_TEXT") + ":");
        lblDni.setText(AppGlobal.getText("WEDIT_CUSTOMERS_DNI_TEXT") + ":");
        lblEnteType.setText(AppGlobal.getText("WEDIT_CUSTOMERS_ENTETYPE_TEXT") + ":");
        lblNotes.setText(AppGlobal.getText("WEDIT_CUSTOMERS_NOTES_TEXT") + ":");
        chkActive.setText(AppGlobal.getText("WEDIT_CUSTOMERS_ACTIVE_TEXT"));
        
        lblCountry.setText(AppGlobal.getText("WEDIT_CUSTOMERS_COUNTRY_TEXT"));
        lblState.setText(AppGlobal.getText("WEDIT_CUSTOMERS_STATE_TEXT"));
        lblCity.setText(AppGlobal.getText("WEDIT_CUSTOMERS_CITY_TEXT"));
        lblMainStreet.setText(AppGlobal.getText("WEDIT_CUSTOMERS_MAINSTREET_TEXT"));
        lblNumber.setText(AppGlobal.getText("WEDIT_CUSTOMERS_NUMBER_TEXT"));
        lblStreet.setText(AppGlobal.getText("WEDIT_CUSTOMERS_STREET_TEXT"));
        lblReference.setText(AppGlobal.getText("WEDIT_CUSTOMERS_REFERENCE_TEXT"));
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
        
        cmbDniType.removeAllItems();
        try {
            Types types = TypeDAO.gets("dnitypes", "name");
            
            if (types != null) {
                for (Type type : types)
                cmbDniType.addItem(type.getName());
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditEnte.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cmbEnteType.removeAllItems();
        try {
            Types types = TypeDAO.gets("entestypes", "name");
            if (types != null) {
                for (Type type : types) {
                    cmbEnteType.addItem(type.getName());
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditEnte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected final void loadData() {
        lblId.setText(String.valueOf(id));
        if (id < 1) {
            txtCode.setText("");
            txtName.setText("");
            txtSurname.setText("");
            txtDni.setText("");
            txtNotes.setText("");
            chkActive.setSelected(true);
            
            // Dirección
            addressId = -1;

            txtCountry.setText("593");
            txtState.setText("");
            txtCity.setText("");
            txtMainStreet.setText("");
            txtNumber.setText("");
            txtStreet.setText("");
            txtReference.setText("");

            addressActive = true;
            addressBilling = true;
        }
        else {
            try {
                Ente ente = EnteDAO.get(id);
                
                txtCode.setText(ente.getCode());
                txtName.setText(ente.getName());
                txtSurname.setText(ente.getSurname());
                Type type = TypeDAO.get("dnitypes", ente.getIdDniType());
                if (type != null) {
                    cmbDniType.setSelectedItem(type.getName());
                }
                else {
                    DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Tipo de DNI no encontrado. id: " + ente.getIdDniType());
                }
                txtDni.setText(ente.getDni());
                
                type = TypeDAO.get("entestypes", ente.getIdType());
                if (type != null) {
                    cmbEnteType.setSelectedItem(type.getName());
                }
                else {
                    DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Tipo de ente no encontrado. id: " + ente.getIdType());
                }
                
                txtNotes.setText(ente.getNote());
                chkActive.setSelected(ente.isActive());
                
                // Dirección
                addressId = ente.getAddress().getId();
                
                txtCountry.setText(String.valueOf(ente.getAddress().getIdCountry()));
                txtState.setText(ente.getAddress().getState());
                txtCity.setText(ente.getAddress().getCity());
                txtMainStreet.setText(ente.getAddress().getMainStreet());
                txtNumber.setText(ente.getAddress().getNumber());
                txtStreet.setText(ente.getAddress().getStreet());
                txtReference.setText(ente.getAddress().getReference());
                
                addressActive = ente.getAddress().isActive();
                addressBilling = ente.getAddress().isBilling();
                
            } catch (ClassNotFoundException | SQLException ex) {
                DknConsole.error(Thread.currentThread().getStackTrace()[1].toString(), "No se pudo recuperar los datos del Ente. ID: " + id);
                Logger.getLogger(PnlEditEnte.class.getName()).log(Level.SEVERE, null, ex);
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
        lblNotes = new javax.swing.JLabel();
        chkActive = new javax.swing.JCheckBox();
        lblSurname = new javax.swing.JLabel();
        txtSurname = new javax.swing.JTextField();
        lblDni = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        cmbDniType = new javax.swing.JComboBox<>();
        lblEnteType = new javax.swing.JLabel();
        cmbEnteType = new javax.swing.JComboBox<>();
        lblCountry = new javax.swing.JLabel();
        lblState = new javax.swing.JLabel();
        lblCity = new javax.swing.JLabel();
        lblMainStreet = new javax.swing.JLabel();
        lblNumber = new javax.swing.JLabel();
        lblStreet = new javax.swing.JLabel();
        lblReference = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextArea();
        txtCountry = new javax.swing.JTextField();
        txtState = new javax.swing.JTextField();
        txtCity = new javax.swing.JTextField();
        txtMainStreet = new javax.swing.JTextField();
        txtNumber = new javax.swing.JTextField();
        txtStreet = new javax.swing.JTextField();
        txtReference = new javax.swing.JTextField();

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

        lblNotes.setText("jLabel4");

        chkActive.setText("jCheckBox1");

        lblSurname.setText("jLabel1");

        txtSurname.setText("jTextField1");

        lblDni.setText("jLabel1");

        txtDni.setText("jTextField1");

        cmbDniType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEnteType.setText("jLabel1");

        cmbEnteType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCountry.setText("jLabel1");

        lblState.setText("jLabel2");

        lblCity.setText("jLabel1");

        lblMainStreet.setText("jLabel1");

        lblNumber.setText("jLabel1");

        lblStreet.setText("jLabel1");

        lblReference.setText("jLabel1");

        txtNotes.setColumns(20);
        txtNotes.setRows(5);
        jScrollPane1.setViewportView(txtNotes);

        txtCountry.setEditable(false);
        txtCountry.setText("jTextField1");

        txtState.setText("jTextField2");

        txtCity.setText("jTextField3");

        txtMainStreet.setText("jTextField4");

        txtNumber.setText("jTextField5");

        txtStreet.setText("jTextField6");

        txtReference.setText("jTextField7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblSurname)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(chkActive))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDni)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbDniType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblEnteType)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbEnteType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNotes)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblCity)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCity))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblState)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtState))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblCountry)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblReference)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtReference))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblStreet)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtStreet))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblMainStreet)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtMainStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCity, lblCode, lblCountry, lblDni, lblEnteType, lblMainStreet, lblName, lblNumber, lblReference, lblState, lblStreet, lblSurname});

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
                    .addComponent(lblSurname)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDni)
                    .addComponent(cmbDniType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEnteType)
                    .addComponent(cmbEnteType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCountry)
                    .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblState)
                    .addComponent(txtState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCity)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMainStreet)
                    .addComponent(txtMainStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStreet)
                    .addComponent(txtStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReference)
                    .addComponent(txtReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
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
    private javax.swing.JCheckBox chkActive;
    private javax.swing.JComboBox<String> cmbDniType;
    private javax.swing.JComboBox<String> cmbEnteType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblEnteType;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblMainStreet;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNotes;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblReference;
    private javax.swing.JLabel lblState;
    private javax.swing.JLabel lblStreet;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtMainStreet;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtNotes;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtReference;
    private javax.swing.JTextField txtState;
    private javax.swing.JTextField txtStreet;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        Ente ente = new Ente();
        
        ente.setId(id);
        ente.setCode(txtCode.getText());
        ente.setName(txtName.getText());
        ente.setSurname(txtSurname.getText());
        
        String dniTypeStr = (String) cmbDniType.getSelectedItem();
        Type type = null;
        try {
            type = TypeDAO.get("dnitypes", "name", dniTypeStr);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditEnte.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (type != null) {
            ente.setIdDniType(type.getId());
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. Tipo de DNI no encontrado");
        }
        
        ente.setDni(txtDni.getText());
        
        String typeStr = (String) cmbEnteType.getSelectedItem();
        type = null;
        try {
            type = TypeDAO.get("entestypes", "name", typeStr);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PnlEditEnte.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (type != null) {
            ente.setIdType(type.getId());
        }
        else {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "ERROR. Tipo no encontrado");
        }
        
        ente.setNote(txtNotes.getText());
        ente.setActive(chkActive.isSelected());
        
        // Direccción
        ente.getAddress().setId(addressId);
        
        ente.getAddress().setIdCountry(Integer.parseInt(txtCountry.getText()));
        ente.getAddress().setState(txtState.getText());
        ente.getAddress().setCity(txtCity.getText());
        ente.getAddress().setMainStreet(txtMainStreet.getText());
        ente.getAddress().setNumber(txtNumber.getText());
        ente.getAddress().setStreet(txtStreet.getText());
        ente.getAddress().setReference(txtReference.getText());
        
        ente.getAddress().setActive(addressActive);
        ente.getAddress().setBilling(addressBilling);
        
        String error = EnteDAO.validate(ente);
        
        if (error == null) {
            try {
                rowsAffected = EnteDAO.update(ente);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditEnte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
