/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.invoice;

import console.DknConsole;
import DknTime.DateTime;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.contract.Contract;
import model.contract.ContractDAO;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.payment.Payments;
import system.config.AppGlobal;
import system.data.DAOSQL;

/**
 *
 * @author richneom
 */
public class InvoiceDAO extends DAOSQL {
    
    private static final String TABLE = "invoices";
    
    private static final String TABLE_DETAIL = "invoicesdetail";
    
    
    // VALIDACIONES
    public static String validate(Invoice invoice) throws ClassNotFoundException, SQLException, ParseException {
        DknConsole.println(Thread.currentThread().getStackTrace()[1].toString() + " idInvoice: " + invoice.getId());
        String error = null;
        
        error = validateNumber(invoice);
        if (error != null) {
            return error;
        }
        
        error = validateContract(invoice.getIdContract(), invoice.isCanceled());
        if (error != null) {
            return error;
        }
        
        return error;
    }
    
    /**
     * Verifica que el número de serie y de factura no este vacio, y que el numero de la factura no esté duplicado.
     * @param invoice
     * @return 
     */
    public static String validateNumber(Invoice invoice) {
        DknConsole.println(Thread.currentThread().getStackTrace()[1].toString() + " idInvlice: " + invoice.getId());
        String error;
        
        if (invoice.getSerie() == null || invoice.getSerie().isEmpty() || invoice.getNumber() <= 0) {
            DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Campos serie o numero vacios.");
            return AppGlobal.getText("INVOICEDAO_ERROR_NUMBEREMPTY_TEXT");
        }
        
        try {
            Invoice i = get("number", invoice.getNumber());
            
            if (i != null && (i.getSerie().equals(invoice.getSerie()) && i.getId() != invoice.getId())) {
                DknConsole.warning(Thread.currentThread().getStackTrace()[1].toString(), "Campo numero duplicado.");
                return AppGlobal.getText("INVOICEDAO_ERROR_NUMBERDUPICATED_TEXT");
            }
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    /**
     * Valida que el contrato exista.
     * @param idContract
     * @param canceled si la factura está anulada
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     * @throws java.text.ParseException 
     */
    public static String validateContract(int idContract, boolean canceled) throws ClassNotFoundException, SQLException, ParseException {
        if (idContract < 0 && !canceled) {
            return AppGlobal.getText("INVOICEDAO_ERROR_CONTRACTEMPTY_TEXT");
        }
        Contract contract = ContractDAO.get(idContract);
        
        if (contract == null && !canceled) {
            return AppGlobal.getText("INVOICEDAO_ERROR_CONTRACTNOFOUND_TEXT");
        }
        
        return null;
    }
    
    
    
    
    
    // ACTUALIZACIONES
    public static int update(Invoice invoice) throws ClassNotFoundException, SQLException, ParseException {
        DknConsole.println(Thread.currentThread().getStackTrace()[1].toString() + " idInvoice: " + invoice.getId());

        Object[] values = invoice.toArray();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE, Invoice.FIELDS, values);
        
        if (rowsAffected > 0 && invoice.getDetails().size() > 0) {
            int idInvoice;
            if (invoice.getId() < 0) {
                idInvoice = InvoiceDAO.get("number", invoice.getNumber()).getId();
            }
            else {
                idInvoice = invoice.getId();
            }
            
            for (InvoiceDetail id : invoice.getDetails()) {
                id.setIdInvoice(idInvoice);
                if (id.isDeleted()) {
                    if (id.getId() > 0) {
                        delete(AppGlobal.getDataBase(), TABLE_DETAIL, id.getId());
                    }
                }
                else {
                    int detailRowsAffected = updateDetail(id);
                }
            }
        }
        
        return rowsAffected;
    }
    
    private static int updateDetail(InvoiceDetail invoiceDetail) throws ClassNotFoundException, SQLException {
        DknConsole.println(Thread.currentThread().getStackTrace()[1].toString() + " Filas afectadas: " + invoiceDetail.getId());
        
        Object[] reg = invoiceDetail.toArray();
        
        int rowsAffected = update(AppGlobal.getDataBase(), TABLE_DETAIL, InvoiceDetail.FIELDS_DETAIL, reg);
        
        return rowsAffected;
    }
    

    
    
    
    
    // CONSULTAS
    /**
     * Devuelve la factura con el id indicado.
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    public static Invoice get(int id) throws ClassNotFoundException, SQLException, ParseException {
        
        Invoice invoice = get("id", id);
        
        return invoice;
    }
    
    /**
     * Devuelve la factura con el codigo indicado.<br>
     * <strong>Para el caso de facturas no utilizar este método ya que no tiene campo code.</strong>
     * @param code
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    public static Invoice get(String code) throws ClassNotFoundException, SQLException, ParseException {
        Invoice invoice = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, code);
        if (map != null && map.size() > 0) {
            invoice = MapToInvoice(map.getFirst());
        }
        
        return invoice;
    }
    
    /**
     * Devuelve la factura que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    public static Invoice get(String field, String value) throws ClassNotFoundException, SQLException, ParseException {
        Invoice invoice = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, true, null);
        if (map != null && map.size() > 0) {
            invoice = MapToInvoice(map.getFirst());
        }
        
        return invoice;
    }
    
    /**
     * Devuelve la factura que contiene le valor <code>value</code> en el campo <code>field</code>
     * @param field
     * @param value
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    public static Invoice get(String field, int value) throws ClassNotFoundException, SQLException, ParseException {
        Invoice invoice = null;
        
        LinkedList<HashMap<String, Object>> map = select(AppGlobal.getDataBase(), TABLE, field, value, null);
        if (map != null && map.size() > 0) {
            invoice = MapToInvoice(map.getFirst());
        }
        
        return invoice;
    }
    
    public static Invoices gets(String fieldOrder) throws ClassNotFoundException, SQLException, ParseException {
        
        Invoices invoices = ListMapToInvoices(selectAll(AppGlobal.getDataBase(), TABLE, fieldOrder));
        
        return invoices;
    }
    
    
    
    
    
    
    
    public static Invoices ListMapToInvoices(LinkedList<HashMap<String, Object>> datos) throws ParseException, ClassNotFoundException, SQLException {
        Invoices invoices = new Invoices();
        
        for (HashMap<String, Object> dato : datos) {
            invoices.add(MapToInvoice(dato));
        }
        
        return invoices;
    }
    
    public static Invoice MapToInvoice(HashMap<String, Object> dato) throws ParseException, ClassNotFoundException, SQLException {
        Invoice invoice = new Invoice();
        
        invoice.setId((int) dato.get("id"));
        invoice.setSerie((String) dato.get("serie"));
        invoice.setNumber((int) dato.get("number"));
        invoice.setDate(DateTime.getStringToDateUtil(((String)dato.get("date")), AppGlobal.getFormatDate()));
        invoice.setIdContract((int) dato.get("idcontract"));
        invoice.setObservations((String) dato.get("observations"));
        invoice.setCanceled(((int) dato.get("canceled")) == 1);
        invoice.setTax((double) dato.get("tax"));
        invoice.setIdType((int) dato.get("idinvoicestype"));
        
        fillDetail(invoice);
        
        return invoice;
    }
    
    
    
    
    
    
    
    
    private static void fillDetail(Invoice invoice) throws ClassNotFoundException, SQLException, ParseException {
        invoice.setDetails(getsDetail(invoice.getId()));
    }
    
    private static InvoiceDetails getsDetail(int idInvoice) throws ClassNotFoundException, SQLException, ParseException {
        
        InvoiceDetails invoiceDetails = ListMapToInvoiceDetails(select(AppGlobal.getDataBase(), TABLE_DETAIL, "idinvoice", idInvoice, null));
        
        return invoiceDetails;
    }
    
    private static InvoiceDetails ListMapToInvoiceDetails(LinkedList<HashMap<String, Object>> datos) throws ParseException {
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        
        for (HashMap<String, Object> dato : datos) {
            invoiceDetails.add(MapToInvoiceDetail(dato));
        }
        
        return invoiceDetails;
    }
    
    private static InvoiceDetail MapToInvoiceDetail(HashMap<String, Object> dato) throws ParseException {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        
        invoiceDetail.setId((int) dato.get("id"));
        invoiceDetail.setIdInvoice((int) dato.get("idinvoice"));
        invoiceDetail.setQuantity((double) dato.get("quantity"));
        invoiceDetail.setIdItem((int) dato.get("iditem"));
        invoiceDetail.setIdLocal((int) dato.get("idlocal"));
        invoiceDetail.setMonth((int) dato.get("month"));
        invoiceDetail.setYear((int) dato.get("year"));
        invoiceDetail.setDateInit(DateTime.getStringToDateUtil(((String)dato.get("dateinit")), AppGlobal.getFormatDate()));
        invoiceDetail.setDateFinal(DateTime.getStringToDateUtil(((String)dato.get("datefinal")), AppGlobal.getFormatDate()));
        invoiceDetail.setPrice((double) dato.get("price"));
        invoiceDetail.setDiscount((double) dato.get("discount"));
        invoiceDetail.setTaxes((double) dato.get("tax"));
        invoiceDetail.setDescription((String) dato.get("description"));
        invoiceDetail.setDeleted(false);
        
        return invoiceDetail;
    }
    
    
    
    
    
    
    
    // METODOS CALCULADOS
    public static double getTotal(int idInvoice) throws ClassNotFoundException, SQLException, ParseException {
        Invoice invoice = get(idInvoice);
        
        return getTotal(invoice);
    }
    
    public static double getTotal(Invoice invoice) {
        double total;
        double suma = 0, discountTotal = 0, taxTotal = 0;
        for (InvoiceDetail id : invoice.getDetails()) {
            double price = id.getPrice();
            double discount = id.getDiscount();
            double discountprice = price - discount;
            
            double quantity = id.getQuantity();
            double amount = (price - discount) * quantity;
            
            double tax = id.getTaxes();
            double taxes = amount * tax / 100;
            
            suma += (price * quantity);
            discountTotal += (discount * quantity);
            taxTotal += taxes;
        }
        
        total = suma - discountTotal + taxTotal;
        
        return total;
    }
    
    public static double getPayment(Invoice invoice) throws ClassNotFoundException, SQLException {
        Payments ps = PaymentDAO.getsInvoice(invoice.getId());
        
        double sum = 0;
        if (ps != null) {
            for (Payment p : ps) {
                sum += p.getValue();
            }
        }
        
        return sum;
    }
    
}
