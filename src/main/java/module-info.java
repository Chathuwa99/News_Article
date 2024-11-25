module org.example.test1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.test1 to javafx.fxml;
    exports org.example.test1;
    exports org.example.test1.Controller;
    opens org.example.test1.Controller to javafx.fxml;
    exports org.example.test1.Models;
    opens org.example.test1.Models to javafx.fxml;
}