package com.diego.arbolbinario.Application;

import com.diego.arbolbinario.Controller.BinarioController;
import com.diego.arbolbinario.Utils.Paths;
import com.diego.arbolbinario.Utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setStage(primaryStage);
        Parent root = FXMLLoader.load((getClass().getResource(Paths.MainView)));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Arbol Binario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);}

}
