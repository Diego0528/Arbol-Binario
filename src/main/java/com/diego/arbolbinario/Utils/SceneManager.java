package com.diego.arbolbinario.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage stagePrincipal;

    public static void setStage(Stage stage) {
        stagePrincipal = stage;
    }

    public static void cambiarEscena(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(rutaFXML));
            Parent root = loader.load();
            stagePrincipal.setScene(new Scene(root));
            stagePrincipal.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void cambiarContenido(Pane destino, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(rutaFXML));
            Parent vista = loader.load();
            destino.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}