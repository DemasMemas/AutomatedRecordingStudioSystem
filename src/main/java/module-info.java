module zh.arss.automatedrecordingstudiosystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires mysql.connector.java;
    requires javafx.media;


    opens zh.arss to javafx.fxml;
    exports zh.arss;
    opens zh.arss.controller to javafx.fxml;
    exports zh.arss.controller;
    opens zh.arss.entity to javafx.fxml;
    exports zh.arss.entity;
    opens zh.arss.dto to javafx.fxml;
    exports zh.arss.dto;
}
