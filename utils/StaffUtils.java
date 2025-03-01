package utils;

import model.HourlySalaryEmployee;
import model.SalariesEmployee;
import model.Volunteer;

public class StaffUtils {

    public static HourlySalaryEmployee createHourlySalaryEmployee(int id, String name, String address, double hourlyRate, int hours) {
        return new HourlySalaryEmployee(id, name, address,  hourlyRate, hours);
    }

    public static Volunteer createVolunteer(int id, String name, String address, double hours) {
        return new Volunteer(id, name, address, hours);
    }

    public static SalariesEmployee createSalariesEmployee(int id, String name, String address, double salary, double bonus) {
        return new SalariesEmployee(id, name, address, salary, bonus);
    }
}
