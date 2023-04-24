package gb.lib.common.exceptions;

public class ObjectInDBNotFoundException extends RuntimeException{
    public ObjectInDBNotFoundException(String message) {
        super(message);
    }
}
