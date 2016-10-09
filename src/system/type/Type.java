/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.type;

/**
 *
 * @author DickNeoM
 */
public class Type {
    private int id;
    private String code;
    private String name;
    private String description;
    private boolean active;

    public Type() {
        this.id = -1;
        this.active = true;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
    
    @Override
    public String toString() {
        return code;
    }
    
    public String aTexto() {
        String str;
        
        str = "Tipo:";
        str += " Id: " + getId();
        str += " Codigo: " + getCode();
        str += " Nombre: " + getName();
        str += " Activo: " + (isActive()? "SI": "NO");
        
        return str;
    }
}
