package gb.library.common.enums;

public enum RoleType {
    ADMIN,
    EMPLOYEE,
    USER;

    public String getRole() {
        return String.format("ROLE_%s", this);
    }
}
