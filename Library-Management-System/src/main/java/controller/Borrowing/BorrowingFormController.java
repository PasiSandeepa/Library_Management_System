package controller.Borrowing;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Borrow;

import java.sql.*;
import java.time.LocalDate;


public class BorrowingFormController {
    @FXML
    public void initialize() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("dateBorrowed"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("dateReturned"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
    }


    @FXML
    private TableView<Borrow> tblBorrowers;

    @FXML
    private TableColumn<Borrow, Integer> colid;

    @FXML
    private TableColumn<Borrow, Integer> colBookID;

    @FXML
    private TableColumn<Borrow, String> colDescription;

    @FXML
    private TableColumn<Borrow, LocalDate> colBorrowedDate;

    @FXML
    private TableColumn<Borrow, LocalDate> colReturnDate;

    @FXML
    private TableColumn<Borrow, String> colStudentName;


    @FXML
    private TextField txtTotal;

    @FXML
    private DatePicker DateBorrowed;

    @FXML
    private DatePicker DateReturn;


    @FXML
    private TableColumn<?, ?> colID;


    @FXML
    private TextField txtBook;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtStudentName;


    @FXML
    private TextField txtid;

    @FXML
    private Label lblTotalBorrowings;



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtid.getText();

        if (id.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an ID to delete.").show();
            return;
        }

        String sql = "DELETE FROM borrowing WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "No record found with that ID.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting.").show();
        }
    }

    @FXML
    void btnInsertOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtid.getText());
            String studentName = txtStudentName.getText();
            String bookid = txtBook.getText();
            String description = txtDescription.getText();
            LocalDate dateBorrowed = DateBorrowed.getValue();
            LocalDate dateReturned = DateReturn.getValue();

            String checkSql = "SELECT COUNT(*) FROM borrowing WHERE ID = ?";
            String bookCheckSql = "SELECT COUNT(*) FROM books WHERE ID = ?";
            String insertSql = "INSERT INTO borrowing (ID, BookID, Description, BorrowedDate, ReturnDate, StudentName) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection connection = DBConnection.getInstance().getConnection()) {

                // ID check
                PreparedStatement checkStmt = connection.prepareStatement(checkSql);
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert("Warning", "ID already exists!", Alert.AlertType.WARNING);
                    return;
                }

                // BookID check
                PreparedStatement bookCheckStmt = connection.prepareStatement(bookCheckSql);
                bookCheckStmt.setInt(1, Integer.parseInt(bookid));
                ResultSet bookRs = bookCheckStmt.executeQuery();

                if (bookRs.next() && bookRs.getInt(1) == 0) {
                    showAlert("Error", "Book ID not found in books table.", Alert.AlertType.ERROR);
                    return;
                }

                // Insert statement
                PreparedStatement pst = connection.prepareStatement(insertSql);
                pst.setInt(1, id);
                pst.setInt(2, Integer.parseInt(bookid));
                pst.setString(3, description);
                pst.setDate(4, java.sql.Date.valueOf(dateBorrowed));
                pst.setDate(5, java.sql.Date.valueOf(dateReturned));
                pst.setString(6, studentName);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    showAlert("Success", "Insert successful.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Insert failed.", Alert.AlertType.ERROR);
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values for ID and Book ID.", Alert.AlertType.ERROR);
        } catch (NullPointerException e) {
            showAlert("Input Error", "Please fill all date fields.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // showAlert method එක:
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        ObservableList<Borrow> borrowList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM borrowing";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setId(rs.getInt("ID"));
                borrow.setBookid(rs.getInt("BookID"));
                borrow.setDescription(rs.getString("Description"));

                // Handle potential null values for dates
                Date borrowed = rs.getDate("BorrowedDate");
                Date returned = rs.getDate("ReturnDate");
                if (borrowed != null) borrow.setDateBorrowed(borrowed.toLocalDate());
                if (returned != null) borrow.setDateReturned(returned.toLocalDate());

                String name = rs.getString("StudentName");
                if (name != null) borrow.setStudentName(name);
                else borrow.setStudentName(""); // or "Unknown"

                borrowList.add(borrow);

            }

            tblBorrowers.setItems(borrowList);

            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid")); // ⚠ correct: book**id**
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("dateBorrowed"));
            colReturnDate.setCellValueFactory(new PropertyValueFactory<>("dateReturned"));
            colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load borrowings!").show();
        }
    }


    @FXML
    void btnTotalBorrowingOnAction(ActionEvent event) {
        String sql = "SELECT COUNT(*) AS total FROM borrowing";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                txtTotal.setText(String.valueOf(total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            txtTotal.setText("Error");
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtid.getText());
            String studentName = txtStudentName.getText();
            String bookid = txtBook.getText();
            String description = txtDescription.getText();
            LocalDate dateBorrowed = DateBorrowed.getValue();
            LocalDate dateReturned = DateReturn.getValue();

            String checkSql = "SELECT COUNT(*) FROM borrowing WHERE ID = ?";
            String bookCheckSql = "SELECT COUNT(*) FROM books WHERE ID = ?";
            String updateSql = "UPDATE borrowing SET BookID = ?, Description = ?, BorrowedDate = ?, ReturnDate = ?, StudentName = ? WHERE ID = ?";

            try (Connection connection = DBConnection.getInstance().getConnection()) {

                // Check if record exists for this ID
                PreparedStatement checkStmt = connection.prepareStatement(checkSql);
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) == 0) {
                    showAlert("Warning", "Record with this ID does not exist!", Alert.AlertType.WARNING);
                    return;
                }

                // Check if BookID exists
                PreparedStatement bookCheckStmt = connection.prepareStatement(bookCheckSql);
                bookCheckStmt.setInt(1, Integer.parseInt(bookid));
                ResultSet bookRs = bookCheckStmt.executeQuery();

                if (bookRs.next() && bookRs.getInt(1) == 0) {
                    showAlert("Error", "Book ID not found in books table.", Alert.AlertType.ERROR);
                    return;
                }

                // Perform update
                PreparedStatement pst = connection.prepareStatement(updateSql);
                pst.setInt(1, Integer.parseInt(bookid));
                pst.setString(2, description);
                pst.setDate(3, java.sql.Date.valueOf(dateBorrowed));
                pst.setDate(4, java.sql.Date.valueOf(dateReturned));
                pst.setString(5, studentName);
                pst.setInt(6, id);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    showAlert("Success", "Update successful.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Update failed.", Alert.AlertType.ERROR);
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values for ID and Book ID.", Alert.AlertType.ERROR);
        } catch (NullPointerException e) {
            showAlert("Input Error", "Please fill all date fields.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}


