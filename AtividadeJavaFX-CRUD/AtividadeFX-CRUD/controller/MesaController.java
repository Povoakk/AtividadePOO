
package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import model.MesaRepositorio;
import model.Database;
import view.AppView;

public class MesaController implements Initializable {

    @FXML
    private TableView<view.Mesa> tabelaMesas;

    @FXML
    private TableColumn<view.Mesa, Integer> colunaId;

    @FXML
    private TableColumn<view.Mesa, String> colunaStatus;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoExcluir;

    private static Database database =
        new Database("mesas.db");

    private static MesaRepositorio mesaRepo =
        new MesaRepositorio(database);

    AppView appView;

    public MesaController() {
        this.appView = new AppView();
    }

    public static void main(String[] args) throws Exception {
        MesaController controller = new MesaController();
        controller.appView.run(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colunaId.setCellValueFactory(
            new PropertyValueFactory<>("id")
        );

        colunaStatus.setCellValueFactory(
            new PropertyValueFactory<>("status")
        );

        statusComboBox.setItems(
            FXCollections.observableArrayList("Livre", "Ocupada")
        );

        tabelaMesas.setItems(loadAllMesas());

        tabelaMesas.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    statusComboBox.setValue(newSelection.getStatus());
                }
            });
    }

    @FXML
    public void onCadastrarButtonAction() {
        try {
            String status = statusComboBox.getValue();

            if (status == null) {
                throw new Exception("Selecione um status.");
            }

            model.Mesa mesa = new model.Mesa();
            mesa.setStatus(status);

            model.Mesa mesaSalva = mesaRepo.create(mesa);

            view.Mesa mesaView = modelToView(mesaSalva);

            tabelaMesas.getItems().add(mesaView);
            tabelaMesas.getSelectionModel().select(mesaView);

        } catch (Exception e) {
            new Alert(
                AlertType.ERROR,
                "Erro ao cadastrar: " + e.getMessage()
            ).show();
        }
    }

    @FXML
    public void onAtualizarButtonAction() {
        try {
            view.Mesa mesaSelecionada =
                tabelaMesas.getSelectionModel().getSelectedItem();

            if (mesaSelecionada == null) {
                throw new Exception("Selecione uma mesa.");
            }

            String status = statusComboBox.getValue();

            if (status == null) {
                throw new Exception("Selecione um status.");
            }

            model.Mesa mesa = new model.Mesa();
            mesa.setId(mesaSelecionada.getId());
            mesa.setStatus(status);

            mesaRepo.update(mesa);

            mesaSelecionada.setStatus(status);

            tabelaMesas.refresh();

        } catch (Exception e) {
            new Alert(
                AlertType.ERROR,
                "Erro ao atualizar: " + e.getMessage()
            ).show();
        }
    }

    @FXML
    public void onExcluirButtonAction() {
        try {
            view.Mesa mesaSelecionada =
                tabelaMesas.getSelectionModel().getSelectedItem();

            if (mesaSelecionada == null) {
                throw new Exception("Selecione uma mesa.");
            }

            model.Mesa mesa = new model.Mesa();
            mesa.setId(mesaSelecionada.getId());
            mesa.setStatus(mesaSelecionada.getStatus());

            mesaRepo.delete(mesa);

            tabelaMesas.getItems().remove(mesaSelecionada);

            statusComboBox.setValue(null);

        } catch (Exception e) {
            new Alert(
                AlertType.ERROR,
                "Erro ao excluir: " + e.getMessage()
            ).show();
        }
    }

    private view.Mesa modelToView(model.Mesa mesa) {
        return new view.Mesa(
            mesa.getId(),
            mesa.getStatus()
        );
    }

    private ObservableList<view.Mesa> loadAllMesas() {

        ObservableList<view.Mesa> lista =
            FXCollections.observableArrayList();

        List<model.Mesa> listaFromDatabase =
            mesaRepo.loadAll();

        for (model.Mesa mesa : listaFromDatabase) {
            lista.add(modelToView(mesa));
        }

        return lista;
    }
}
