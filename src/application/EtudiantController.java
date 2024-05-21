package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class EtudiantController {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private RadioButton f;
    @FXML
    private RadioButton m;
    @FXML
    private ChoiceBox<String> filiere;
    @FXML
    private TableView<Etudiant> tableView;
    @FXML
    private TableColumn<Etudiant, Integer> idCol;
    @FXML
    private TableColumn<Etudiant, String> nomCol;
    @FXML
    private TableColumn<Etudiant, String> prenomCol;
    @FXML
    private TableColumn<Etudiant, String> sexeCol;
    @FXML
    private TableColumn<Etudiant, String> filiereCol;

    private ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();

    private final ToggleGroup sexeGroup = new ToggleGroup();
    private final EtudiantM etudiantManager = new EtudiantM();

    @FXML
    public void initialize() {
        f.setToggleGroup(sexeGroup);
        m.setToggleGroup(sexeGroup);
        sexeGroup.selectToggle(f);

        filiere.setItems(FXCollections.observableArrayList("DSI", "MDW", "SEM", "RSI"));

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        filiereCol.setCellValueFactory(new PropertyValueFactory<>("filiere"));

        tableView.setItems(etudiantList);
        loadData();
    }

    private void loadData() {
        etudiantList.setAll(etudiantManager.findAll());
    }

    @FXML
    private void add() {
        String selectedSexe = ((RadioButton) sexeGroup.getSelectedToggle()).getText();
        Etudiant etudiant = new Etudiant(nom.getText(), prenom.getText(), selectedSexe, filiere.getValue());
        if (etudiantManager.create(etudiant)) {
            loadData();
            clearForm();
        }
    }

    @FXML
    private void delete() {
        Etudiant selectedEtudiant = tableView.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null && etudiantManager.delete(selectedEtudiant)) {
            loadData();
        }
    }

    @FXML
    private void modify() {
        Etudiant selectedEtudiant = tableView.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            String selectedSexe = ((RadioButton) sexeGroup.getSelectedToggle()).getText();
            selectedEtudiant.setNom(nom.getText());
            selectedEtudiant.setPrenom(prenom.getText());
            selectedEtudiant.setSexe(selectedSexe);
            selectedEtudiant.setFiliere(filiere.getValue());
            if (etudiantManager.update(selectedEtudiant)) {
                loadData();
                clearForm();
            }
        }
    }

    private void clearForm() {
        nom.clear();
        prenom.clear();
        sexeGroup.selectToggle(f);
        filiere.setValue(null);
    }
}