package modul_6proglan.demo.Modul6_T2;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {

    private Stage stage;

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public void showLoginScreen() {
        VBox vbox = new VBox();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (isValidUser(username, password)) {
                if (isAdmin(username)) {
                    AdminController adminController = new AdminController(stage);
                    adminController.showAdminScreen();
                } else {
                    UserController userController = new UserController(stage);
                    userController.showUserScreen();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Login Error", "Invalid username or password.");
            }
        });

        vbox.getChildren().addAll(usernameField, passwordField, loginButton);

        stage.setScene(Utils.createScene(vbox));
        stage.show();
    }

    private boolean isValidUser(String username, String password) {
        return ("admin".equals(username) && "admin".equals(password)) ||
                ("user".equals(username) && "user".equals(password));
    }

    private boolean isAdmin(String username) {
        return "admin".equals(username);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

