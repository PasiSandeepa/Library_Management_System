package controller;

import com.jfoenix.controls.JFXBadge;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BooksFormController {
    @FXML
    private TableColumn<Book, String> colid;

    @FXML
    private TableColumn<Book, String> colName;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colPublisher;


    @FXML
    private TableView<Book> tblBooks;

    @FXML
    private TextField txtauthor;

    @FXML
    private TextField txtbookid;

    @FXML
    private TextField txtbookname;

    @FXML
    private TextField txtpublisher;

    @FXML
    private TextField txttotal;

    @FXML
    public void initialize() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtbookid.getText();

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Book deleted successfully.");
                // Optionally clear text fields
                txtbookid.clear();
                txtbookname.clear();
                txtauthor.clear();
                txtpublisher.clear();
            } else {
                System.out.println("Delete failed. Book ID not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnInsertOnAction(ActionEvent event) {
        String id = txtbookid.getText();
        String name = txtbookname.getText();
        String author = txtauthor.getText();
        String publisher = txtpublisher.getText();

        String sql = "INSERT INTO books (id, name, author, publisher) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);
            pst.setString(2, name);
            pst.setString(3, author);
            pst.setString(4, publisher);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Book inserted successfully.");
            } else {
                System.out.println("Insert failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadBooksToTable();
    }

    private void loadBooksToTable() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        String sql = "SELECT id, name, author, publisher FROM books";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                bookList.add(new Book(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("publisher")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        tblBooks.setItems(bookList);

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtbookid.getText();

        String sql = "SELECT name, author, publisher FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtbookname.setText(rs.getString("name"));
                txtauthor.setText(rs.getString("author"));
                txtpublisher.setText(rs.getString("publisher"));
            } else {
                System.out.println("Book not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label lblTotalBooks;

    @FXML
    void btnTotalBooksOnAction(ActionEvent event) {
        String sql = "SELECT COUNT(*) AS total FROM books";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                lblTotalBooks.setText("Total: " + total); // 🔁 Update label
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtbookid.getText();
        String name = txtbookname.getText();
        String author = txtauthor.getText();
        String publisher = txtpublisher.getText();

        String sql = "UPDATE books SET name = ?, author = ?, publisher = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, author);
            pst.setString(3, publisher);
            pst.setString(4, id); // WHERE id = ?

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Update failed. Check the Book ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





