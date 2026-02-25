module com.diego.arbolbinario {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.diego.arbolbinario to javafx.fxml;
    exports com.diego.arbolbinario;
}