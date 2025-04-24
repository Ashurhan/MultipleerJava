module com.devtiro.muitipleer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.devtiro.muitipleer to javafx.fxml;
    exports com.devtiro.muitipleer;
}