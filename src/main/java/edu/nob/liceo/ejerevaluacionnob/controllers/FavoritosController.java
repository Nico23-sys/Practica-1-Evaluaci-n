package edu.nob.liceo.ejerevaluacionnob.controllers;

import edu.nob.liceo.ejerevaluacionnob.Favoritos.FavoritosDAO;
import edu.nob.liceo.ejerevaluacionnob.Favoritos.FavoritosDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.dao.GolfistasDAO;
import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;
import edu.nob.liceo.ejerevaluacionnob.model.Usuario;
import edu.nob.liceo.ejerevaluacionnob.navegacion.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FavoritosController implements Initializable {

    @FXML private TableView<Golfistas> tablaFavoritos;
    @FXML private TableColumn<Golfistas, String> colNombre;
    @FXML private TableColumn<Golfistas, String> colApellido;
    @FXML private TableColumn<Golfistas, String> colPais;
    @FXML private TableColumn<Golfistas, String> colTipoPalo;

    private FavoritosDAO favoritosDAO;
    private Usuario usuarioActual;

    private ObservableList<Golfistas> listaFav;

    public FavoritosController() {
        this.favoritosDAO=new FavoritosDAOImpl();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioActual= SessionManager.getInstance().getUsuarioactual();

        listaFav= FXCollections.observableArrayList();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTipoPalo.setCellValueFactory(new PropertyValueFactory<>("tipoPalo"));

        tablaFavoritos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaFavoritos.setItems(listaFav);

        cargarFavoritos();

    }

    private void cargarFavoritos(){
        if(usuarioActual!=null){
            listaFav.clear();
            List<Golfistas> favs= favoritosDAO.getFavoritosPorUsuario(usuarioActual.getUsuario_id());
            listaFav.addAll(favs);
        }
    }


}
