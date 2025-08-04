import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {

    @FXML
    private AnchorPane loadFormContent;

    @FXML
    void btnBookOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/books_form.fxml");


        assert resource != null;
        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

    }

    @FXML
    void btnBorrowingOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getClassLoader().getResource("view/borrowing_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

    }

    @FXML
    void btnLibrarianOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/user_form.fxml");


        assert resource != null;
        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/register_Form.fxml");


        assert resource != null;
        Parent load = FXMLLoader.load(resource);

        loadFormContent.getChildren().clear();
        loadFormContent.getChildren().add(load);


    }



}