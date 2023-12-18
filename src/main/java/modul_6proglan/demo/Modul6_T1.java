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
    private TableView<Mahasiswa> tabel = new TableView<>();

    private TextField tambahNama;
    private TextField tambahNim;
    private TextField tambahEmail;
    private TextField tambahFakultas;
    private TextField tambahJurusan;
    private TextField tambahAlamat;
    private TextField tambahKota;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Form Biodata Mahasiswa V1.0");
        stage.setWidth(600);
        stage.setHeight(500);

        final Label label = new Label("Form Biodata Mahasiswa");
        label.setFont(new Font("Arial", 20));

        tabel.setEditable(true);

        TableColumn<Mahasiswa, String> namaKolom = new TableColumn<>("Nama");
        TableColumn<Mahasiswa, String> nimKolom = new TableColumn<>("NIM");
        TableColumn<Mahasiswa, String> emailKolom = new TableColumn<>("Email");
        TableColumn<Mahasiswa, String> fakultasKolom = new TableColumn<>("Fakultas");
        TableColumn<Mahasiswa, String> jurusanKolom = new TableColumn<>("Jurusan");
        TableColumn<Mahasiswa, String> alamatKolom = new TableColumn<>("Alamat");
        TableColumn<Mahasiswa, String> kotaKolom = new TableColumn<>("Kota");

        tabel.getColumns().addAll(namaKolom, nimKolom, emailKolom, fakultasKolom, jurusanKolom, alamatKolom, kotaKolom);

        final VBox vbox = new VBox();
        vbox.setSpacing(50);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        vbox.getChildren().addAll(label, tabel);

        tambahNama = new TextField();
        tambahNama.setPromptText("Nama Mahasiswa");

        tambahNim = new TextField();
        tambahNim.setPromptText("NIM");

        tambahEmail = new TextField();
        tambahEmail.setPromptText("Email");

        tambahFakultas = new TextField();
        tambahFakultas.setPromptText("Fakultas");

        tambahJurusan = new TextField();
        tambahJurusan.setPromptText("Jurusan");

        tambahAlamat = new TextField();
        tambahAlamat.setPromptText("Alamat");

        tambahKota = new TextField();
        tambahKota.setPromptText("Kota");

        final Button tombolBuat = new Button("Buat");
        tombolBuat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (validateInput()) {
                    Mahasiswa mahasiswaBaru = new Mahasiswa(
                            tambahNama.getText(),
                            tambahNim.getText(),
                            tambahEmail.getText(),
                            tambahFakultas.getText(),
                            tambahJurusan.getText(),
                            tambahAlamat.getText(),
                            tambahKota.getText()
                    );

                    tabel.getItems().add(mahasiswaBaru);

                    saveDataToFile("Data.txt", tabel.getItems());

                    clearFields();
                }
            }
        });

        final Button tombolHapusData = new Button("Hapus Data yang Dipilih");
        tombolHapusData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Mahasiswa mahasiswaDipilih = tabel.getSelectionModel().getSelectedItem();
                if (mahasiswaDipilih != null) {
                    tabel.getItems().remove(mahasiswaDipilih);
                    saveDataToFile("Data.txt", tabel.getItems());
                }
            }
        });

        final HBox hboxInput = new HBox(10);
        hboxInput.getChildren().addAll(tambahNama, tambahNim, tambahEmail, tambahFakultas, tambahJurusan, tambahAlamat, tambahKota, tombolBuat, tombolHapusData);

        vbox.getChildren().addAll(hboxInput);

        namaKolom.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nimKolom.setCellValueFactory(new PropertyValueFactory<>("nim"));
        emailKolom.setCellValueFactory(new PropertyValueFactory<>("email"));
        fakultasKolom.setCellValueFactory(new PropertyValueFactory<>("fakultas"));
        jurusanKolom.setCellValueFactory(new PropertyValueFactory<>("jurusan"));
        alamatKolom.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        kotaKolom.setCellValueFactory(new PropertyValueFactory<>("kota"));

        loadExistingData("Data.txt");

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private boolean validateInput() {
        if (tambahNama.getText().isEmpty() || tambahNim.getText().isEmpty() || tambahEmail.getText().isEmpty() ||
                tambahFakultas.getText().isEmpty() || tambahJurusan.getText().isEmpty() || tambahAlamat.getText().isEmpty() ||
                tambahKota.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Kesalahan Input", "Harap isi semua kolom.");
            return false;
        }

        try {
            long nimValue = Long.parseLong(tambahNim.getText());

            if (String.valueOf(nimValue).length() >= 20) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Kesalahan Input", "NIM harus angka valid dengan kurang dari 20 digit.");
            return false;
        }

        if (!tambahEmail.getText().endsWith("@webmail.umm.ac.id")) {
            showAlert(AlertType.ERROR, "Error", "Kesalahan Input", "Email harus diakhiri dengan @webmail.umm.ac.id");
            return false;
        }

        return true;
    }

    private void clearFields() {
        tambahNama.clear();
        tambahNim.clear();
        tambahEmail.clear();
        tambahFakultas.clear();
        tambahJurusan.clear();
        tambahAlamat.clear();
        tambahKota.clear();
    }

    private void loadExistingData(String filename) {
        Path path = Paths.get(filename);

        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split(",");
                    Mahasiswa mahasiswa = new Mahasiswa(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    tabel.getItems().add(mahasiswa);
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
