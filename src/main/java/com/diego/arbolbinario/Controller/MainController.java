package com.diego.arbolbinario.Controller;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public Pane Main;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    public void goBinary() {
        System.out.println("Ir a Binary Tree");
        // cargar vista binary
    }

    public void goAVL() {
        System.out.println("Ir a AVL Tree");
        // cargar vista avl
    }
}