package repository.impl;

import model.HourlySalaryEmployee;
import model.SalariesEmployee;
import model.StaffMember;
import model.Volunteer;
import repository.StaffRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static utils.StaffUtils.*;

public class StaffRepositoryImpl implements StaffRepository {

    private final List<StaffMember> staffMemberList = new ArrayList<>(
            Arrays.asList(
            createHourlySalaryEmployee(1, "John Doe", "123 Main St", 40, 20),
            createVolunteer(2, "Jane Doe", "456 Main St", 40),
            createSalariesEmployee(3, "Tom Smith", "789 Main St", 50000, 2000),
            createHourlySalaryEmployee(4, "Jerry Smith", "101 Main St", 40, 20),
            createSalariesEmployee(5, "Tom Jerry", "111 Main St", 50000, 2000),
            createVolunteer(6, "Jerry Tom", "121 Main St", 40),
            createHourlySalaryEmployee(7, "Tom Doe", "131 Main St", 40, 20),
            createSalariesEmployee(8, "Jerry Doe", "141 Main St", 50000, 2000),
            createVolunteer(9, "Tom Smith", "151 Main St", 40)
    ));

    private int currentId = staffMemberList.size();

    public List<StaffMember> findAll() {
        return staffMemberList;
    }

    public void save(StaffMember staffMember) {
        staffMember.setId(++currentId);
        staffMemberList.add(staffMember);
    }

    public StaffMember findById(int id) {
        return staffMemberList.stream()
                .filter(staffMember -> staffMember.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void deleteById(int id) {
        staffMemberList.stream()
                .filter(staffMember -> staffMember.getId() == id)
                .findFirst()
                .ifPresent(staffMemberList::remove);
    }

    @Override
    public void update(StaffMember staffMember) {
        Optional<StaffMember> optionalStaffMember = staffMemberList.stream()
                .filter(staff -> staff.getId() == staffMember.getId())
                .findFirst();
        if (optionalStaffMember.isPresent()) {
            StaffMember staff = optionalStaffMember.get();
            staff.setName(staffMember.getName());
            staff.setAddress(staffMember.getAddress());
            switch (staff) {
                case HourlySalaryEmployee hourlySalaryEmployee -> {
                    hourlySalaryEmployee.setHoursWorked(((HourlySalaryEmployee) staffMember).getHours());
                    hourlySalaryEmployee.setHourlyRate(((HourlySalaryEmployee) staffMember).getRate());
                }
                case SalariesEmployee salariesEmployee -> {
                    salariesEmployee.setSalary(((SalariesEmployee) staffMember).getSalary());
                    salariesEmployee.setBonus(((SalariesEmployee) staffMember).getBonus());
                }
                case Volunteer volunteer -> volunteer.setSalary(((Volunteer) staffMember).pay());
                default -> {

                }
            }
        }
    }

    public int generateId() {
        return currentId;
    }
}
