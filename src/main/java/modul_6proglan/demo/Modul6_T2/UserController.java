package modul_6proglan.demo.Modul6_T2;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserController {

    private Stage stage;

    public UserController(Stage stage) {
        this.stage = stage;
    }

    public void showUserScreen() {
        VBox vbox = new VBox();
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(event -> {
            LoginController loginController = new LoginController(stage);
            loginController.showLoginScreen();
        });

        vbox.getChildren().addAll(logoutButton);

        stage.setScene(Utils.createScene(vbox));
        stage.show();
    }
}

