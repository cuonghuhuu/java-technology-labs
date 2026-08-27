package vn.edu.eaut.lab10.model;

public enum Role {
    ADMIN("Quản trị viên"),
    STAFF("Nhân viên nghiệp vụ"),
    USER("Người dùng");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
