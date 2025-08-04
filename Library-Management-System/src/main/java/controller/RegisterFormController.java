package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import model.Student;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {
    @Getter
    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtconfirmpassword;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtusername;

    private String generateId() {
        return "U" + System.currentTimeMillis();
    }


    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {
        String key = "#5541Asd";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String SQL = "INSERT INTO users(id,name,email,password,address) VALUES (?,?,?,?,?)";

        if (txtpassword.getText().trim().equals(txtconfirmpassword.getText().trim())) {
            Connection connection = DBConnection.getInstance().getConnection();

            String checkSQL = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
            checkStmt.setString(1, txtemail.getText());
            ResultSet resultSet = checkStmt.executeQuery();

            if (!resultSet.next()) {
                Student student = new Student(
                        txtusername.getText(),
                        txtemail.getText(),
                        txtpassword.getText(),
                        txtaddress.getText()
                );

                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1, generateId());
                psTm.setString(2, student.getUsername());
                psTm.setString(3, student.getEmail());
                psTm.setString(4, basicTextEncryptor.encrypt(student.getPassword()));
                psTm.setString(5, student.getAddress());
                psTm.executeUpdate();

                new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "User found").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Check your password...☺").show();
        }
    }
}
