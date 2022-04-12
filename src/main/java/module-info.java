module com.amg.simplecalculatorserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires com.jfoenix;

    opens com.amg.simplecalculatorserver to javafx.fxml;
    exports com.amg.simplecalculatorserver;
}