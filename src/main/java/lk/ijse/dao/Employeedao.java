package lk.ijse.dao;

import lk.ijse.entity.Employee;

import java.sql.SQLException;

public interface Employeedao {


    boolean save(Employee employee) throws SQLException;

}
