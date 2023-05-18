package gb.library.common.exceptions;

public class ObjectInDBNotFoundException extends RuntimeException {
    private final String entityName;
    public ObjectInDBNotFoundException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
