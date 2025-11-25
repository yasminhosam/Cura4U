package ui.login;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private Button button_login;

    @FXML
    private Button button_signup;

    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText("");


    }

    @FXML
    public void handleLogin(ActionEvent event) {
        messageLabel.setText("");
        String username = tf_username.getText();
        String password = tf_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            // Check if messageLabel is null to avoid crash if you forgot to add it to FXML
             messageLabel.setText("Please enter username and password.");
            return;
        }

        // 2. Check Database
        User loggedUser = UserDAO.checkLogin(username, password);

        if (loggedUser!=null) {
            User.setCurrentUser(loggedUser);
            messageLabel.setText("Login Successful!");
            messageLabel.setStyle("-fx-text-fill: green;");

            //  Navigate to the ui.Main Dashboard (model.Patient or model.Doctor Home)

        } else {
            messageLabel.setText("Invalid Username or Password.");
            System.out.println("Login Failed.");
        }
    }

    @FXML
    public void handleSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/signup/sign-up.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}