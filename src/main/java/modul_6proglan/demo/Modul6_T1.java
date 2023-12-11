package modul_6proglan.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Modul6_T1 extends Application {
    private TableView<Mahasiswa> table = new TableView<>();

    private TextField addNama;  // Declare at the class level
    private TextField addNim;   // Declare at the class level
    private TextField addEmail;  // Declare at the class level
    private TextField addFakultas;  // Declare at the class level
    private TextField addJurusan;   // Declare at the class level
    private TextField addAlamat;    // Declare at the class level
    private TextField addKota;      // Declare at the class level

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Form Biodata Mahasiswa");
        stage.setWidth(600);
        stage.setHeight(500);

        final Label label = new Label("Form Biodata Mahasiswa");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Mahasiswa, String> namaCol = new TableColumn<>("Nama");
        TableColumn<Mahasiswa, String> nimCol = new TableColumn<>("NIM");
        TableColumn<Mahasiswa, String> emailCol = new TableColumn<>("Email");
        TableColumn<Mahasiswa, String> fakultasCol = new TableColumn<>("Fakultas");
        TableColumn<Mahasiswa, String> jurusanCol = new TableColumn<>("Jurusan");
        TableColumn<Mahasiswa, String> alamatCol = new TableColumn<>("Alamat");
        TableColumn<Mahasiswa, String> kotaCol = new TableColumn<>("Kota");

        table.getColumns().addAll(namaCol, nimCol, emailCol, fakultasCol, jurusanCol, alamatCol, kotaCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        // Input fields and Create button
        addNama = new TextField();
        addNama.setPromptText("Nama Mahasiswa");

        addNim = new TextField();
        addNim.setPromptText("NIM");

        addEmail = new TextField();
        addEmail.setPromptText("Email");

        addFakultas = new TextField();
        addFakultas.setPromptText("Fakultas");

        addJurusan = new TextField();
        addJurusan.setPromptText("Jurusan");

        addAlamat = new TextField();
        addAlamat.setPromptText("Alamat");

        addKota = new TextField();
        addKota.setPromptText("Kota");

        final Button createButton = new Button("Create");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (validateInput()) {
                    Mahasiswa newMahasiswa = new Mahasiswa(
                            addNama.getText(),
                            addNim.getText(),
                            addEmail.getText(),
                            addFakultas.getText(),
                            addJurusan.getText(),
                            addAlamat.getText(),
                            addKota.getText()
                    );

                    table.getItems().add(newMahasiswa);

                    clearFields();
                }
            }
        });

        final HBox hboxInput = new HBox(10);
        hboxInput.getChildren().addAll(addNama, addNim, addEmail, addFakultas, addJurusan, addAlamat, addKota, createButton);

        vbox.getChildren().addAll(hboxInput);

        // Set cell value factories
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nimCol.setCellValueFactory(new PropertyValueFactory<>("nim"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        fakultasCol.setCellValueFactory(new PropertyValueFactory<>("fakultas"));
        jurusanCol.setCellValueFactory(new PropertyValueFactory<>("jurusan"));
        alamatCol.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        kotaCol.setCellValueFactory(new PropertyValueFactory<>("kota"));

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private boolean validateInput() {
        if (addNama.getText().isEmpty() || addNim.getText().isEmpty() || addEmail.getText().isEmpty() ||
                addFakultas.getText().isEmpty() || addJurusan.getText().isEmpty() || addAlamat.getText().isEmpty() ||
                addKota.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Input Error", "Please fill in all fields.");
            return false;
        }

        try {
            Integer.parseInt(addNim.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Input Error", "NIM must be a number.");
            return false;
        }

        if (!addEmail.getText().endsWith("@webmail.umm.ac.id")) {
            showAlert(AlertType.ERROR, "Error", "Input Error", "Email must end with @webmail.umm.ac.id");
            return false;
        }

        return true;
    }

    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        addNama.clear();
        addNim.clear();
        addEmail.clear();
        addFakultas.clear();
        addJurusan.clear();
        addAlamat.clear();
        addKota.clear();
    }

    public static class Mahasiswa {
        private final String nama;
        private final String nim;
        private final String email;
        private final String fakultas;
        private final String jurusan;
        private final String alamat;
        private final String kota;

        public Mahasiswa(String nama, String nim, String email, String fakultas, String jurusan, String alamat, String kota) {
            this.nama = nama;
            this.nim = nim;
            this.email = email;
            this.fakultas = fakultas;
            this.jurusan = jurusan;
            this.alamat = alamat;
            this.kota = kota;
        }

        public String getNama() {
            return nama;
        }

        public String getNim() {
            return nim;
        }

        public String getEmail() {
            return email;
        }

        public String getFakultas() {
            return fakultas;
        }

        public String getJurusan() {
            return jurusan;
        }

        public String getAlamat() {
            return alamat;
        }

        public String getKota() {
            return kota;
        }
    }
}

