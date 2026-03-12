package com.diego.arbolbinario.Controller;

import com.diego.arbolbinario.Model.ArbolBinario;
import com.diego.arbolbinario.Utils.SceneManager;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ArchivoController {

    @FXML TextField txtResultado;
    private ArbolBinario arbol;

    BinarioController bc = new BinarioController();



    public void guardarArchivo(MouseEvent e) {
        if (arbol.raiz == null) {
            txtResultado.setText("El árbol está vacío.");
            return;
        }

        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
        fc.setTitle("Guardar árbol");
        fc.setInitialFileName("arbol.txt");
        fc.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Archivo de texto", "*.txt")
        );

        java.io.File archivo = fc.showSaveDialog(SceneManager.getStagePrincipal());
        if (archivo == null) return;

        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(
                new java.io.FileWriter(archivo))) {

            bw.write(arbol.exportarTXT());
            txtResultado.setText("Guardado: " + archivo.getName());

        } catch (java.io.IOException ex) {
            txtResultado.setText("Error al guardar.");
            ex.printStackTrace();
        }
    }

    public void cargarArchivo(MouseEvent e) {
        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
        fc.setTitle("Cargar árbol");
        fc.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Archivo de texto", "*.txt")
        );

        java.io.File archivo = fc.showOpenDialog(SceneManager.getStagePrincipal());
        if (archivo == null) return;

        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.FileReader(archivo))) {

            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) sb.append(linea);

            arbol.importarTXT(sb.toString());
            bc.dibujarArbol();
            txtResultado.setText("Cargado: " + archivo.getName());

        } catch (java.io.IOException ex) {
            txtResultado.setText("Error al cargar.");
            ex.printStackTrace();
        }
    }

    public void regresar(MouseEvent mouseEvent) {
    }
}
