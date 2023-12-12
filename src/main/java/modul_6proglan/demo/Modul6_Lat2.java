package modul_6proglan.demo;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Modul6_Lat2 extends Application {
    private TableView<Mahasiswa> table = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Test TableView");
        stage.setWidth(450);
        stage.setHeight(550);

        final Label label = new Label("Daftar Mahasiswa");
        label.setFont(new Font("Arial", 30));

        table.setEditable(true);

        TableColumn<Mahasiswa, String> nameCol = new TableColumn<>("Nama");
        TableColumn<Mahasiswa, String> nimCol = new TableColumn<>("NIM");
        TableColumn<Mahasiswa, String> emailCol = new TableColumn<>("Email");

        table.getColumns().addAll(nameCol, nimCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        final ObservableList<Mahasiswa> data = FXCollections.observableArrayList(
                new Mahasiswa("Larynt", "202110370311189", "laryntsa@gmail.com"),
                new Mahasiswa("Ahya", "202110370311187", "ayaa@gmail.com")
        );

        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nimCol.setCellValueFactory(cellData -> cellData.getValue().nimProperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        table.setItems(data);

        final TextField addName = new TextField();
        addName.setMaxWidth(nameCol.getPrefWidth());
        addName.setPromptText("Nama Mahasiswa");

        final TextField addNim = new TextField();
        addNim.setMaxWidth(nimCol.getPrefWidth());
        addNim.setPromptText("NIM");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Mahasiswa(
                        addName.getText(),
                        addNim.getText(),
                        addEmail.getText()
                ));

                addName.clear();
                addNim.clear();
                addEmail.clear();
            }
        });

        final HBox hboxInput = new HBox();
        hboxInput.getChildren().addAll(addName, addNim, addEmail, addButton);
        hboxInput.setSpacing(10);

        vbox.getChildren().addAll(hboxInput);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class Mahasiswa {
        private final StringProperty name;
        private final StringProperty nim;
        private final StringProperty email;

        public Mahasiswa(String name, String nim, String email) {
            this.name = new SimpleStringProperty(name);
            this.nim = new SimpleStringProperty(nim);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getNim() {
            return nim.get();
        }

        public void setNim(String fNim) {
            nim.set(fNim);
        }

        public StringProperty nimProperty() {
            return nim;
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fEmail) {
            email.set(fEmail);
        }

        public StringProperty emailProperty() {
            return email;
        }
    }
}
