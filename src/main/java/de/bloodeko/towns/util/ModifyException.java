package de.bloodeko.towns.util;


public class ModifyException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ModifyException(String text) {
        super(Messages.get(text));
    }

    public ModifyException(String text, Exception ex) {
        super(Messages.get(text), ex);
    }

    public ModifyException(String text, Object... args) {
        super(Messages.get(text, args));
    }
}
