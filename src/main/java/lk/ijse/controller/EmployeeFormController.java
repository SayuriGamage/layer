package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.Employeebo;
import lk.ijse.bo.Employeeboimpl;
import lk.ijse.dao.Employeedao;
import lk.ijse.dao.Employeedaoimpl;
import lk.ijse.dao.Employeedaoimpl;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;
import lk.ijse.repo.EmployeeRepo;
import lk.ijse.tm.EmployeeTm;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML private TableView<EmployeeTm> tblEmployee;
    @FXML private TableColumn<EmployeeTm, String> colId;
    @FXML private TableColumn<EmployeeTm, String> colName;
    @FXML private TableColumn<EmployeeTm, String> colAddress;
    @FXML private TableColumn<EmployeeTm, String> colTel;
    @FXML private Label lblempid;
    @FXML private TextField empnametext;
    @FXML private TextField addresemptext;
    @FXML private TextField telemptext;


    Employeebo employeebo= (Employeebo) new Employeeboimpl();
    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
        generateNextEmpId();

        // Set table row selection listener
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblempid.setText(newSelection.getId());
                empnametext.setText(newSelection.getName());
                addresemptext.setText(newSelection.getAddress());
                telemptext.setText(newSelection.getTel());
            }
        });
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee emp : employeeList) {
                obList.add(new EmployeeTm(emp.getId(), emp.getName(), emp.getAddress(), emp.getTel()));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public void saveempAction(ActionEvent actionEvent) {
        EmployeeDTO employee = new EmployeeDTO(lblempid.getText(), empnametext.getText(), addresemptext.getText(), telemptext.getText());
        boolean isSaved =employeebo.save(employee);
        if (isSaved) {
            showAlert("Employee saved!");
            refreshData();
        }
    }

    public void updateempAction(ActionEvent actionEvent) {
        Employee employee = new Employee(lblempid.getText(), empnametext.getText(), addresemptext.getText(), telemptext.getText());
        try {

            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                showAlert("Employee updated!");
                refreshData();
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public void deleteempAction(ActionEvent actionEvent) {
        try {
            boolean isDeleted = EmployeeRepo.delete(lblempid.getText());
            if (isDeleted) {
                showAlert("Employee deleted!");
                refreshData();
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public void clearempAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void refreshData() {
        clearFields();
        loadAllEmployees();
        generateNextEmpId();
    }

    private void clearFields() {
        lblempid.setText("");
        empnametext.setText("");
        addresemptext.setText("");
        telemptext.setText("");
    }

    private void generateNextEmpId() {
        try {
            String currentId = EmployeeRepo.getCurrentId();
            lblempid.setText(generateNextId(currentId));
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private String generateNextId(String currentId) {
        if (currentId != null && currentId.matches("^EMP\\d+$")) {
            int num = Integer.parseInt(currentId.substring(3)) + 1;
            return "EMP" + String.format("%03d", num);
        }
        return "EMP001";
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.CONFIRMATION, message).show();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}
