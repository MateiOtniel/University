module project.schelet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens project to javafx.fxml;
    exports project;
    opens project.controller to javafx.fxml;
    exports project.controller;
    exports project.exception;
    opens project.exception to javafx.fxml;
}