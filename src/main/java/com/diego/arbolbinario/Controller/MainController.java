package com.diego.arbolbinario.Controller;

import com.diego.arbolbinario.Model.ArbolBinario;
import javafx.fxml.FXML;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class MainController {

    public TextField txtResultado;
    @FXML
    private Pane overlay;
    @FXML
    private Button btnIngresar;
    @FXML
    private TextField txtNodo;
    @FXML
    private Button btnPreOrden;
    @FXML
    private Button btnInOrden;
    @FXML
    private Button btnPostOrden;


    ArbolBinario arbol = new ArbolBinario();

    public void initialize() {

        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(0);

        overlay.setEffect(blur);
    }


    public void ingNodo (MouseEvent mouseEvent) {
        int x = Integer.parseInt(txtNodo.getText());
        txtNodo.clear();
        System.out.println(x);
        arbol.Insertar(x);
    }



    public void preOrden(MouseEvent mouseEvent) {
        System.out.println("PreOrden");
        String resultado = arbol.preOrden(arbol.raiz);
        txtResultado.setText(resultado);
    }

    public void inOrden(MouseEvent mouseEvent) {
        System.out.println("InOrden");
        String resultado = arbol.inOrden(arbol.raiz);
        txtResultado.setText(resultado);
    }

    public void postOrden(MouseEvent mouseEvent) {
        System.out.println("PostOrden");
        String resultado = arbol.postOrden(arbol.raiz);
        txtResultado.setText(resultado);
    }


}