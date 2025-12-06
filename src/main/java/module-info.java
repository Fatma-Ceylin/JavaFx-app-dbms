module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    exports org.example.demo1;
    opens org.example.demo1 to javafx.graphics, javafx.fxml;


    exports org.example.demo1.controller;
    opens org.example.demo1.controller to javafx.fxml;

    exports org.example.demo1.model;
    opens org.example.demo1.model to javafx.base;
}
