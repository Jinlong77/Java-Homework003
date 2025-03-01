package repository;

import model.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffRepository {

    List<StaffMember> findAll();
    void save(StaffMember staffMember);
    StaffMember findById(int id);
    void deleteById(int id);
    void update(StaffMember staffMember);
    int generateId();
}
