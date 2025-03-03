package service.impl;

import enumeration.EmployeeType;
import functional.StaffMemberProcessor;
import model.HourlySalaryEmployee;
import model.SalariesEmployee;
import model.StaffMember;
import model.Volunteer;
import repository.StaffRepository;
import service.StaffService;

import java.util.*;
import java.util.function.*;

import static constant.Color.*;
import static constant.Color.RESET;
import static constant.Config.*;
import static constant.Message.*;
import static constant.Validation.getValidatedInput;
import static constant.Validation.validateChoice;
import static enumeration.EmployeeType.*;
import static utils.StaffUtils.*;


public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final Scanner scanner = new Scanner(System.in);

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    private final StaffMemberProcessor staffMemberProcessor = (staffMember, table, cellCenter) -> {
        table.addCell(String.valueOf(staffMember.getId()), cellCenter);
        table.addCell(staffMember.getType().getValue(), cellCenter);
        table.addCell(staffMember.getName(), cellCenter);
        table.addCell(staffMember.getAddress(), cellCenter);

        switch (staffMember) {
            case SalariesEmployee salaried -> {
                table.addCell(salaried.getSalaryString(), cellCenter);
                table.addCell(salaried.getBonusString(), cellCenter);
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
            }
            case HourlySalaryEmployee hourly -> {
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
                table.addCell(hourly.getHoursWorked(), cellCenter);
                table.addCell(hourly.getHourlyRate(), cellCenter);
            }
            case Volunteer volunteer -> {
                table.addCell(volunteer.toString(), cellCenter);
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
            }
            default -> {
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
            }
        }
        table.addCell(staffMember.toString(), cellCenter);
    };

    private final StaffMemberProcessor staffMemberProcessorType = (staffMember, table, cellCenter) -> {
        table.addCell(String.valueOf(staffMember.getId()), cellCenter);
        table.addCell(staffMember.getType().getValue(), cellCenter);
        table.addCell(staffMember.getName(), cellCenter);
        table.addCell(staffMember.getAddress(), cellCenter);

        switch (staffMember) {
            case SalariesEmployee salaried -> {
                table.addCell(salaried.getSalaryString(), cellCenter);
                table.addCell(salaried.getBonusString(), cellCenter);
            }
            case HourlySalaryEmployee hourly -> {
                table.addCell(hourly.getHoursWorked(), cellCenter);
                table.addCell(hourly.getHourlyRate(), cellCenter);
            }
            case Volunteer volunteer -> {
                table.addCell(volunteer.toString(), cellCenter);
            }
            default -> {
                table.addCell("---", cellCenter);
                table.addCell("---", cellCenter);
            }
        }
        table.addCell(staffMember.toString(), cellCenter);
    };

    private final Map<String, Supplier<StaffMember>> staffMemberMap = Map.of(
            VOLUNTEER.getValue(), () -> createVolunteer(
                    generateId(),
                    getValidatedInput(scanner::nextLine, name -> name.matches(REGEX_NAME), ERROR_INVALID_NAME, ENTER_USERNAME),
                    getValidatedInput(scanner::nextLine, address -> address.matches(REGEX_ADDRESS), ERROR_INVALID_ADDRESS, ENTER_ADDRESS),
                    Double.parseDouble(getValidatedInput(scanner::nextLine, salary -> salary.matches(REGEX_SALARY), ERROR_INVALID_SALARY, ENTER_SALARY))
            ),
            SALARIED_EMPLOYEE.getValue(), () -> createSalariesEmployee(
                    generateId(),
                    getValidatedInput(scanner::nextLine, name -> name.matches(REGEX_NAME), ERROR_INVALID_NAME, ENTER_USERNAME),
                    getValidatedInput(scanner::nextLine, address -> address.matches(REGEX_ADDRESS), ERROR_INVALID_ADDRESS, ENTER_ADDRESS),
                    Double.parseDouble(getValidatedInput(scanner::nextLine, salary -> salary.matches(REGEX_SALARY), ERROR_INVALID_SALARY, ENTER_SALARY)),
                    Double.parseDouble(getValidatedInput(scanner::nextLine, bonus -> bonus.matches(REGEX_SALARY), ERROR_INVALID_BONUS, ENTER_BONUS))
            ),
            HOURLY_EMPLOYEE.getValue(), () -> createHourlySalaryEmployee(
                    generateId(),
                    getValidatedInput(scanner::nextLine, name -> name.matches(REGEX_NAME), ERROR_INVALID_NAME, ENTER_USERNAME),
                    getValidatedInput(scanner::nextLine, address -> address.matches(REGEX_ADDRESS), ERROR_INVALID_ADDRESS, ENTER_ADDRESS),
                    Double.parseDouble(getValidatedInput(scanner::nextLine, rate -> rate.matches(REGEX_SALARY), ERROR_INVALID_RATE, ENTER_RATE)),
                    Integer.parseInt(getValidatedInput(scanner::nextLine, hour -> hour.matches(REGEX_INT), ERROR_INVALID_HOUR, ENTER_HOUR)
            )
    ));


    private final BiConsumer<EmployeeType, StaffMember> displayStaffMembers = (employeeType, staffMember) -> {
        switch (employeeType) {
            case SALARIED_EMPLOYEE -> printDisplayEmployeeByType(7, SALARIED_EMPLOYEE.getValue(), staffMember, staffMemberProcessorType, 5, 20, 20, 20, 20, 20, 15);
            case HOURLY_EMPLOYEE -> printDisplayEmployeeByType(7, HOURLY_EMPLOYEE.getValue(), staffMember, staffMemberProcessorType, 5, 20, 20, 20, 10, 10, 15);
            case VOLUNTEER -> printDisplayEmployeeByType(6, VOLUNTEER.getValue(), staffMember, staffMemberProcessorType, 5, 20, 20, 20, 15, 15);
        }
    };


    @Override
    public void addStaffMember() {
            printInsertMenu();
            String choice = getValidatedInput(scanner::nextLine, input -> input.matches(REGEX_TYPE), ERROR_INVALID_TYPE, ENTER_TYPE);
            if (choice.equals("4")) return;

            System.out.println("ID: " + generateId());
            Supplier<StaffMember> staffMemberSupplier = staffMemberMap.get(
                    switch (choice) {
                        case "1" -> VOLUNTEER.getValue();
                        case "2" -> SALARIED_EMPLOYEE.getValue();
                        default -> HOURLY_EMPLOYEE.getValue();
                    }
            );
            StaffMember staffMember = staffMemberSupplier.get();
            staffRepository.save(staffMember);
            printSuccess("Staff member added successfully");
    }

    @Override
    public void updateStaffMember() {
        System.out.printf("\n================ %s UPDATE EMPLOYEES %s =================\n", LIGHT_BLUE, RESET);
        int id = Integer.parseInt(getValidatedInput(scanner::nextLine, input -> input.matches(REGEX_ID), "Invalid ID", ENTER_ID + "update: "));
        StaffMember staffMember = staffRepository.findById(id);
        if (staffMember == null) {
            printError("Staff member not found");
            return;
        }

        Predicate<String> regex = input -> input.matches(staffMember.getType().getValue().equals(VOLUNTEER.getValue()) ? REGEX_TABLE_VOLUNTEER_OPTION : REGEX_TABLE);
        Function<String, String> message = contain -> contain.equals(VOLUNTEER.getValue()) ? ERROR_INVALID_VOLUNTEER : ERROR_INVALID_TABLE;

        displayStaffMembers.accept(staffMember.getType(), staffMember);
        String choice = getValidatedInput(scanner::nextLine, regex, message.apply(staffMember.getType().getValue()), ENTER_TABLE_OPTION);
        if (choice.equals("0")) return;

        switch (staffMember.getType()) {
            case SALARIED_EMPLOYEE -> updateSalariesEmployee((SalariesEmployee) staffMember, choice);
            case HOURLY_EMPLOYEE -> updateHourlySalaryEmployee((HourlySalaryEmployee) staffMember, choice);
            case VOLUNTEER -> updateVolunteer((Volunteer) staffMember, choice);
        }
    }

    private void updateSalariesEmployee(SalariesEmployee salaried, String choice) {
        switch (choice) {
            case "1" -> salaried.setName(getValidatedInput(scanner::nextLine, name -> name.matches(REGEX_NAME), ERROR_INVALID_NAME, ENTER_USERNAME));
            case "2" -> salaried.setAddress(getValidatedInput(scanner::nextLine, address -> address.matches(REGEX_ADDRESS), ERROR_INVALID_ADDRESS, ENTER_ADDRESS));
            case "3" -> salaried.setSalary(Double.parseDouble(getValidatedInput(scanner::nextLine, salary -> salary.matches(REGEX_SALARY), ERROR_INVALID_SALARY, ENTER_SALARY)));
            case "4" -> salaried.setBonus(Double.parseDouble(getValidatedInput(scanner::nextLine, bonus -> bonus.matches(REGEX_SALARY), ERROR_INVALID_BONUS, ENTER_BONUS)));
        }
        staffRepository.update(salaried);
        printSuccess("Staff member updated successfully");
    }

    private void updateHourlySalaryEmployee(HourlySalaryEmployee hourly, String choice) {
        switch (choice) {
            case "1" -> hourly.setName(getValidatedInput(scanner::nextLine, name -> name.matches(REGEX_NAME), ERROR_INVALID_NAME, ENTER_USERNAME));
            case "2" -> hourly.setAddress(getValidatedInput(scanner::nextLine, address -> address.matches(REGEX_ADDRESS), ERROR_INVALID_ADDRESS, ENTER_ADDRESS));
            case "3" -> hourly.setHoursWorked(Integer.parseInt(getValidatedInput(scanner::nextLine, hour -> hour.matches(REGEX_INT), ERROR_INVALID_HOUR, ENTER_HOUR)));
            case "4" -> hourly.setHourlyRate(Double.parseDouble(getValidatedInput(scanner::nextLine, rate -> rate.matches(REGEX_SALARY), ERROR_INVALID_RATE, ENTER_RATE)));
        }
        staffRepository.update(hourly);
        printSuccess("Staff member updated successfully");
    }

    private void updateVolunteer(Volunteer volunteer, String choice) {
        switch (choice) {
            case "1" -> volunteer.setName(getValidatedInput(scanner::nextLine, name -> name.matches(REGEX_NAME), ERROR_INVALID_NAME, ENTER_USERNAME));
            case "2" -> volunteer.setAddress(getValidatedInput(scanner::nextLine, address -> address.matches(REGEX_ADDRESS), ERROR_INVALID_ADDRESS, ENTER_ADDRESS));
            case "3" -> volunteer.setSalary(Double.parseDouble(getValidatedInput(scanner::nextLine, salary -> salary.matches(REGEX_SALARY), ERROR_INVALID_SALARY, ENTER_SALARY)));
        }
        staffRepository.update(volunteer);
        printSuccess("Staff member updated successfully");
    }

    @Override
    public void deleteStaffMember() {
        System.out.printf("\n================ %s DELETE EMPLOYEES %s =================\n", LIGHT_BLUE, RESET);
        int id = Integer.parseInt(getValidatedInput(scanner::nextLine, input -> input.matches(REGEX_ID), "Invalid ID", ENTER_ID + "delete: "));
        StaffMember staffMember = staffRepository.findById(id);
        if (staffMember == null) {
            printError("Staff member not found");
            return;
        }
        staffRepository.deleteById(id);
        printSuccess("Staff member deleted successfully \uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB\uD83D\uDE22\uD83E\uDD1D");
    }

    @Override
    public void displayStaffMember() {
        List<StaffMember> staffMembers = staffRepository.findAll();
        int totalPages = ((staffMembers.size() + 3 - 1) / 3);
        int currentPage = 1;
        String choice;
        while (true) {
            int start = (currentPage - 1) * 3;
            int end = Math.min(currentPage * 3, staffMembers.size());
            printDisplayEmployees(staffMembers, staffMemberProcessor, start, end);
            System.out.printf("\nPage: %s/%s\n", currentPage, totalPages);
            System.out.printf("%s1. Next Page \t %s 2. Previous Page \t %s 3. First Page \t %s 4. Last Page \t %s 5. Exit %s\n", BLUE, PURPLE, CYAN, GREEN, RED, RESET);
            System.out.print(LIGHT_GREEN + ENTER_TABLE_OPTION);
            choice = scanner.nextLine();
            if (!validateChoice(choice)) {
                continue;
            }

            if (choice.equals("5")) break;

            switch (choice) {
                case "1" -> {
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        printError("You are already in the last page");
                    }
                }
                case "2" -> {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        printError("You are already in the first page");
                    }
                }
                case "3" -> currentPage = 1;
                default -> currentPage = totalPages;
            }
        }
    }

    private int generateId() {
        return staffRepository.generateId() + 1;
    }
}