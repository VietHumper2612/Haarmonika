module com.example.haarmonika {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.haarmonika to javafx.fxml;
    exports com.example.haarmonika;
    exports com.example.haarmonika.Controllers;
    opens com.example.haarmonika.Controllers to javafx.fxml;
    exports com.example.haarmonika.Objects;
    opens com.example.haarmonika.Objects to javafx.fxml;
}