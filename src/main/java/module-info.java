module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
    exports org.example;
    /* Dobio sam gresku kako org.example doesn't open org.example.models.internetpackage to javafx.base, pa sam dodao ovu liniju koda. */
    exports org.example.controllers;
    opens org.example.controllers to javafx.fxml;
    exports org.example.util;
    opens org.example.util to javafx.fxml;
    exports org.example.main;
    opens org.example.main to javafx.fxml;
    exports org.example.models.internetpackage;
    opens org.example.models.internetpackage to javafx.base, javafx.fxml;
}
