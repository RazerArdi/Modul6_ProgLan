package modul_6proglan.demo;

import javafx.application.Application;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Modul6_T1 extends Application {
    private TableView<Mahasiswa> table = new TableView<>();

    private TextField addNama;
    private TextField addNim;
    private TextField addEmail;
    private TextField addFakultas;
    private TextField addJurusan;
    private TextField addAlamat;
    private TextField addKota;

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

                    saveDataToFile("Data.txt", table.getItems());

                    clearFields();
                }
            }
        });

        final Button clearDataButton = new Button("Clear Data");
        clearDataButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearData();
            }
        });

        final HBox hboxInput = new HBox(10);
        hboxInput.getChildren().addAll(addNama, addNim, addEmail, addFakultas, addJurusan, addAlamat, addKota, createButton, clearDataButton);

        vbox.getChildren().addAll(hboxInput);

        // Set cell value factories
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nimCol.setCellValueFactory(new PropertyValueFactory<>("nim"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        fakultasCol.setCellValueFactory(new PropertyValueFactory<>("fakultas"));
        jurusanCol.setCellValueFactory(new PropertyValueFactory<>("jurusan"));
        alamatCol.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        kotaCol.setCellValueFactory(new PropertyValueFactory<>("kota"));

        loadExistingData("Data.txt");

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
            long nimValue = Long.parseLong(addNim.getText());

            if (String.valueOf(nimValue).length() >= 20) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Input Error", "NIM must be a valid number with less than 20 digits.");
            return false;
        }

        if (!addEmail.getText().endsWith("@webmail.umm.ac.id")) {
            showAlert(AlertType.ERROR, "Error", "Input Error", "Email must end with @webmail.umm.ac.id");
            return false;
        }

        return true;
    }

    private void clearData() {
        table.getItems().clear();
        clearFields();
        saveDataToFile("Data.txt", table.getItems());
    }

    private void loadExistingData(String filename) {
        Path path = Paths.get(filename);

        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split(",");
                    Mahasiswa mahasiswa = new Mahasiswa(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    table.getItems().add(mahasiswa);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDataToFile(String filename, List<Mahasiswa> mahasiswaList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Mahasiswa mahasiswa : mahasiswaList) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s%n",
                        mahasiswa.getNama(),
                        mahasiswa.getNim(),
                        mahasiswa.getEmail(),
                        mahasiswa.getFakultas(),
                        mahasiswa.getJurusan(),
                        mahasiswa.getAlamat(),
                        mahasiswa.getKota()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
