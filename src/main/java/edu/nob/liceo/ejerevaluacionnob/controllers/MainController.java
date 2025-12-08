package edu.nob.liceo.ejerevaluacionnob.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import edu.nob.liceo.ejerevaluacionnob.navegacion.AppView;
import edu.nob.liceo.ejerevaluacionnob.navegacion.SessionManager;
import edu.nob.liceo.ejerevaluacionnob.navegacion.ViewSwitcher;



public class MainController {
    @FXML
    private BorderPane mainContentPane;



    @FXML
    public void initialize() {

        ViewSwitcher.setMainContentPane(mainContentPane);

        ViewSwitcher.switchView(AppView.GOLFISTAS);
    }

    @FXML
    private void mostrarUsuarios() {
        ViewSwitcher.switchView(AppView.USUARIOS);
    }

    @FXML
    private void showGolfistas() {
        ViewSwitcher.switchView(AppView.GOLFISTAS);
    }

    @FXML
    private void mostrarTorneos() {
        ViewSwitcher.switchView(AppView.TORNEOS);
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        System.exit(0);
    }
}
