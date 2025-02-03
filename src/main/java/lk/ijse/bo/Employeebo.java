package lk.ijse.bo;

import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;

public interface Employeebo extends SuperBo{


    boolean save(EmployeeDTO employee);

}
