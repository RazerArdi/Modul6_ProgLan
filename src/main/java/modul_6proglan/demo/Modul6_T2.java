package modul_6proglan.demo;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
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
import java.util.*;

public class Modul6_T2 extends Application {
    private TableView<JadwalKuliah> table = new TableView<>();

    private TextField addNamaDosen;
    private TextField addMataKuliah;
    private ComboBox<String> addGKB;
    private ComboBox<String> addWaktu1;
    private ComboBox<String> addWaktu2;
    private ComboBox<String> addWaktu3;
    private ComboBox<String> addRuangan;

    private Map<String, Map<String, Set<String>>> jadwalMap = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Aplikasi Jadwal Kuliah1");
        stage.setWidth(800);
        stage.setHeight(600);

        final Label label = new Label("Jadwal Kuliah");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<JadwalKuliah, Boolean> deleteCol = new TableColumn<>("Hapus");
        TableColumn<JadwalKuliah, String> namaDosenCol = new TableColumn<>("Nama Dosen");
        TableColumn<JadwalKuliah, String> mataKuliahCol = new TableColumn<>("Mata Kuliah");
        TableColumn<JadwalKuliah, String> gkbCol = new TableColumn<>("GKB");
        TableColumn<JadwalKuliah, String> waktuCol = new TableColumn<>("Waktu");
        TableColumn<JadwalKuliah, String> ruanganCol = new TableColumn<>("Ruangan");

        table.getColumns().addAll(deleteCol, namaDosenCol, mataKuliahCol, gkbCol, waktuCol, ruanganCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        addNamaDosen = new TextField();
        addNamaDosen.setPromptText("Nama Dosen");

        addMataKuliah = new TextField();
        addMataKuliah.setPromptText("Mata Kuliah");

        addGKB = new ComboBox<>();
        addGKB.getItems().addAll("GKB 1", "GKB 2", "GKB 3", "GKB 4");
        addGKB.setPromptText("Pilih GKB");

        addWaktu1 = new ComboBox<>();
        addWaktu1.getItems().addAll(
                "07.00-07.50", "07.50-08.40", "08.40-09.30", "09.30-10.20",
                "10.20-11.10", "11.10-12.00 (SHALAT DHUHUR)", "12.00-12.45",
                "12.45-13.35", "13.35-14.25 (SHALAT ASHAR)", "14.25-15.15", "15.15-15.30",
                "15.30-16.20", "16.20-17.10", "17.10-18.00 (SHALAT MAGHRIB)",
                "18.00-18.30", "18.30-19.20", "19.20-20.10", "20.10-21.00"
        );
        addWaktu1.setPromptText("Pilih Waktu 1");

        addWaktu2 = new ComboBox<>();
        addWaktu2.getItems().addAll(
                "07.00-07.50", "07.50-08.40", "08.40-09.30", "09.30-10.20",
                "10.20-11.10", "11.10-12.00 (SHALAT DHUHUR)", "12.00-12.45",
                "12.45-13.35", "13.35-14.25 (SHALAT ASHAR)", "14.25-15.15", "15.15-15.30",
                "15.30-16.20", "16.20-17.10", "17.10-18.00 (SHALAT MAGHRIB)",
                "18.00-18.30", "18.30-19.20", "19.20-20.10", "20.10-21.00"
        );
        addWaktu2.setPromptText("Pilih Waktu 2");

        addWaktu3 = new ComboBox<>();
        addWaktu3.getItems().addAll(
                "07.00-07.50", "07.50-08.40", "08.40-09.30", "09.30-10.20",
                "10.20-11.10", "11.10-12.00 (SHALAT DHUHUR)", "12.00-12.45",
                "12.45-13.35", "13.35-14.25 (SHALAT ASHAR)", "14.25-15.15", "15.15-15.30",
                "15.30-16.20", "16.20-17.10", "17.10-18.00 (SHALAT MAGHRIB)",
                "18.00-18.30", "18.30-19.20", "19.20-20.10", "20.10-21.00"
        );
        addWaktu3.setPromptText("Pilih Waktu 3");

        addRuangan = new ComboBox<>();
        addRuangan.setPromptText("Pilih Ruangan");

        addGKB.setOnAction(e -> updateRuanganOptions(addGKB.getValue()));
        addWaktu1.setOnAction(e -> updateRuanganOptions(addGKB.getValue()));
        addWaktu2.setOnAction(e -> updateRuanganOptions(addGKB.getValue()));
        addWaktu3.setOnAction(e -> updateRuanganOptions(addGKB.getValue()));

        final Button createButton = new Button("Tambah Jadwal");
        createButton.setOnAction(e -> {
            if (validateInput()) {
                List<String> selectedWaktu = new ArrayList<>();
                if (addWaktu1.getValue() != null) selectedWaktu.add(addWaktu1.getValue());
                if (addWaktu2.getValue() != null) selectedWaktu.add(addWaktu2.getValue());
                if (addWaktu3.getValue() != null) selectedWaktu.add(addWaktu3.getValue());

                JadwalKuliah newJadwal = new JadwalKuliah(
                        addNamaDosen.getText(),
                        addMataKuliah.getText(),
                        addGKB.getValue(),
                        selectedWaktu,
                        addRuangan.getValue()
                );

                table.getItems().add(newJadwal);

                updateJadwalMap(newJadwal);

                saveDataToFile("DataT2.txt", table.getItems());

                clearFields();
            }
        });

        final Button deleteButton = new Button("Hapus Data yang Dipilih");
        deleteButton.setOnAction(e -> deleteSelectedRows());

        final HBox hboxInput = new HBox(10);
        hboxInput.getChildren().addAll(addNamaDosen, addMataKuliah, addGKB, addWaktu1, addWaktu2, addWaktu3, addRuangan, createButton, deleteButton);

        vbox.getChildren().addAll(hboxInput);

        deleteCol.setCellValueFactory(cellData -> cellData.getValue().deleteProperty());
        deleteCol.setCellFactory(CheckBoxTableCell.forTableColumn(deleteCol));
        deleteCol.setEditable(true);

        namaDosenCol.setCellValueFactory(new PropertyValueFactory<>("namaDosen"));
        mataKuliahCol.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));
        gkbCol.setCellValueFactory(new PropertyValueFactory<>("gkb"));
        waktuCol.setCellValueFactory(cellData -> {
            JadwalKuliah jadwal = cellData.getValue();
            List<String> waktuList = jadwal.getWaktuList();
            return new SimpleStringProperty(String.join(" | ", waktuList));
        });
        ruanganCol.setCellValueFactory(new PropertyValueFactory<>("ruangan"));

        loadExistingData("DataT2.txt");

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private void deleteSelectedRows() {
        List<JadwalKuliah> selectedJadwal = new ArrayList<>();
        for (JadwalKuliah jadwal : table.getItems()) {
            if (jadwal.isDelete()) {
                selectedJadwal.add(jadwal);
            }
        }

        table.getItems().removeAll(selectedJadwal);
        clearData();

        saveDataToFile("DataT2.txt", table.getItems());
    }

    private void updateJadwalMap(JadwalKuliah jadwal) {
        List<String> waktuList = jadwal.getWaktuList();
        for (String waktu : waktuList) {
            jadwalMap.computeIfAbsent(jadwal.getGkb(), k -> new HashMap<>())
                    .computeIfAbsent(jadwal.getRuangan(), k -> new HashSet<>())
                    .add(waktu);
        }
    }

    private boolean validateInput() {
        if (addNamaDosen.getText().isEmpty() || addMataKuliah.getText().isEmpty()
                || addGKB.getValue() == null || addRuangan.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Input Error", "Harap isi semua kolom.");
            return false;
        }

        List<String> selectedWaktu = new ArrayList<>();
        if (addWaktu1.getValue() != null) selectedWaktu.add(addWaktu1.getValue());
        if (addWaktu2.getValue() != null) selectedWaktu.add(addWaktu2.getValue());
        if (addWaktu3.getValue() != null) selectedWaktu.add(addWaktu3.getValue());

        if (selectedWaktu.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Input Error", "Pilih minimal satu waktu.");
            return false;
        }

        if (isRuanganConflict(addGKB.getValue(), addRuangan.getValue(), selectedWaktu)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Konflik Jadwal", "Ruang sudah terisi untuk waktu tersebut.");
            return false;
        }

        for (String waktu : selectedWaktu) {
            try {
                Integer.parseInt(waktu.substring(0, 2));
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Input Error", "Format waktu tidak valid.");
                return false;
            }
        }

        return true;
    }

    private boolean isRuanganConflict(String gkb, String ruangan, List<String> waktuList) {
        Map<String, Set<String>> ruanganMap = jadwalMap.getOrDefault(gkb, Collections.emptyMap());

        for (String waktu : waktuList) {
            for (Map.Entry<String, Set<String>> entry : ruanganMap.entrySet()) {
                String existingRuangan = entry.getKey();
                Set<String> existingWaktuSet = entry.getValue();

                if (existingRuangan.equals(ruangan) && existingWaktuSet.contains(waktu)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void updateRuanganOptions(String gkb) {
        Set<String> ruanganOptions = getRuanganOptions(gkb);
        addRuangan.getItems().setAll(ruanganOptions);
        addRuangan.setValue(null);
    }

    private Set<String> getRuanganOptions(String gkb) {
        Set<String> existingRuangan = jadwalMap.getOrDefault(gkb, Collections.emptyMap()).keySet();
        Set<String> allRuangan = new HashSet<>();
        int maxRuangan = 10; // Jumlah ruangan per GKB

        for (int i = 1; i <= maxRuangan; i++) {
            allRuangan.add(String.format("%03d", i));
        }

        allRuangan.removeAll(existingRuangan);
        return allRuangan;
    }

    private void clearData() {
        jadwalMap.clear();
        for (JadwalKuliah jadwal : table.getItems()) {
            updateJadwalMap(jadwal);
        }
    }

    private void clearFields() {
        addNamaDosen.clear();
        addMataKuliah.clear();
        addGKB.setValue(null);
        addWaktu1.setValue(null);
        addWaktu2.setValue(null);
        addWaktu3.setValue(null);
        addRuangan.setValue(null);
    }

    private void loadExistingData(String filename) {
        Path path = Paths.get(filename);

        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split(",");

                    if (parts.length >= 6) {
                        List<String> waktuList = Arrays.asList(Arrays.copyOfRange(parts, 3, parts.length));
                        JadwalKuliah jadwal = new JadwalKuliah(parts[0], parts[1], parts[2], waktuList, parts[parts.length - 1]);
                        table.getItems().add(jadwal);
                        updateJadwalMap(jadwal);
                    } else {
                        System.err.println("Invalid data format: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDataToFile(String filename, List<JadwalKuliah> jadwalList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (JadwalKuliah jadwal : jadwalList) {
                List<String> waktuList = jadwal.getWaktuList();
                writer.write(String.format("%s,%s,%s,%s,%s%n",
                        jadwal.getNamaDosen(),
                        jadwal.getMataKuliah(),
                        jadwal.getGkb(),
                        String.join(",", waktuList),
                        jadwal.getRuangan()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class JadwalKuliah {
        private final SimpleBooleanProperty delete;
        private final String namaDosen;
        private final String mataKuliah;
        private final String gkb;
        private final List<String> waktuList;
        private final String ruangan;

        public JadwalKuliah(String namaDosen, String mataKuliah, String gkb, List<String> waktuList, String ruangan) {
            this.delete = new SimpleBooleanProperty(false);
            this.namaDosen = namaDosen;
            this.mataKuliah = mataKuliah;
            this.gkb = gkb;
            this.waktuList = waktuList;
            this.ruangan = ruangan;
        }

        public ObservableValue<Boolean> deleteProperty() {
            return delete;
        }

        public boolean isDelete() {
            return delete.get();
        }

        public void setDelete(boolean delete) {
            this.delete.set(delete);
        }

        public String getNamaDosen() {
            return namaDosen;
        }

        public String getMataKuliah() {
            return mataKuliah;
        }

        public String getGkb() {
            return gkb;
        }

        public List<String> getWaktuList() {
            return waktuList;
        }

        public String getRuangan() {
            return ruangan;
        }

        @Override
        public String toString() {
            return "JadwalKuliah{" +
                    "delete=" + delete +
                    ", namaDosen='" + namaDosen + '\'' +
                    ", mataKuliah='" + mataKuliah + '\'' +
                    ", gkb='" + gkb + '\'' +
                    ", waktuList=" + waktuList +
                    ", ruangan='" + ruangan + '\'' +
                    '}';
        }
    }
}

