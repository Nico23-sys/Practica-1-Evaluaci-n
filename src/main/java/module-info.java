module edu.nob.liceo.ejerevaluacionnob {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires edu.nob.liceo.ejerevaluacionnob;

    opens edu.nob.liceo.ejerevaluacionnob to javafx.fxml;
    exports edu.nob.liceo.ejerevaluacionnob;

    opens edu.nob.liceo.ejerevaluacionnob.controllers to javafx.fxml;
    exports edu.nob.liceo.ejerevaluacionnob.controllers ;

    opens edu.nob.liceo.ejerevaluacionnob.model to javafx.fxml;
    exports edu.nob.liceo.ejerevaluacionnob.model;

    opens edu.nob.liceo.ejerevaluacionnob.db to javafx.fxml;
    exports edu.nob.liceo.ejerevaluacionnob.db;
}