/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.window;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.config.AppGlobal;
import system.edit.PnlEdit;
import system.edit.WEdit;

/**
 *
 * @author DickNeoM
 */
public class PnlEditWindow extends PnlEdit {

    private final WEdit wEdit;
    private final int id;
    private PnlEditWindowData pnlWindowData;
    private WindowData window;
    private PnlEditWindowButtons pnlWindowButtons;
    private PnlEditWindowSerchFields pnlWindowFieldsSearch;

    /**
     * Creates new form PnlEditLocalType
     * @param wEdit
     * @param id
     */
    public PnlEditWindow(WEdit wEdit, int id) {
        initComponents();
        this.wEdit = wEdit;
        this.id = id;
        
        init();
        
        loadData();
    }
    
    @Override
    protected final void init() {
        pnlWindowData = new PnlEditWindowData();
        pnlWindowButtons = new PnlEditWindowButtons();
        pnlWindowFieldsSearch = new PnlEditWindowSerchFields();
        pnlTabs.add(pnlWindowData, AppGlobal.getText("WEDIT_WINDOWS_WINDATA_TAB_TITLE"));
        pnlTabs.add(pnlWindowButtons, AppGlobal.getText("WEDIT_WINDOWS_WINBUTTONS_TAB_TITLE"));
        pnlTabs.add(pnlWindowFieldsSearch, AppGlobal.getText("WEDIT_WINDOWS_WINFIELDSEARCH_TAB_TITLE"));
        
        btnOk.setText(AppGlobal.getText("WEDIT_BTN_OK"));
        btnCancel.setText(AppGlobal.getText("WEDIT_BTN_CANCEL"));
    }
    
    @Override
    protected final void loadData() {
        window = null;
        
        if (id < 1) {
            window = new WindowData();
        }
        else {
            try {
                window = WindowDAO.get(id);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(PnlEditWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        pnlWindowData.load(window);
        pnlWindowButtons.load(window);
        pnlWindowFieldsSearch.load(window);

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
        pnlTabs = new javax.swing.JTabbedPane();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(265, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addContainerGap())
            .addComponent(pnlTabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
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
        window = null;
        
        this.ok = false;
        
        wEdit.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JTabbedPane pnlTabs;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void save() {
        String error = WindowDAO.validate(window);
        
        if (error == null) {
            try {
                rowsAffected = WindowDAO.update(window);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PnlEditWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void showData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
