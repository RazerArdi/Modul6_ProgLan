package modul_6proglan.demo.Modul6_T2;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminController {

    private Stage stage;

    public AdminController(Stage stage) {
        this.stage = stage;
    }

    public void showAdminScreen() {
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

