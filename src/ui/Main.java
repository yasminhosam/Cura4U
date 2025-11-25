package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Load the Sign Up FXML file
            // MAKE SURE THIS PATH IS CORRECT relative to your src folder
            Parent root = FXMLLoader.load(getClass().getResource("/ui/signup/sign-up.fxml"));

            // 2. Set up the Scene (Window)
            primaryStage.setTitle("Clinic System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Could not load sign-up.fxml. Check the file path!");
        }
    }

    public static void main(String[] args) {
        System.out.println("Checking Classpath...");
        String classpath = System.getProperty("java.class.path");
        if (classpath.contains("mysql")) {
            System.out.println("✅ MySQL JAR is FOUND!");
        } else {
            System.out.println("❌ MySQL JAR is MISSING from classpath.");
        }
        launch(args);
    }
}