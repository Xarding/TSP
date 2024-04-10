module com.ding.neighbor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ding.neighbor to javafx.fxml;
    exports com.ding.neighbor;
    exports com.ding.neighbor.util;
    opens com.ding.neighbor.util to javafx.fxml;
}