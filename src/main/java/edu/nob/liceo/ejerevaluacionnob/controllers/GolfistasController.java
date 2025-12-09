package edu.nob.liceo.ejerevaluacionnob.controllers;

import edu.nob.liceo.ejerevaluacionnob.DatosPais;
import edu.nob.liceo.ejerevaluacionnob.Favoritos.FavoritosDAO;
import edu.nob.liceo.ejerevaluacionnob.Favoritos.FavoritosDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.dao.GolfistasDAO;
import edu.nob.liceo.ejerevaluacionnob.dao.GolfistasDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;
import edu.nob.liceo.ejerevaluacionnob.model.Usuario;
import edu.nob.liceo.ejerevaluacionnob.navegacion.SessionManager;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GolfistasController implements Initializable {


    @FXML private TableView<Golfistas> tablaGolfistas;
    @FXML    private TableColumn<Golfistas, Integer> colId;
    @FXML    private TableColumn<Golfistas, String> colNombre;
    @FXML    private TableColumn<Golfistas, String> colApellido;
    @FXML    private TableColumn<Golfistas, Integer> colEdad;
    @FXML    private TableColumn<Golfistas, String> colPais;
    @FXML    private TableColumn<Golfistas, String> colTipoPalo;
    @FXML private TableColumn<Golfistas, String> colCategoria;


    @FXML    private Label GolfistaId;
    @FXML    private TextField tfNombre;
    @FXML    private TextField tfApellido;
    @FXML    private Slider sliderEdad;
    @FXML    private Label lblValorEdad;

    @FXML    private ComboBox<String> cbPais;
    @FXML    private ComboBox<String> cbTipoPalo;

    private FavoritosDAO favoritosDAO = new FavoritosDAOImpl();


    @FXML    private Button btnAnadir;
    @FXML    private Button btnModificar;
    @FXML    private Button btnEliminar;
    @FXML    private Button btnLimpiar;

    @FXML private TextField tfBuscar;



    private ObservableList<Golfistas> listaGolfistas;
    private ObservableList<Golfistas> listaGolfistasMaster;

    private GolfistasDAO golfistasDAO;

    private Timeline debounce;
    private static final int DEBOUNCE_DELAY_MS = 500;

    private Golfistas golfistaSeleccionado;

    public GolfistasController() {
        this.golfistasDAO = new GolfistasDAOImpl();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaGolfistas = FXCollections.observableArrayList();
        listaGolfistasMaster = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id_golfista"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTipoPalo.setCellValueFactory(new PropertyValueFactory<>("tipoPalo"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        cbPais.setItems(FXCollections.observableArrayList(DatosPais.listapaises));
        cbTipoPalo.getItems().addAll("Driver", "Madera", "Hibrido", "Hierro", "Wedge", "Putter");



        lblValorEdad.setText(String.valueOf((int) sliderEdad.getValue()));
        sliderEdad.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblValorEdad.setText(String.valueOf(newValue.intValue()));
        });

        cargarGolfistasdelaBD();

        tablaGolfistas.setItems(listaGolfistas);
        tablaGolfistas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tablaGolfistas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                golfistaSeleccionado = newValue;
                rellenarFormulario(golfistaSeleccionado);
            }
        });

        debounce = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), e -> {
            filtrarGolfistas(tfBuscar.getText());
        }));
        debounce.setCycleCount(1);


        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            debounce.stop();
            debounce.playFromStart();
        });
    }

    private void filtrarGolfistas(String terminoBusq) {
        listaGolfistas.clear();

        List<Golfistas> resultadoBusqueda;

        if (terminoBusq != null && !terminoBusq.trim().isEmpty()) {
            resultadoBusqueda = golfistasDAO.filtrarGolfistas(terminoBusq);
        } else {
            resultadoBusqueda = golfistasDAO.getAllGolfistas();
        }

        listaGolfistas.addAll(resultadoBusqueda);
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
        if(golfistaSeleccionado == null){
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

    public void handleEliminar(){
        if(golfistaSeleccionado == null){
            mostrarAlerta(Alert.AlertType.WARNING,"Ningún golfista seleccionado");
       return;
        }

        Alert confirmacion= new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("Seguro que quieres eliminar a: " + golfistaSeleccionado.getNombre()+" ?");
        confirmacion.setContentText("Esta accion no se puede deshacer");

        Optional<ButtonType> resultado= confirmacion.showAndWait();
        if (resultado.isPresent()&& resultado.get()==ButtonType.OK){
            golfistasDAO.deleteGolfista(golfistaSeleccionado.getId_golfista());
            cargarGolfistasdelaBD();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Golfista eliminado");
        }
    }

    public void handleLimpiar() {
        GolfistaId.setText("-");
        tfNombre.clear();
        tfApellido.clear();
        sliderEdad.setValue(0);
        cbPais.setValue(null);
        cbTipoPalo.setValue(null);
        golfistaSeleccionado=null;
        tablaGolfistas.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleAnadirFav() {
        if (golfistaSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un golfista primero");
            return;
        }

        Usuario user = SessionManager.getInstance().getUsuarioactual();

        if (favoritosDAO.yaEsFavorito(user.getUsuario_id(), golfistaSeleccionado.getId_golfista())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Este golfista ya está en tus favoritos");
        } else {
            favoritosDAO.addFavorito(user.getUsuario_id(), golfistaSeleccionado.getId_golfista());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Añadido a favoritos correctamente");
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
        try {
            List<Golfistas> golfistasBD = golfistasDAO.getAllGolfistas();
            if (golfistasBD != null) {
                listaGolfistas.addAll(golfistasBD);
                tablaGolfistas.refresh();
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void mostrarAlerta(Alert.AlertType type, String mensaje){
        Alert alerta = new Alert(type);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
