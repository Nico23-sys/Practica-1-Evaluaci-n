package edu.nob.liceo.ejerevaluacionnob.navegacion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class ViewSwitcher {
    private static BorderPane mainContentPane;

    public static void setMainContentPane(BorderPane mainContentPane) {
        ViewSwitcher.mainContentPane = mainContentPane;
    }

    public static void switchView(AppView view) {
        if (mainContentPane == null) {
            System.err.println("Error: contenido no inicializado.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFxmlFile())));
             Parent viewRoot = loader.load();
            mainContentPane.setCenter(viewRoot);

        } catch (IOException e) {
            System.err.println("Error al cargar la vista");
            e.printStackTrace();
        }
    }
}
