/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ente;

/**
 *
 * @author DickNeoM
 */
public class Ente {
    private int id;
    private String code;
    private String name;
    private String surname;
    private int idType;
    private int idDniType;
    private String dni;
    private String note;
    private boolean active;
    private int idLegalRep;
    
    private EnteAddress address;
    
    public final static String[] FIELDS = {"id", "code", "name", "surname", "iddnitype", "dni", "idtype", "notes", "active", "idlegalrep"};
    
    public Ente() {
        this.id = -1;
        this.active = true;
        this.address = new EnteAddress();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdDniType() {
        return idDniType;
    }

    public void setIdDniType(int idDniType) {
        this.idDniType = idDniType;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIdLegalRep() {
        return idLegalRep;
    }

    public void setIdLegalRep(int idLegalRep) {
        this.idLegalRep = idLegalRep;
    }

    public EnteAddress getAddress() {
        return address;
    }
    
    public void setAddress(EnteAddress address) {
        this.address = address;
    }


    
    
    
    public String getFullName() {
        String fullName;
        if (surname == null || surname.isEmpty()) {
            fullName = name;
        }
        else {
            fullName = surname + " " + name;
        }
        return fullName;
    }


    
    
    
    
    @Override
    public String toString() {
        return code;
    }
    
    public String toText() {
        String str;
        
        str = "Ente:";
        str += " Id: " + getId();
        str += " Codigo: " + getCode();
        str += " Nombre: " + getName();
        str += " Apellido: " + getSurname();
        str += " DNI: " + getDni();
        str += " Activo: " + (isActive()? "SI": "NO");
        
        return str;
    }


}
