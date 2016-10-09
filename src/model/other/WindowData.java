/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.other;

import java.util.Date;
import model.ente.*;

/**
 *
 * @author richneom
 */
public class WindowData {
    private int id;
    private String code;
    private String name;
    private Double value;
    private Date dateInit;
    private Date dateFinal;

    public WindowData() {
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }


    
    
    
    
    
    @Override
    public String toString() {
        return code;
    }
    
    public String toText() {
        String str;
        
        str = "Tax:";
        str += " Id: " + getId();
        str += " Codigo: " + getCode();
        str += " Nombre: " + getName();
        str += " Valor [%]: " + getValue();
        str += " Fecha Inicial: " + getDateInit();
        str += " Fecha Final: " + getDateFinal();

        
        return str;
    }


}
