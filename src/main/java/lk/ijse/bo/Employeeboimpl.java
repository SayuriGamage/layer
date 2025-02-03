package lk.ijse.bo;

import lk.ijse.dao.Employeedao;
import lk.ijse.dao.Employeedaoimpl;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;

public class Employeeboimpl  implements Employeebo{
    Employeedao employeedao= (Employeedao) BOFactory.getBoFactory().getBO(BOTypes.Employee);


    @Override
    public boolean save(EmployeeDTO employee) {
        return employeedao.save(new Employee(employee.getId(),employee.getName(),employee.getAddress(),employee.getTel()));
    }
}
