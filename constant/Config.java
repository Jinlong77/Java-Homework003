package constant;

public class Config {
    public static final String REGEX_NAME = "^[A-Za-z ]{3,50}$";
    public static final String REGEX_ADDRESS = "^[A-Za-z0-9\\s]+$";
    public static final String REGEX_SALARY = "^\\d+(\\.\\d{1,2})?$";
    public static final String REGEX_INT = "^[1-9]\\d{0,9}$";
    public static final String REGEX_ID = "^[1-9]\\d?$";
    public static final String REGEX_OPTION = "^[1-5]$";
    public static final String REGEX_TYPE = "^[1-4]$";
    public static final String REGEX_TABLE_OPTION = "^[1-5]$";
    public static final String REGEX_TABLE = "^[0-4]$";
    public static final String REGEX_TABLE_VOLUNTEER_OPTION = "^[0-3]$";
}
