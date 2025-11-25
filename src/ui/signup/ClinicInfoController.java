package ui.signup;


import dao.DoctorDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClinicInfoController implements Initializable {


    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_department;
    @FXML
    private TextField tf_price;
    @FXML
    private TextArea tf_description;
    @FXML
    private Button button_save;
    @FXML
    private Label messageLabel;

    private String currentDoctorUsername;

    public void setUsername(String username) {
        this.currentDoctorUsername = username;
    }

    @FXML
    public void handleSave() {
        String address = tf_address.getText();
        String dept = tf_department.getText();
        String price = tf_price.getText();
        String desc = tf_description.getText();

        if (address.isEmpty() || dept.isEmpty() || price.isEmpty()) {
            messageLabel.setText("Please fill all fields!");
            return;
        }

        // Make sure 'DoctorDAO' or 'ClinicDAO' matches the file you created earlier
        boolean success = DoctorDAO.saveClinicInfo(currentDoctorUsername, address, dept, price, desc);

        if (success) {
            System.out.println("model.Doctor profile completed.");
// NAVIGATE TO LOGIN SCREEN
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login/login.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) button_save.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Error saving info.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText("");
        tf_price.textProperty().addListener((observable ,oldValue,newValue )->{
            if(!newValue.matches("\\d*(\\.\\d*)?")){
                tf_price.setText(oldValue);
            }
        });
    }
}