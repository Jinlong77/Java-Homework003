package constant;

import functional.StaffMemberProcessor;
import model.StaffMember;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

import static constant.Color.*;

public class Message {

    //MESSAGES
    public static final String ENTER_USERNAME = "Enter Name: ";
    public static final String ENTER_ADDRESS = "Enter Address: ";
    public static final String ENTER_SALARY = "Enter Salary: ";
    public static final String ENTER_HOUR = "Enter Hour: ";
    public static final String ENTER_RATE = "Enter Rate: ";
    public static final String ENTER_BONUS = "Enter Bonus: ";
    public static final String ENTER_TYPE = "=> Select Employee Type: ";
    public static final String ENTER_OPTION = "=> Choose option(1-5): ";
    public static final String ENTER_TABLE_OPTION = "=> Enter option: ";
    public static final String ENTER_ID = "Enter or Search ID to ";
    public static final String FOOTER_TABLE_VOLUNTEER = "1. Name \t 2. Address ";


    //ERROR MESSAGES
    public static final String ERROR_INVALID_OPTION = "Please enter a valid option. [1-5]";
    public static final String ERROR_INVALID_VOLUNTEER = "Please enter a valid option. [0-3]";
    public static final String ERROR_INVALID_TABLE = "Please enter a valid option. [0-4]";
    public static final String ERROR_INVALID_TYPE = "Invalid type, please enter number between [1-4]";
    public static final String ERROR_INVALID_NAME = "Invalid name, please enter a valid name";
    public static final String ERROR_INVALID_ADDRESS = "Invalid address, please enter a valid address";
    public static final String ERROR_INVALID_SALARY = "Invalid salary, please enter a valid salary";
    public static final String ERROR_INVALID_HOUR = "Invalid hour, please enter a valid hour";
    public static final String ERROR_INVALID_RATE = "Invalid rate, please enter a valid rate";
    public static final String ERROR_INVALID_BONUS = "Invalid bonus, please enter a valid bonus";

    public static void printMenu() {
        CellStyle cellLeft = new CellStyle(CellStyle.HorizontalAlign.left);
        CellStyle cellCenter = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell(LIGHT_BLUE + "STAFF MANAGEMENT SYSTEM", cellCenter);
        table.addCell(LIGHT_YELLOW + " ".repeat(6) + "1. Insert Employee", cellLeft);
        table.addCell(LIGHT_GREEN + " ".repeat(6) + "2. Update Employee" , cellLeft);
        table.addCell(LIGHT_CYAN + " ".repeat(6) + "3. Display Employee" , cellLeft);
        table.addCell(LIGHT_PURPLE + " ".repeat(6) + "4. Delete Employee" , cellLeft);
        table.addCell(RED + " ".repeat(6) + "5. Exit" , cellLeft);
        table.setColumnWidth(0, 40, 40);
        System.out.println(table.render());
    }

    public static void printInsertMenu() {
        CellStyle cellCenter = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(4, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell(LIGHT_YELLOW + "1. Volunteer", cellCenter);
        table.addCell(LIGHT_GREEN + "2. Salaries Employee" , cellCenter);
        table.addCell(LIGHT_CYAN + "3. Hourly Employee" , cellCenter);
        table.addCell(RED + "4. Back" , cellCenter);
        System.out.printf("\n================ %s INSERT EMPLOYEE %s =================\n", LIGHT_BLUE , RESET);
        System.out.println(BLUE + "Employee Type: " + RESET);
        System.out.println(table.render());
    }

    public static void printDisplayEmployees(List<StaffMember> staffMembers, StaffMemberProcessor processor, int start, int end) {
        CellStyle cellCenter = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = tableStyle();

        for(int i = start; i < end; i++){
            processor.process(staffMembers.get(i), table, cellCenter);
        }

        System.out.printf("\n================ %s DISPLAY EMPLOYEES %s =================\n", LIGHT_BLUE, RESET);
        System.out.println(table.render());
    }

    public static void printDisplayEmployeeByType(int col, String header, StaffMember staffMembers, StaffMemberProcessor processor, int ... widths) {
        CellStyle cellCenter = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = tableStyleType(col,
                switch (header) {
                    case "Volunteer" -> List.of(
                            LIGHT_YELLOW + "ID",
                            YELLOW + "Type",
                            LIGHT_PURPLE + "Name",
                            LIGHT_CYAN + "Address",
                            LIGHT_GREEN + "Salary",
                            LIGHT_CYAN + "Pay"
                    );
                    case "Salaried Employee" -> List.of(
                            LIGHT_YELLOW + "ID",
                            YELLOW + "Type",
                            LIGHT_PURPLE + "Name",
                            LIGHT_CYAN + "Address",
                            LIGHT_GREEN + "Salary",
                            LIGHT_BLUE + "Bonus",
                            LIGHT_CYAN + "Pay"
                    );
                    case "Hourly Employee" -> List.of(
                            LIGHT_YELLOW + "ID",
                            YELLOW + "Type",
                            LIGHT_PURPLE + "Name",
                            LIGHT_CYAN + "Address",
                            RED + "Hour",
                            LIGHT_PURPLE + "Rate",
                            LIGHT_CYAN + "Pay"
                    );
                    default -> List.of();
                }, widths);
        processor.process(staffMembers, table, cellCenter);
        System.out.println(table.render());
        System.out.println(FOOTER_TABLE_VOLUNTEER + switch (header) {
            case "Volunteer" -> "\t 3. Salary \t 0. Cancel";
            case "Salaried Employee" -> "\t 3. Salary \t 4. Bonus \t 0. Cancel";
            default -> "\t 3. Hour \t 4. Rate \t 0. Cancel";
        });
    }

    private static Table tableStyleType(int col, List<String> header, int ... args){
        Table table = createTable(col, header);
        for(int i = 0; i < args.length; i++){
            table.setColumnWidth(i, args[i], args[i]);
        }
        return table;
    }

    private static Table tableStyle() {
        Table table = getTableStaffAll();
        table.setColumnWidth(0, 5, 5);
        table.setColumnWidth(1, 20, 20);
        table.setColumnWidth(2, 20, 30);
        table.setColumnWidth(3, 20, 30);
        table.setColumnWidth(4, 15, 20);
        table.setColumnWidth(5, 10, 10);
        table.setColumnWidth(6, 8, 8);
        table.setColumnWidth(7, 8, 8);
        table.setColumnWidth(8, 15, 18);
        return table;
    }

    private static Table getTableStaffAll() {
        List<String> headers = List.of(
                LIGHT_YELLOW + "ID",
                YELLOW + "Type",
                LIGHT_PURPLE + "Name",
                LIGHT_CYAN + "Address",
                LIGHT_GREEN + "Salary",
                LIGHT_BLUE + "Bonus",
                RED + "Hour",
                LIGHT_PURPLE + "Rate",
                LIGHT_CYAN + "Pay"
        );
        return createTable(9, headers);
    }

    private static Table createTable(int column, List<String> headers) {
        CellStyle cellCenter = new CellStyle(CellStyle.HorizontalAlign.center);
        BorderStyle borderStyle = BorderStyle.UNICODE_ROUND_BOX_WIDE;
        ShownBorders shownBorders = ShownBorders.ALL;
        Table table = new Table(column, borderStyle, shownBorders);
        headers.forEach(header -> table.addCell(header, cellCenter));
        return table;
    }

    public static void printError(String message){
        System.out.println("\n" + RED + "❌ " + message + RESET);
    }

    public static void printSuccess(String message){
        System.out.println("\n" + GREEN + "✅ " + message + RESET);
    }
}
