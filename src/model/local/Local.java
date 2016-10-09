/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.local;

/**
 *
 * @author DickNeoM
 */
public class Local {
    
    private int id;
    private String code;
    private String notes;
    private int idType;
    private String property;
    private int floor;
    private double rent;
    private double discount;
    private double area;
    
    public static final String[] FIELDS = {"id", "code", "notes", "property", "idtype", "floor", "area", "rent", "discount"};

    public Local() {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
    
    
    
    @Override
    public String toString() {
        return code;
    }
    
    public String toText() {
        String str;
        
        str = "Local:";
        str += " Id: " + getId();
        str += " Codigo: " + getCode();
        str += " Canon: " + getRent();
        str += " Descuento: " + getDiscount();
        str += " Area: " + getArea();
        
        return str;
    }

}
