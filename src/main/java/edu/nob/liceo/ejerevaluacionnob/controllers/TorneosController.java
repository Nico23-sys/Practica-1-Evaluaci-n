package edu.nob.liceo.ejerevaluacionnob.controllers;

import edu.nob.liceo.ejerevaluacionnob.DatosPais;
import edu.nob.liceo.ejerevaluacionnob.dao.TorneoDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.model.Torneo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TorneosController implements Initializable {
    @FXML private TableView<Torneo> tablaTorneos;
    @FXML private TableColumn<Torneo, Integer> colId;
    @FXML
    private TableColumn<Torneo, String> colNombre;
    @FXML private TableColumn<Torneo, Integer> colAnho;
    @FXML private TableColumn<Torneo, String> colModalidad;
    @FXML private TableColumn<Torneo, String> colPais;

    @FXML private TextField tfBuscar;


    @FXML private Label lblTorneoId;
    @FXML private TextField tfNombre;
    @FXML private TextField tfAnho;
    @FXML private ComboBox<String> cbModalidad;
    @FXML private ComboBox<String> cbPais;
    @FXML private TableColumn<Torneo, String> colEstado;


    private ObservableList<Torneo> listaTorneos;
    private ObservableList<Torneo> listaTorneosMaster;

    private TorneoDAOImpl torneosDAO;

    private Torneo torneoSeleccionado;


    private Timeline debounce;
    private static final int DEBOUNCE_DELAY_MS = 500;

    public TorneosController() {
    this.torneosDAO = new TorneoDAOImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listaTorneos=FXCollections.observableArrayList();
        listaTorneosMaster = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id_torneo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAnho.setCellValueFactory(new PropertyValueFactory<>("anho"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));

        cbModalidad.getItems().addAll("Stroke Play", "Match Play", "Stableford");
        cbPais.setItems(FXCollections.observableArrayList(DatosPais.listapaises));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarTorneosBD();

        tablaTorneos.setItems(listaTorneos);
        tablaTorneos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tablaTorneos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                torneoSeleccionado=newValue;
                rellenarFormulario(torneoSeleccionado);
            }
        });

        debounce = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), e -> {
            filtrarTorneos(tfBuscar.getText());
        }));
        debounce.setCycleCount(1);


        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            debounce.stop();
            debounce.playFromStart();
        });
    }

    private void filtrarTorneos(String terminoBusq) {
        listaTorneos.clear();
        List<Torneo> resultadoBusqueda;

        if (terminoBusq != null && !terminoBusq.trim().isEmpty()) {
            resultadoBusqueda= torneosDAO.filtrarTorneos(terminoBusq);
        }else{
            resultadoBusqueda= torneosDAO.getAllTorneos();
        }
        listaTorneos.addAll(resultadoBusqueda);
    }

    private void rellenarFormulario(Torneo torneoSeleccionado) {
        lblTorneoId.setText(String.valueOf(torneoSeleccionado.getId_torneo()));
        tfNombre.setText(torneoSeleccionado.getNombre());
        tfAnho.setText(String.valueOf(torneoSeleccionado.getAnho()));
        cbModalidad.setValue(torneoSeleccionado.getModalidad());
        cbPais.setValue(torneoSeleccionado.getPais());
    }

    private void cargarTorneosBD() {
        listaTorneos.clear();
        try {
            List<Torneo> torneosBD= torneosDAO.getAllTorneos();
            listaTorneos.addAll(torneosBD);
            tablaTorneos.refresh();
        }catch (Exception e){
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleAnadir(){
        if (camposValidos()){
            int anho = Integer.parseInt(tfAnho.getText());
            Torneo newTorneo= new Torneo(tfNombre.getText(), anho ,cbModalidad.getValue(), cbPais.getValue());
            torneosDAO.addTorneos(newTorneo);
            cargarTorneosBD();
            handleLimpiar();

        mostrarAlerta(Alert.AlertType.INFORMATION,"Torneo Añadido");
    }else{
        mostrarAlerta(Alert.AlertType.WARNING,"Datos incompletos");
    }
    }

    public void handleModificar(){
        if (torneoSeleccionado==null){
            mostrarAlerta(Alert.AlertType.WARNING,"Ningún torneo seleccionado");

            return;
        }
        if (camposValidos()){
            torneoSeleccionado.setNombre(tfNombre.getText());
            torneoSeleccionado.setAnho(Integer.parseInt(tfAnho.getText()));
            torneoSeleccionado.setModalidad(cbModalidad.getValue());
            torneoSeleccionado.setPais(cbPais.getValue());

            torneosDAO.actuTorneos(torneoSeleccionado);
            cargarTorneosBD();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION,"Torneo Modificado");
        }else {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos Incompletos, rellena los campos del formulario");

        }
    }
    public void handleEliminar(){
        if (torneoSeleccionado==null){
            mostrarAlerta(Alert.AlertType.WARNING,"Ningún Torneo seleccionado");
            return;
        }

        Alert confirmacion= new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("Seguro que quieres eliminar a: " + torneoSeleccionado.getNombre()+" ?");
        confirmacion.setContentText("Esta accion no se puede deshacer");

        Optional<ButtonType> resultado= confirmacion.showAndWait();
        if (resultado.isPresent()&& resultado.get()==ButtonType.OK){
            torneosDAO.eliminarTorneo(torneoSeleccionado.getId_torneo());
            cargarTorneosBD();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Golfista eliminado");
        }
    }

    public void handleLimpiar(){
        lblTorneoId.setText("-");
        tfNombre.clear();
        tfAnho.clear();
        cbModalidad.setValue(null);
        cbPais.setValue(null);
        torneoSeleccionado=null;
        tablaTorneos.getSelectionModel().clearSelection();

    }

    private boolean camposValidos() {
        return !tfNombre.getText().isEmpty() &&
                !tfAnho.getText().isEmpty() &&
                cbModalidad.getValue() != null &&
                cbPais.getValue() != null;
    }
    private void mostrarAlerta(Alert.AlertType type, String mensaje){
        Alert alerta = new Alert(type);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
