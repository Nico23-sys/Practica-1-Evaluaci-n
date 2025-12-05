package edu.nob.liceo.ejerevaluacionnob.controllers;

import edu.nob.liceo.ejerevaluacionnob.dao.UsuarioDAO;
import edu.nob.liceo.ejerevaluacionnob.dao.UsuarioDAOImpl;
import edu.nob.liceo.ejerevaluacionnob.model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private UsuarioDAO usuariodao = new UsuarioDAOImpl();

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (usuariodao.validarCredenciales(user, pass)) {

            Usuario usuario = usuariodao.getUsuarioPorUsername(user);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/nob/liceo/ejerevaluacionnob/recursos/usuarios-view.fxml"));

            Parent root = loader.load();


                UsuarioController usuariosController = loader.getController();
                usuariosController.setUsuarioLogueado(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Error de credenciales");
            alert.setContentText("Por favor, verifica tu usuario y contraseña");
            alert.showAndWait();

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("La vista de login está lista. Poniendo el foco en el campo de usuario.");

        Platform.runLater(()->usernameField.requestFocus());
    }
    }



