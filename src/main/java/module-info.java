module com.diego.arbolbinario {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.diego.arbolbinario.Application to javafx.fxml;
    opens com.diego.arbolbinario.Controller to javafx.fxml;
    opens com.diego.arbolbinario.Model to javafx.fxml;
    opens com.diego.arbolbinario.Utils to javafx.fxml;
    exports com.diego.arbolbinario.Application;
    exports com.diego.arbolbinario.Controller;
    exports com.diego.arbolbinario.Model;
    exports com.diego.arbolbinario.Utils;

}