package model;

import enumeration.EmployeeType;

public abstract class StaffMember {
    protected int id;
    protected String name;
    protected String address;
    protected EmployeeType type;

    public StaffMember(int id, String name, String address, EmployeeType type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public String toString() {
        return "$ ";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract double pay();
}
