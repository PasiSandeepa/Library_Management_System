import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

public class Starter extends Application {

    public static void main(String[] args) {
        launch();


        String password = "saman@1212";

        String key = "#5541Asd";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

        basicTextEncryptor.setPassword(key);

// password encrypt කරනවා
        String encrypt = basicTextEncryptor.encrypt(password);
        System.out.println("Encrypt Password : " + encrypt);

// encrypt කරපු password එක decrypt කරනවා
        String decrypt = basicTextEncryptor.decrypt(encrypt);
        System.out.println("Decrypt Password : " + decrypt);



    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dashboard_form.fxml"))));
       stage.show();
    }
}
