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

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
    
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("La vista de login está lista. Poniendo el foco en el campo de usuario.");

        Platform.runLater(()->usernameField.requestFocus());
    }
}
