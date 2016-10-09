/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ente;

/**
 *
 * @author richneom
 */
public class EnteAddress {
    private int id;
    private int idEnte;
    private int idCountry;
    private String state;
    private String city;
    private String mainStreet;
    private String number;
    private String street;
    private String reference;
    private boolean active;
    private boolean billing;
    
    public static String[] FIELDS = {"id", "idente", "idcountry", "state", "city", "mainstreet", "number", "street", "reference", "active", "billing"};

    public EnteAddress() {
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(int idEnte) {
        this.idEnte = idEnte;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMainStreet() {
        return mainStreet;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBilling() {
        return billing;
    }

    public void setBilling(boolean billing) {
        this.billing = billing;
    }

    public static String[] getFIELDS() {
        return FIELDS;
    }

    public static void setFIELDS(String[] FIELDS) {
        EnteAddress.FIELDS = FIELDS;
    }
    
    
    
    
    
    
    public Object[] toArray() {
        Object[] array = new Object[FIELDS.length];
        
        array[0] = getId();
        array[1] = getIdEnte();
        array[2] = getIdCountry();
        array[3] = getState();
        array[4] = getCity();
        array[5] = getMainStreet();
        array[6] = getNumber();
        array[7] = getStreet();
        array[8] = getReference();
        array[9] = isActive();
        array[10] = isBilling();
        
        return array;
    }
    
}
