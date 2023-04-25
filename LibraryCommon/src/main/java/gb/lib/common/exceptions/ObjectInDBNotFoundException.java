package gb.lib.common.exceptions;

public class ObjectInDBNotFoundException extends RuntimeException {
    String entityName;
    public ObjectInDBNotFoundException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
