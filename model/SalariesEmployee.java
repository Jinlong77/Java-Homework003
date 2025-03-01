package model;

import static enumeration.EmployeeType.SALARIED_EMPLOYEE;

public class SalariesEmployee extends StaffMember {
    private double salary;
    private double bonus;

    public SalariesEmployee(int id, String name, String address, double salary, double bonus) {
        super(id, name, address, SALARIED_EMPLOYEE);
        this.salary = salary;
        this.bonus = bonus;
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public String getSalaryString() {
        return super.toString() + salary;
    }

    public String getBonusString() {
        return super.toString() + bonus;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double pay() {
        return salary + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + pay();
    }
}
