/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.window;

/**
 *
 * @author richneom
 */
public class WindowButton {
    private String code;
    private String title;
    private String command;
    private int width;
    private int height;
    private boolean visible;

    public WindowButton() {
        this.code = "";
        this.title = "";
        this.command = "";
        this.width = 0;
        this.height = 0;
        this.visible = true;
    }

    public WindowButton(String code, String title, String command, int width, int height, boolean visible) {
        this.code = code;
        this.title = title;
        this.command = command;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    
    
    
    
    public String toText() {
        String str;
        
        str = "Boton:";
        str += " Código: " + code;
        str += " Título: " + title;
        str += " Comando: " + command;
        str += " Ancho: " + width;
        str += " Alto: " + height;
        str += " Visible: " + visible;
        
        
        return str;
    }
    
}
