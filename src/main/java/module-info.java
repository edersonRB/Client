module sistemasdistribuidos.cliente {
    requires javafx.controls;
    requires javafx.fxml;

    requires json;

    requires org.apache.commons.codec;

    opens sistemasdistribuidos.cliente to javafx.fxml;
    exports sistemasdistribuidos.cliente;
}