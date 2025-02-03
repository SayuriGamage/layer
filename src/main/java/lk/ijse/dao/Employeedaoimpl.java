package lk.ijse.dao;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employeedaoimpl implements Employeedao {

    @Override
    public boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?)";


        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(4, employee.getTel());

        return pstm.executeUpdate() > 0;
    }
}
