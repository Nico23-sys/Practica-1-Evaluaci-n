package navegacion;

public enum AppView {
        LOGIN("/edu/nob/liceo/ejerevaluacionnob/hello-view.fxml"),
        MAIN("/edu/nob/liceo/ejerevaluacionnob/recursos/mainView.fxml"), // Nueva vista carcasa
        USUARIOS("/edu/nob/liceo/ejerevaluacionnob/recursos/usuarios-view.fxml");

        private final String fxmlFile;

        AppView(String fxmlFile) {
            this.fxmlFile = fxmlFile;
        }

        public String getFxmlFile() {
            return fxmlFile;
        }

    }
