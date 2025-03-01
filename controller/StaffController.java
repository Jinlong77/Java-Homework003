package controller;

import functional.Command;
import service.StaffService;

import java.util.HashMap;
import java.util.Scanner;

import static constant.Color.LIGHT_CYAN;
import static constant.Config.REGEX_OPTION;
import static constant.Message.*;
import static constant.Validation.getValidatedInput;
public class StaffController {

    private final StaffService staffService;
    private final Scanner sc;
    private final HashMap<String, Command> commands = new HashMap<>();

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
        sc = new Scanner(System.in);

        commands.put("1", this::insertEmployee);
        commands.put("2", this::updateEmployee);
        commands.put("3", this::displayEmployee);
        commands.put("4", this::deleteEmployee);
    }

    public void start() {
        menu();
        sc.close();
    }

    private void menu() {
        printMenu();
        String option = getValidatedInput(sc::nextLine, input -> input.matches(REGEX_OPTION), ERROR_INVALID_OPTION, ENTER_OPTION);
        commands.getOrDefault(option, this::exit).execute();
        commands.clear();
    }

    private void insertEmployee() {
        staffService.addStaffMember();
        menu();
    }

    private void updateEmployee() {
        staffService.updateStaffMember();
        menu();
    }

    private void displayEmployee() {
        staffService.displayStaffMember();
        menu();
    }

    private void deleteEmployee() {
        staffService.deleteStaffMember();
        menu();
    }

    private void exit() {
        System.out.println(LIGHT_CYAN + "Have a nice day! \uD83E\uDD16\uD83D\uDC4B\uD83D\uDC96");
        System.exit(0);
    }
}
