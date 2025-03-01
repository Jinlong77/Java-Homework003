package functional;

import model.StaffMember;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

@FunctionalInterface
public interface StaffMemberProcessor {
    void process(StaffMember staffMember, Table table, CellStyle cellCenter);
}