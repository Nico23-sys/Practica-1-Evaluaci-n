package edu.nob.liceo.ejerevaluacionnob.navegacion;

public enum AppView {
        LOGIN("/edu/nob/liceo/ejerevaluacionnob/hello-view.fxml"),
        MAIN("/edu/nob/liceo/ejerevaluacionnob/recursos/mainView.fxml"),
        USUARIOS("/edu/nob/liceo/ejerevaluacionnob/recursos/usuarios-view.fxml"),
        GOLFISTAS("/edu/nob/liceo/ejerevaluacionnob/recursos/golfistasview.fxml");
        private final String fxmlFile;

        AppView(String fxmlFile) {
            this.fxmlFile = fxmlFile;
        }

        public String getFxmlFile() {
            return fxmlFile;
        }

    }
