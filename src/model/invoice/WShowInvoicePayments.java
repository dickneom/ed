/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.invoice;

import DknTime.DateTime;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import messages.VError;
import messages.VMessage;
import model.contract.ContractDAO;
import model.ente.Ente;
import model.ente.EnteDAO;
import system.config.AppConfig;
import system.config.AppGlobal;
import system.config.ConfigData;
import system.data.DAOSQL;

/**
 *
 * @author DickNeoM
 */
public class WShowInvoicePayments extends JDialog {
    private final int idInvoice;
    private DefaultTableModel modelo;
    
    private double totalInvoice;
    private double totalAbonado;
    private double saldo;

    /**
     * Creates new form VEditAbonos
     * @param parent
     * @param modal
     * @param idInvoice
     */
    public WShowInvoicePayments(java.awt.Dialog parent, boolean modal, int idInvoice) {
        super(parent, modal);
        initComponents();
        
        ((JPanel) this.getContentPane()).setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        Invoice invoice = null;
        try {
            invoice = InvoiceDAO.get(idInvoice);
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            Logger.getLogger(WShowInvoicePayments.class.getName()).log(Level.SEVERE, null, ex);
        }
        String titulo = AppGlobal.getText("WSHOWINVOICEPAYMENTS_TITLE");
        
        setTitle(titulo);
        setLocationRelativeTo(parent);
        
        this.idInvoice = idInvoice;
        
        init();
        load(invoice);
    }
    
    private void init() {
        lblDate.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_LBL_INVOICEDATE_TEXT") + ":");
        lblInvoiceNumber.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_LBL_INVOICENUMBER_TEXT") + ":");
        lblInvoiceTotal.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_LBL_INVOICETOTAL_TEXT") + ":");
        lblInvoiceCustomer.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_LBL_INVOICECUSTOMER_TEXT") + ":");
        
        lblFormatoFecha.setText(AppGlobal.getFormatDate());
        
        modelo = (DefaultTableModel) tblPayments.getModel();
        
        lblPaymentsTotal.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_LBL_PAYMENTSTOTAL_TEXT") + ":");
        txtInvoiceBalance.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_LBL_INVOICEBALANCE_TEXT") + ":");
        
        btnClose.setText(AppGlobal.getText("WSHOWINVOICEPAYMENTS_BTN_CLOSE_TEXT"));
    }
    
    private void load(Invoice invoice) {
        if (invoice != null) {
            if (invoice.isCanceled()) {
                VMessage.show(this, "Factura anulada.", "Aviso");
            }
            totalAbonado = 0.0;
            
            txtInvoiceDate.setText(DateTime.getDateUtilToString(invoice.getDate(), AppGlobal.getFormatDate()));
            txtInvoiceNumber.setText(invoice.getFullNumber(AppConfig.getInt(ConfigData.INVOICES_NUMBER_TAM)));
            totalInvoice = InvoiceDAO.getTotal(invoice);
            txtInvoiceTotal.setText(AppGlobal.getFormatDecimalShort().format(totalInvoice));
            try {
                if (invoice.getIdContract() > 0) {
                    Ente customer = EnteDAO.get(ContractDAO.get(invoice.getIdContract()).getIdCustomer());
                    txtInvoiceCustomer.setText(customer.getDni() + " - " + customer.getFullName());
                }
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(WShowInvoicePayments.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String sql = "SELECT * FROM vpayments WHERE idinvoice = " + idInvoice;
            try (Connection con = DAOSQL.getConection(AppGlobal.getDataBase());
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                modelo.setRowCount(0);

                while (rs.next()) {
                    Object[] fila = new Object[6];
                    
                    double value = rs.getDouble("value");
                    
                    fila[0] = rs.getString("date");
                    fila[1] = rs.getDouble("value");
                    fila[2] = rs.getString("paymentname");
                    fila[3] = rs.getString("paynumber");
                    fila[4] = rs.getString("paydate");
                    fila[5] = rs.getDouble("payvalue");

                    totalAbonado += value;

                    modelo.addRow(fila);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                VError.show(this, ex);
                Logger.getLogger(WShowInvoicePayments.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            txtPaymetsTotal.setText(AppGlobal.getFormatDecimalShort().format(totalAbonado));
            saldo = totalInvoice - totalAbonado;
            txtInvoiceBalance.setText(AppGlobal.getFormatDecimalShort().format(saldo));
        }
        
        lblId.setText(String.valueOf(idInvoice));
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblDate = new javax.swing.JLabel();
        lblInvoiceNumber = new javax.swing.JLabel();
        txtInvoiceDate = new javax.swing.JTextField();
        txtInvoiceNumber = new javax.swing.JTextField();
        lblFormatoFecha = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtInvoiceTotal = new javax.swing.JTextField();
        lblInvoiceTotal = new javax.swing.JLabel();
        lblInvoiceCustomer = new javax.swing.JLabel();
        txtInvoiceCustomer = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPayments = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblPaymentsTotal = new javax.swing.JLabel();
        txtPaymetsTotal = new javax.swing.JTextField();
        txtInvoiceBalance = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        lblDate.setText("Fecha:");

        lblInvoiceNumber.setText("Factura:");

        txtInvoiceDate.setEditable(false);

        txtInvoiceNumber.setEditable(false);

        lblFormatoFecha.setText("jLabel3");

        lblId.setText("jLabel4");

        txtInvoiceTotal.setEditable(false);
        txtInvoiceTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblInvoiceTotal.setText("Total:");

        lblInvoiceCustomer.setText("Cliente:");

        txtInvoiceCustomer.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFormatoFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblInvoiceNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                        .addComponent(lblInvoiceTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblInvoiceCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvoiceCustomer)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblDate, lblInvoiceCustomer, lblInvoiceNumber, lblInvoiceTotal});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDate)
                        .addComponent(txtInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFormatoFecha))
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblInvoiceNumber)
                        .addComponent(txtInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblInvoiceTotal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInvoiceCustomer)
                    .addComponent(txtInvoiceCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);

        tblPayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Valor", "Tipo Doc.", "Documento", "Doc Fecha", "Doc Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPayments.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPayments.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblPayments);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2);

        lblPaymentsTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPaymentsTotal.setText("Total:");

        txtPaymetsTotal.setEditable(false);
        txtPaymetsTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtInvoiceBalance.setEditable(false);
        txtInvoiceBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Saldo:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPaymentsTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPaymetsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInvoiceBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel8, lblPaymentsTotal});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentsTotal)
                    .addComponent(txtPaymetsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3);

        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(629, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
        );

        getContentPane().add(jPanel4);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblFormatoFecha;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblInvoiceCustomer;
    private javax.swing.JLabel lblInvoiceNumber;
    private javax.swing.JLabel lblInvoiceTotal;
    private javax.swing.JLabel lblPaymentsTotal;
    private javax.swing.JTable tblPayments;
    private javax.swing.JTextField txtInvoiceBalance;
    private javax.swing.JTextField txtInvoiceCustomer;
    private javax.swing.JTextField txtInvoiceDate;
    private javax.swing.JTextField txtInvoiceNumber;
    private javax.swing.JTextField txtInvoiceTotal;
    private javax.swing.JTextField txtPaymetsTotal;
    // End of variables declaration//GEN-END:variables
}
