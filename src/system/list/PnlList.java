/*
 * Muestra una lista de registros obtenidos desde una base de datos.
 * En la primera columna de la tabla debe esta el campo de identificación "id".
 */
package system.list;

import console.DknConsole;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;
import system.config.AppGlobal;
import system.data.DAOSQL;
import system.window.WindowButton;
import system.window.WindowData;
import system.window.WindowFieldSearch;
import system.window.WindowFieldTable;
import table.FormatoTabla;

/**
 *
 * @author DickNeoM
 */
public class PnlList extends javax.swing.JPanel {

    private boolean ok;
    private int idSelected;
    private final WindowData window;
    private final WList wList;
    private DefaultTableModel model;
    private final PnlListController control;

    /**
     * Creates new form PnlList
     * @param wList
     * @param window
     */
    public PnlList(WList wList, WindowData window) {
        initComponents();
        this.wList = wList;
        this.window = window;
        
        this.control = new PnlListController(wList, this, window);
        
        init();
    }
    
    private void init() {
        this.model = (DefaultTableModel) this.tblData.getModel();
        
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        lblSearch.setText(AppGlobal.getText("WLIST_LBL_SEARCH_TEXT") + ":");
        btnSearch.setText(AppGlobal.getText("WLIST_BTN_SEARCH_TEXT"));
        chkGlobal.setText(AppGlobal.getText("WLIST_CHK_GLOBAL_TEXT"));

        
        if (window != null) {
            // Llenar el combo de busqueda
            initFieldsSearch();
            
            // Columnas de la tabla
            initTable();
            
            // Botones para edición y otros
            initButtons();
            
            if (window.isLoadDataStart()) {
                cargarDatos();
            }
            
            txtSearch.requestFocus();
        }
    }
    
    private void initFieldsSearch() {
        this.cmbSearch.removeAllItems();
        for (WindowFieldSearch field : window.getFieldsSearch()) {
            if (field.isSearch()) {
                this.cmbSearch.addItem(field.getTitle());
            }
        }
    }
    
    private void initTable() {
        tblData.setAutoCreateRowSorter(true); // Para poder ordenar al dar click en las cabezeras e las columnas
        
//        DknConsole.debug(Thread.currentThread().getStackTrace()[1].toString(), "Columna cambia color: " + window.getColChangeColor());
        if (window.getColChangeColor() >= 0) {
            tblData.setDefaultRenderer (Object.class, new FormatoTabla(window.getColChangeColor(), window.getValueChangeColor() != 0));
        }
        else {
            tblData.setDefaultRenderer (Object.class, new FormatoTabla());
        }
        
        // Agregar las columnas a la tabla
        this.model.setColumnCount(0);
        for (WindowFieldTable field : window.getFieldsTable()) {
            this.model.addColumn(field.getTitle());
        }
        // Configurar el ancho de las columnas
        for (int i = 0; i < window.getFieldsTable().size(); i++) {
            if (window.getFieldTable(i).isVisible()) {
                setColWidth(i, window.getFieldTable(i).getColumnWidth(), window.getFieldTable(i).getColumnWidthMax(), window.getFieldTable(i).getColumnWidthMin());
            }
            else {
                this.tblData.getColumnModel().getColumn(i).setResizable(false);
                setColWidth(i, 0, 0, 0);
                this.tblData.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
                this.tblData.getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);
            }
        }
        // Borrar el contenido de la tabla
        this.model.setRowCount(0);
    }
    
    private void initButtons() {
        for (WindowButton btn : window.getButtons()) {
            if (btn.getCode().toUpperCase().equals("SEPARATOR")) {
                tlbButtons.add(new JSeparator(JSeparator.HORIZONTAL));
            }
            else {
                JButton button = new JButton();

//                System.out.println("Agregando boton: " + btn.getCode());
                button.setName(btn.getCode());
                button.setText(btn.getTitle());
                button.setActionCommand(btn.getCommand());
                button.addActionListener(control);
                if (btn.getHeight() > 0 && btn.getWidth() > 0) {
                    int btnWidth = 0, btnHeight = 0;
                    btnHeight = btn.getHeight();
                    btnWidth = btn.getWidth();
                    button.setSize(btnWidth, btnHeight);
                }

                if (btn.isVisible()) {
                    button.setVisible(true);
                }
                else {
                    button.setVisible(false);
                }

                tlbButtons.add(button);
            }
        }
    }
    
    public boolean isOk() {
        return this.ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    
    public int getIdSelected() {
        return this.idSelected;
    }

    public void setIdSelected(int idSelected) {
        this.idSelected = idSelected;
    }
    
    public void setEstado(String estado) {
        lblState.setText(estado);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSearch = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        cmbSearch = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        chkGlobal = new javax.swing.JCheckBox();
        pnlState = new javax.swing.JPanel();
        lblState = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        tlbButtons = new javax.swing.JToolBar();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnlSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblSearch.setText("jLabel1");

        cmbSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setText("jButton1");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        chkGlobal.setSelected(true);
        chkGlobal.setText("jCheckBox1");

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkGlobal)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(chkGlobal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(pnlSearch, java.awt.BorderLayout.PAGE_START);

        pnlState.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblState.setText("jLabel1");

        javax.swing.GroupLayout pnlStateLayout = new javax.swing.GroupLayout(pnlState);
        pnlState.setLayout(pnlStateLayout);
        pnlStateLayout.setHorizontalGroup(
            pnlStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblState, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );
        pnlStateLayout.setVerticalGroup(
            pnlStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblState)
        );

        add(pnlState, java.awt.BorderLayout.PAGE_END);

        pnlButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tlbButtons.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tlbButtons.setRollover(true);

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tlbButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tlbButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
        );

        add(pnlButtons, java.awt.BorderLayout.LINE_END);

        pnlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblData.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblData);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
        );

        add(pnlList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        cargarDatos();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        cargarDatos();
    }//GEN-LAST:event_txtSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JCheckBox chkGlobal;
    private javax.swing.JComboBox<String> cmbSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblState;
    public javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlState;
    private javax.swing.JTable tblData;
    public javax.swing.JToolBar tlbButtons;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables


    /**
     * Devuelve el id del registro de la fila actual
     * @return 
     */
    public int getId() {
        int id = -1;
        
        int row = tblData.getSelectedRow();
        if (row >= 0) {
            id = (int) tblData.getValueAt(row, 0);
        }
        else {
            JOptionPane.showMessageDialog(this, AppGlobal.getText("WLIST_MSG_ERROR_NOREGISTERSELECT_TEXT"),
                    AppGlobal.getText("WMSG_ERROR_TITLE"), JOptionPane.WARNING_MESSAGE);
        }
        
        return id;
    }
    
    @Deprecated
    protected void setColWidth(int colIndex, int width) {
        setColWidth(colIndex, width, width * 4, width * 0);
    }
    
    protected void setColWidth(int colIndex, int width, int widthMax, int withMin) {
        this.tblData.getColumnModel().getColumn(colIndex).setMaxWidth(widthMax);
        this.tblData.getColumnModel().getColumn(colIndex).setMinWidth(withMin);
        this.tblData.getColumnModel().getColumn(colIndex).setPreferredWidth(width);
        this.tblData.getColumnModel().getColumn(colIndex).setWidth(width);
    }
    
    protected void cargarDatos() {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Cargando datos para listar.");
        String sql = getSql();
        
        try {
            int rowCount;
            try (Connection con = DAOSQL.getConection(window.getDataBase());
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                rowCount = fillTable(rs);
            }
            
            String estado = AppGlobal.getText("WLIST_LBL_STATE_TEXT") + ": " + rowCount; 
            setEstado(estado);
            
            tblData.getSelectionModel().setSelectionInterval(0, 0);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(WList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getSql() {
        // Recuperar información
        String fieldSearch = (String) cmbSearch.getSelectedItem();
        String valueSearch = txtSearch.getText().trim();
        boolean exact = false;
        
        for (WindowFieldSearch ws : window.getFieldsSearch()) {
            if (fieldSearch.equals(ws.getTitle())) {
                fieldSearch = ws.getField();
                break;
            }
        }
        String fieldOrder = fieldSearch;
        
        // Generando los campos que se solicitaran
        LinkedList<WindowFieldTable> fields = window.getFieldsTable();
        
        boolean first = true;
        String sqlFields = "";
        for (WindowFieldTable field : fields) {
            if (first) {
                sqlFields = "";
                first = false;
            }
            else {
                sqlFields += ",";
            }
            sqlFields += field.getField();
        }
        
        // Generando WHERE
        String sqlWhere = "";
        if (valueSearch != null && !valueSearch.isEmpty()) {
            sqlWhere = " WHERE ";
            if (chkGlobal.isSelected()) {
                for (WindowFieldSearch ws : window.getFieldsSearch()) {
                    sqlWhere += ws.getField() + " LIKE '%" + valueSearch + "%'";
                    sqlWhere += " OR ";
                }
                int tam = sqlWhere.length();
                sqlWhere = sqlWhere.substring(0, tam - 4);
            }
            else {
                if (exact) {
                    sqlWhere += fieldSearch + " = " + valueSearch;
                }
                else {
                    sqlWhere += fieldSearch + " LIKE '%" + valueSearch + "%'";
                }
            }
        }
        
        // Generando ORDER BY
        String sqlOrder = "";
        if (fieldOrder != null && !fieldOrder.isEmpty()) {
            sqlOrder = " ORDER BY " + fieldOrder;
        }
        
        // Generando instruccion SQL
        String sql;
        if (window.isUseSqlBase()) {
            sql = window.getSqlBase();
        }
        else {
            sql = "SELECT * FROM " + window.getTable();
        }
        
        if (!sqlFields.isEmpty()) {
            sql = sql.replace("*", sqlFields);
        }
        if (!sqlWhere.isEmpty()) {
            sql += sqlWhere;
        }
        if (!sqlOrder.isEmpty()) {
            sql += sqlOrder;
        }
        
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Producción de getSql() para listar.  Sql: " + sql);
        
        return sql;
    }
    
    private int fillTable(ResultSet rs) throws SQLException {
        DknConsole.msg(Thread.currentThread().getStackTrace()[1].toString(), " Llenando tabla con datos para listar...");
        int rowCount = 0 ;
        int columnCount;
        if (window.getFieldsTable().size() > 0) {
            columnCount = window.getFieldsTable().size();
        }
        else {
            columnCount = rs.getMetaData().getColumnCount();
            for (int i=0; i<columnCount; i++) {
                model.addColumn(rs.getMetaData().getColumnName(i+1));
            }
        }
        String fieldName;
        
        Object[] row;
        model.setRowCount(0);
        while (rs.next()) {
            row = new Object[columnCount];
            
            for (int i = 0; i < columnCount; i++) {
                if (window.getFieldsTable().size() > 0) {
                    fieldName = window.getFieldsTable().get(i).getField();
                    row[i] = rs.getObject(fieldName);
                }
                else {
                    fieldName = rs.getMetaData().getColumnName(i+1);
                    row[i] = rs.getObject(fieldName);
                }
            }
            
            model.addRow(row);
            rowCount++;
        }
        
        DknConsole.msgIsOk(true);
        
        return rowCount;
    }


}
