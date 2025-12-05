package edu.nob.liceo.ejerevaluacionnob.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import navegacion.AppView;
import navegacion.SessionManager;
import navegacion.ViewSwitcher;



public class MainController {
    @FXML
    private BorderPane mainContentPane;

    @FXML
    public void initialize() {

        ViewSwitcher.setMainContentPane(mainContentPane);

        ViewSwitcher.switchView(AppView.USUARIOS);
    }

    @FXML
    private void mostrarUsuarios() {
        ViewSwitcher.switchView(AppView.USUARIOS);
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        System.exit(0);
    }
}
