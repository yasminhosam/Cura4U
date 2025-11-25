package ui.signup;

import dao.UserDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*; // Import all JavaFX controls (Button, Label, TextField, etc.)
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class signUpController implements Initializable {

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField tf_password;
    @FXML
    private TextField tf_phone;

    @FXML
    private RadioButton rb_patient;
    @FXML
    private RadioButton rb_doctor;

    @FXML
    private Button button_sign_up;

    @FXML
    private Button button_login;

    @FXML
    private Label messageLabel;
    @FXML
    public void handleSignUp(ActionEvent event) {
        // 2. Use the corrected variable names
        String username = tf_username.getText();
        String email = tf_email.getText();
        String password = tf_password.getText();
        String phone = tf_phone.getText();
        String role = rb_patient.isSelected() ? "patient" : "doctor";

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() ) {
            messageLabel.setText("Please fill in all fields!");
            return;
        }


        User newUser = new User(username, email, phone, password, role);
        // Call the DAO
        boolean isRegistered = UserDAO.registerUser(newUser);

        if (isRegistered) {
            if (role.equals("doctor")) {
                openClinicInfoScreen(username);
            } else {
                messageLabel.setText("Success! Please Login.");
                messageLabel.setStyle("-fx-text-fill: green;");
                openLoginScreen();
            }
        } else {
            messageLabel.setText("Registration Failed. model.User might exist.");
        }
    }

    private void openClinicInfoScreen(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/signup/doctor-clinc-info.fxml"));
            Parent root = loader.load();

            ClinicInfoController controller = loader.getController();
            controller.setUsername(username);

            // 3. Use correct button variable to get the stage
            Stage stage = (Stage) button_sign_up.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error loading next screen.");
        }
    }
    private void openLoginScreen() {
        try {
            // Make sure this path matches where your login.fxml actually is!
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login/login.fxml"));
            Parent root = loader.load();

            // Get the current window (stage) using the sign-up button
            Stage stage = (Stage) button_sign_up.getScene().getWindow();

            // Switch scenes
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading login screen.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // When the user clicks "Login" (Already a user?)
        button_login.setOnAction(event -> {
            try {
                // 1. Load the Login FXML

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login/login.fxml"));
                Parent root = loader.load();

                // 2. Get the current stage (window)
                Stage stage = (Stage) button_login.getScene().getWindow();

                // 3. Switch the scene
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Could not load Login screen.");
            }
        });
    }
}