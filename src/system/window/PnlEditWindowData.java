/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.window;

import system.config.AppGlobal;

/**
 *
 * @author DickNeoM
 */
public class PnlEditWindowData extends javax.swing.JPanel {
    
    /**
     * Creates new form PnlEditWindowData
     */
    public PnlEditWindowData() {
        initComponents();
        
        init();
    }
    
    public void init() {
        lblCode.setText(AppGlobal.getText("WEDIT_WINDOWS_WINDATA_CODE_TEXT") + ":");
        lblName.setText(AppGlobal.getText("WEDIT_WINDOWS_WINDATA_NAME_TEXT") + ":");
        lblTitle.setText(AppGlobal.getText("WEDIT_WINDOWS_WINDATA_TITLE_TEXT") + ":");
        lblWidth.setText(AppGlobal.getText("WEDIT_WINDOWS_WINDATA_WIDTH_TEXT") + ":");
        lblHeight.setText(AppGlobal.getText("WEDIT_WINDOWS_WINDATA_HEIGHT_TEXT") + ":");
    }
    
    public void load(WindowData window) {
        if (window == null || window.getId() < 0) {
            lblId.setText(String.valueOf(-1));
            
            txtCode.setText("");
        }
        else {
            lblId.setText(String.valueOf(window.getId()));
            
            txtCode.setText(window.getCode());
            txtName.setText(window.getName());
            txtTitle.setText(window.getTitle());
            txtWidth.setText(String.valueOf(window.getWidth()));
            txtHeight.setText(String.valueOf(window.getHeight()));
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

        lblCode = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblWidth = new javax.swing.JLabel();
        lblHeight = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtTitle = new javax.swing.JTextField();
        txtWidth = new javax.swing.JTextField();
        txtHeight = new javax.swing.JTextField();

        lblCode.setText("jLabel1");

        lblId.setText("jLabel1");

        txtCode.setText("jTextField1");

        lblName.setText("jLabel1");

        lblTitle.setText("jLabel1");

        lblWidth.setText("jLabel1");

        lblHeight.setText("jLabel1");

        txtName.setText("jTextField1");

        txtTitle.setText("jTextField2");

        txtWidth.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtWidth.setText("jTextField3");

        txtHeight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHeight.setText("jTextField4");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 323, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblHeight)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtHeight))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblWidth)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCode, lblHeight, lblName, lblTitle, lblWidth});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode)
                    .addComponent(lblId)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth)
                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeight)
                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(173, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblHeight;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWidth;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables

    public void save() {
        
    }
}
