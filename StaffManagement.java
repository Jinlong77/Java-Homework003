import controller.StaffController;
import repository.StaffRepository;
import repository.impl.StaffRepositoryImpl;
import service.StaffService;
import service.impl.StaffServiceImpl;

public class StaffManagement {
    public static void main(String[] args) {
        StaffRepository staffRepository = new StaffRepositoryImpl();
        StaffService staffService= new StaffServiceImpl(staffRepository);

        StaffController staffManagement = new StaffController(staffService);
        staffManagement.start();
    }
}
