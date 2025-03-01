package enumeration;

public enum EmployeeType {
    VOLUNTEER("Volunteer"),
    SALARIED_EMPLOYEE("Salaried Employee"),
    HOURLY_EMPLOYEE("Hourly Employee");

    private final String value;

    EmployeeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
