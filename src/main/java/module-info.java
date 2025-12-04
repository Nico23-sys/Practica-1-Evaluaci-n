module edu.nob.liceo.ejerevaluacionnob {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;


    opens edu.nob.liceo.ejerevaluacionnob to javafx.fxml;
    exports edu.nob.liceo.ejerevaluacionnob;
    exports edu.nob.liceo.ejerevaluacionnob.controllers;
    opens edu.nob.liceo.ejerevaluacionnob.controllers to javafx.fxml;
}