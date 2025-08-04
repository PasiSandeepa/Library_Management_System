import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFromController {

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        System.out.println("Login button clicked");

        String email = txtEmail.getText().trim();
        String inputPassword = txtPassword.getText().trim();

        System.out.println("Email entered: " + email);
        System.out.println("Password entered: " + inputPassword);

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT password FROM users WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dbEncryptedPassword = rs.getString("password");
                System.out.println("Encrypted password from DB: " + dbEncryptedPassword);

                BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
                textEncryptor.setPassword("#5541Asd");

                String decryptedDbPassword = textEncryptor.decrypt(dbEncryptedPassword);
                System.out.println("Decrypted DB password: " + decryptedDbPassword);

                if (inputPassword.equals(decryptedDbPassword)) {
                    System.out.println("Password matched. Opening dashboard...");

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
                    Parent root = fxmlLoader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Dashboard");
                    stage.setScene(new Scene(root));
                    stage.show();

                    Stage currentStage = (Stage) txtEmail.getScene().getWindow();
                    currentStage.close();

                } else {
                    System.out.println("Password invalid");
                    new Alert(Alert.AlertType.ERROR, "Invalid Password!").show();
                }
            } else {
                System.out.println("User not found");
                new Alert(Alert.AlertType.ERROR, "User not found!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Login failed due to an error!").show();
        }
    }

    @FXML
    void hyperRegisterOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/register_Form.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage(); // new window
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load register form!").show();
        }
    }

    }


