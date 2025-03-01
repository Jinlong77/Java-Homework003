package model;

import static enumeration.EmployeeType.VOLUNTEER;

public class Volunteer extends StaffMember {
    private double salary;

    public Volunteer(int id, String name, String address, double salary) {
        super(id, name, address, VOLUNTEER);
        this.salary = salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public double pay() {
        return salary;
    }

    @Override
    public String toString() {
        return super.toString() + pay();
    }
}
