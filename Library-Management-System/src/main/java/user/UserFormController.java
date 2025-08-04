package user;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;


import java.sql.*;

import static javax.swing.text.html.HTML.Tag.U;

public class UserFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableView<User> tblUsers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtid;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtid.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        String sql = "INSERT INTO users(id, name, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);
            pst.setString(2, name);
            pst.setString(3, email);
            pst.setString(4, address);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "User added!").show();
                loadAllUsers();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to add user.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error occurred!").show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtid.getText();

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "User deleted!").show();
                loadAllUsers();
            } else {
                new Alert(Alert.AlertType.WARNING, "No user found with that ID.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error occurred!").show();
        }
    }


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadAllUsers();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtid.getText();

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtEmail.setText(rs.getString("email"));
                txtAddress.setText(rs.getString("address"));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No user found with that ID").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error occurred!").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtid.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        String sql = "UPDATE users SET name = ?, email = ?, address = ? WHERE id = ?";


        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, address);
            pst.setString(4, id);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "User updated!").show();
                loadAllUsers();
            } else {
                new Alert(Alert.AlertType.WARNING, "No user found with that ID.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error occurred!").show();
        }
    }

    private void loadAllUsers() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        ObservableList<User> userObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User u = new User(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );
                userObservableList.add(u); // ✅ fixed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblUsers.setItems(userObservableList); // ✅ fixed
    }
}
