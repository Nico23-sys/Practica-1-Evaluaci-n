package edu.nob.liceo.ejerevaluacionnob.controllers;


import edu.nob.liceo.ejerevaluacionnob.dao.UsuarioDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import edu.nob.liceo.ejerevaluacionnob.navegacion.SessionManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {


    private UsuarioDAOImpl usuarioDAO;
    @FXML private TextField tfBuscar;
    @FXML private TableView<Usuario> table;
    @FXML private TableColumn<Usuario,Integer> tId;
    @FXML private TableColumn<Usuario,String> tNombre;
    @FXML private TableColumn<Usuario,String> tApellido;
    @FXML private TableColumn<Usuario,String> tNickname;
    @FXML private TableColumn<Usuario, LocalDate> tFechaNacimiento;

    private ObservableList<Usuario> listaUsuariosMaster;
    private ObservableList<Usuario> listaUsuarios;
    private FilteredList<Usuario> listaFiltradaUsuarios;
    private Usuario usuarioLogueado;

    public UsuarioController(){
        this.usuarioDAO= new UsuarioDAOImpl();
    }

    private Timeline debounce;

    private static final int DEBOUNCE_DELAY_MS = 500;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.usuarioLogueado = SessionManager.getInstance().getUsuarioactual();

        if(this.usuarioLogueado==null){
            System.err.println("Usuario no encontrado");
        }

    listaUsuariosMaster = FXCollections.observableArrayList();

    listaUsuariosMaster.addAll(usuarioDAO.getAllUsuarios());

        tId.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
        tNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        table.setItems(listaUsuariosMaster);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//usado para redistribuir el espacio automaticamnte


        debounce = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), e -> {
            buscarUsuario(tfBuscar.getText());
        }));
        debounce.setCycleCount(1);


        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            debounce.stop();
            debounce.playFromStart();
        });



    }

    private void buscarUsuario(String terminoBusq) {
        listaUsuariosMaster.clear();

        List<Usuario> resultadoBusqueda;

        if (terminoBusq != null && !terminoBusq.trim().isEmpty()) {
            resultadoBusqueda = usuarioDAO.buscarUsuario(terminoBusq);
        } else {
            resultadoBusqueda = usuarioDAO.getAllUsuarios();
        }

        listaUsuariosMaster.addAll(resultadoBusqueda);
    }


    }


