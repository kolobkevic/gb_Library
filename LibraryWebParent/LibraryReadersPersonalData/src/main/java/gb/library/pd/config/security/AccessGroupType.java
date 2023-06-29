package gb.library.pd.config.security;

public enum AccessGroupType {
    ADMINISTRATOR,
    EMPLOYEE,
    USER;

    public String getRole() {
        return String.format("ROLE_%s", this);
    }
}
