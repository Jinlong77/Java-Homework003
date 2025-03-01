package model;

import static enumeration.EmployeeType.HOURLY_EMPLOYEE;

public class HourlySalaryEmployee extends StaffMember {
    private double rate;
    private int hours;

    public HourlySalaryEmployee(int id, String name, String address, double rate, int hours) {
        super(id, name, address, HOURLY_EMPLOYEE);
        this.rate = rate;
        this.hours = hours;
    }

    public double getRate() {
        return rate;
    }

    public int getHours() {
        return hours;
    }

    public String getHourlyRate() {
        return super.toString() + rate;
    }

    public String getHoursWorked() {
        return String.valueOf(hours);
    }

    public void setHourlyRate(double rate) {
        this.rate = rate;
    }

    public void setHoursWorked(int hours) {
        this.hours = hours;
    }

    @Override
    public double pay() {
        return rate * hours;
    }

    @Override
    public String toString() {
        return super.toString() + pay();
    }
}
