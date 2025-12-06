package edu.nob.liceo.ejerevaluacionnob.controllers;

import edu.nob.liceo.ejerevaluacionnob.DatosPais;
import edu.nob.liceo.ejerevaluacionnob.dao.GolfistasDAO;
import edu.nob.liceo.ejerevaluacionnob.dao.GolfistasDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GolfistasController implements Initializable {


    @FXML private TableView<Golfistas> tablaGolfistas;
    @FXML    private TableColumn<Golfistas, Integer> colId;
    @FXML    private TableColumn<Golfistas, String> colNombre;
    @FXML    private TableColumn<Golfistas, String> colApellido;
    @FXML    private TableColumn<Golfistas, Integer> colEdad;
    @FXML    private TableColumn<Golfistas, String> colPais;
    @FXML    private TableColumn<Golfistas, String> colTipoPalo;


    @FXML    private Label GolfistaId;
    @FXML    private TextField tfNombre;
    @FXML    private TextField tfApellido;
    @FXML    private Slider sliderEdad;
    @FXML    private Label lblValorEdad;

    @FXML    private ComboBox<String> cbPais;
    @FXML    private ComboBox<String> cbTipoPalo;


    @FXML    private Button btnAnadir;
    @FXML    private Button btnModificar;
    @FXML    private Button btnEliminar;
    @FXML    private Button btnLimpiar;



    private ObservableList<Golfistas> listaGolfistas;

    private GolfistasDAO golfistasDAO;

    private Golfistas golfistaSeleccionado;

    public GolfistasController() {
        this.golfistasDAO = new GolfistasDAOImpl();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaGolfistas = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id_golfista"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTipoPalo.setCellValueFactory(new PropertyValueFactory<>("tipoPalo"));

        cbPais.setItems(FXCollections.observableArrayList(DatosPais.listapaises));
        cbTipoPalo.getItems().addAll("Driver", "Madera", "Hibrido", "Hierro", "Wedge", "Putter");

        sliderEdad.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblValorEdad.setText(String.valueOf(newValue.intValue()));
        });

        cargarGolfistasdelaBD();

        tablaGolfistas.setItems(listaGolfistas);

        tablaGolfistas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                golfistaSeleccionado = newValue;
                rellenarFormulario(golfistaSeleccionado);
            }
        });
    }

    public void handleAnadir(){
        if(camposValidos()){
            Golfistas newgolfista = new Golfistas(tfNombre.getText(), tfApellido.getText(),
                    (int) sliderEdad.getValue(), cbPais.getValue(), cbTipoPalo.getValue());

            golfistasDAO.addGolfistas(newgolfista);
            cargarGolfistasdelaBD();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION,"Golfista Añadido");
        }else{
            mostrarAlerta(Alert.AlertType.WARNING,"Datos incompletos");
        }
    }

    public void handleModificar(){
        if(golfistaSeleccionado != null){
            mostrarAlerta(Alert.AlertType.WARNING,"Ningún golfista seleccionado");

        return;
        }

        if(camposValidos()){
            golfistaSeleccionado.setNombre(tfNombre.getText());
            golfistaSeleccionado.setApellido(tfApellido.getText());
            golfistaSeleccionado.setEdad((int) sliderEdad.getValue());
            golfistaSeleccionado.setPais(cbPais.getValue());
            golfistaSeleccionado.setTipoPalo(cbTipoPalo.getValue());

            golfistasDAO.actuGolfista(golfistaSeleccionado);
            cargarGolfistasdelaBD();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION,"Golfista Modificado");
        }else {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos Incompletos, rellena los campos del formulario");

        }
    }

    private boolean camposValidos() {
        return !tfNombre.getText().isEmpty() &&
                !tfApellido.getText().isEmpty() &&
                cbPais.getValue() != null &&
                cbTipoPalo.getValue() != null;
    }

    private void rellenarFormulario(Golfistas golfista) {
        GolfistaId.setText(String.valueOf(golfista.getId_golfista()));
        tfNombre.setText(golfista.getNombre());
        tfApellido.setText(golfista.getApellido());
        sliderEdad.setValue(golfista.getEdad());
        lblValorEdad.setText(String.valueOf(golfista.getEdad()));
        cbPais.setValue(golfista.getPais());
        cbTipoPalo.setValue(golfista.getTipoPalo());
    }

    private void cargarGolfistasdelaBD() {
        listaGolfistas.clear();
        List<Golfistas> golfistasBD= golfistasDAO.getAllGolfistas();
        listaGolfistas.addAll(golfistasBD);
    }

}
