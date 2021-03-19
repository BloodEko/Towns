package de.bloodeko.towns.util;


public class ModifyException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ModifyException(String text) {
        super(text);
    }

    public ModifyException(String text, Exception ex) {
        super(text, ex);
    }
}
