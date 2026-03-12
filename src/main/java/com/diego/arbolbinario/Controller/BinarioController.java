package com.diego.arbolbinario.Controller;

import com.diego.arbolbinario.Model.ArbolBinario;
import com.diego.arbolbinario.Model.Nodo;
import com.diego.arbolbinario.Utils.Paths;
import com.diego.arbolbinario.Utils.SceneManager;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BinarioController {
    @FXML public TextField txtResultado;
    @FXML private Pane overlay;
    @FXML private Pane treePane;
    @FXML private Pane contenedor;
    @FXML private Button btnIngresar;
    @FXML private TextField txtNodo;
    @FXML private Button btnPreOrden;
    @FXML private Button btnInOrden;
    @FXML private Button btnPostOrden;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtEliminar;
    @FXML private Button btnBuscar;
    @FXML private Button btnEliminar;
    @FXML private Button btnGuardar;
    @FXML private Button btnCargar;


    ArbolBinario arbol = new ArbolBinario();

    // Mapa valor -> StackPane del nodo visual
    private final Map<Integer, StackPane> nodeViews = new HashMap<>();

    private static final double NODE_RADIUS = 20;
    private static final double V_SPACING  = 70;

    public void initialize() {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10);
        overlay.setEffect(blur);
    }

    public void ingNodo(MouseEvent e) {
        String text = txtNodo.getText().trim();
        if (text.isEmpty()) return;
        try {
            int x = Integer.parseInt(text);
            txtNodo.clear();
            arbol.Insertar(x);
            dibujarArbol();
        } catch (NumberFormatException ex) {
            txtNodo.clear();
        }
    }

    public void preOrden(MouseEvent e) {
        String resultado = arbol.preOrden(arbol.raiz);
        txtResultado.setText(resultado);
        animarRecorrido(obtenerOrdenPre(arbol.raiz));
    }

    public void inOrden(MouseEvent e) {
        String resultado = arbol.inOrden(arbol.raiz);
        txtResultado.setText(resultado);
        animarRecorrido(obtenerOrdenIn(arbol.raiz));
    }

    public void postOrden(MouseEvent e) {
        String resultado = arbol.postOrden(arbol.raiz);
        txtResultado.setText(resultado);
        animarRecorrido(obtenerOrdenPost(arbol.raiz));
    }


    void dibujarArbol() {
        treePane.getChildren().clear();
        nodeViews.clear();

        if (arbol.raiz == null) return;

        double startX = treePane.getPrefWidth() / 2.0;
        double startY = NODE_RADIUS + 10;

        dibujarNodo(arbol.raiz, startX, startY, startX / 2.0);
    }

    private void dibujarNodo(Nodo nodo, double x, double y, double offset) {
        if (nodo == null) return;

        // Dibujar ramas primero (quedan detrás de los nodos)
        if (nodo.izquierdo != null) {
            double childX = x - offset;
            double childY = y + V_SPACING;
            Line line = crearLinea(x, y, childX, childY);
            treePane.getChildren().add(line);
            dibujarNodo(nodo.izquierdo, childX, childY, offset / 2.0);
        }
        if (nodo.derecho != null) {
            double childX = x + offset;
            double childY = y + V_SPACING;
            Line line = crearLinea(x, y, childX, childY);
            treePane.getChildren().add(line);
            dibujarNodo(nodo.derecho, childX, childY, offset / 2.0);
        }

        // Crear círculo + etiqueta
        Circle circle = new Circle(NODE_RADIUS);
        circle.setFill(Color.web("#dadad3"));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        Label label = new Label(String.valueOf(nodo.valor));
        label.getStyleClass().add("tree-node-label");

        StackPane sp = new StackPane(circle, label);
        sp.setLayoutX(x - NODE_RADIUS);
        sp.setLayoutY(y - NODE_RADIUS);
        sp.setPrefSize(NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Animación de entrada
        sp.setScaleX(0);
        sp.setScaleY(0);
        ScaleTransition st = new ScaleTransition(Duration.millis(300), sp);
        st.setToX(1);
        st.setToY(1);

        treePane.getChildren().add(sp);
        nodeViews.put(nodo.valor, sp);

        st.play();
    }

    private Line crearLinea(double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(1.5);
        return line;
    }

    private void animarRecorrido(List<Integer> orden) {
        SequentialTransition seq = new SequentialTransition();

        for (int valor : orden) {
            StackPane sp = nodeViews.get(valor);
            if (sp == null) continue;

            Circle c = (Circle) sp.getChildren().get(0);

            // Encendido: color dorado + glow
            KeyFrame kfOn = new KeyFrame(Duration.millis(400), ev -> {
                c.setFill(Color.web("rgb(126,114,83)"));
                c.setStroke(Color.web("#114399"));
                c.setStrokeWidth(3);
                c.setEffect(new javafx.scene.effect.DropShadow(18,
                        Color.web("rgba(17,67,153,0.85)")));
            });

            // Apagado: vuelve al estado normal
            KeyFrame kfOff = new KeyFrame(Duration.millis(800), ev -> {
                c.setFill(Color.web("#dadad3"));
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(2);
                c.setEffect(null);
            });

            Timeline tl = new Timeline(kfOn, kfOff);
            seq.getChildren().add(new PauseTransition(Duration.millis(100)));
            seq.getChildren().add(tl);
        }

        seq.play();
    }

    private List<Integer> obtenerOrdenPre(Nodo n) {
        List<Integer> list = new ArrayList<>();
        preRec(n, list);
        return list;
    }
    private void preRec(Nodo n, List<Integer> l) {
        if (n == null) return;
        l.add(n.valor); preRec(n.izquierdo, l); preRec(n.derecho, l);
    }

    private List<Integer> obtenerOrdenIn(Nodo n) {
        List<Integer> list = new ArrayList<>();
        inRec(n, list);
        return list;
    }
    private void inRec(Nodo n, List<Integer> l) {
        if (n == null) return;
        inRec(n.izquierdo, l); l.add(n.valor); inRec(n.derecho, l);
    }

    private List<Integer> obtenerOrdenPost(Nodo n) {
        List<Integer> list = new ArrayList<>();
        postRec(n, list);
        return list;
    }
    private void postRec(Nodo n, List<Integer> l) {
        if (n == null) return;
        postRec(n.izquierdo, l); postRec(n.derecho, l); l.add(n.valor);
    }
    public void buscarNodo(MouseEvent e) {
        String text = txtBuscar.getText().trim();
        if (text.isEmpty()) return;
        try {
            int valor = Integer.parseInt(text);
            txtBuscar.clear();

            boolean encontrado = arbol.buscar(valor);
            txtResultado.setText(encontrado ? "Encontrado: " + valor : "No encontrado: " + valor);

            if (encontrado) animarBusqueda(valor);

        } catch (NumberFormatException ex) {
            txtBuscar.clear();
        }
    }

    public void eliminarNodo(MouseEvent e) {
        String text = txtEliminar.getText().trim();
        if (text.isEmpty()) return;
        try {
            int valor = Integer.parseInt(text);
            txtEliminar.clear();

            if (arbol.buscar(valor)) {
                // Primero animar el nodo que se va a borrar, despues elimar
                animarEliminacion(valor, () -> {
                    arbol.eliminar(valor);
                    dibujarArbol();
                    txtResultado.setText("Eliminado: " + valor);
                });
            } else {
                txtResultado.setText("No encontrado: " + valor);
            }

        } catch (NumberFormatException ex) {
            txtEliminar.clear();
        }
    }


    private void animarBusqueda(int valor) {
        StackPane sp = nodeViews.get(valor);
        if (sp == null) return;
        Circle c = (Circle) sp.getChildren().get(0);

        Timeline tl = new Timeline(
                new KeyFrame(Duration.millis(0), ev -> {
                    c.setFill(Color.web("#2ecc71"));
                    c.setStroke(Color.web("#27ae60"));
                    c.setStrokeWidth(3);
                    c.setEffect(new javafx.scene.effect.DropShadow(20, Color.web("#27ae60")));
                }),
                new KeyFrame(Duration.millis(900), ev -> {
                    c.setFill(Color.web("#dadad3"));
                    c.setStroke(Color.BLACK);
                    c.setStrokeWidth(2);
                    c.setEffect(null);
                })
        );
        tl.play();
    }

    private void animarEliminacion(int valor, Runnable onFinish) {
        StackPane sp = nodeViews.get(valor);
        if (sp == null) { onFinish.run(); return; }
        Circle c = (Circle) sp.getChildren().get(0);

        Timeline tl = new Timeline(
                new KeyFrame(Duration.millis(0), ev -> {
                    c.setFill(Color.web("#e74c3c"));
                    c.setStroke(Color.web("#c0392b"));
                    c.setStrokeWidth(3);
                    c.setEffect(new javafx.scene.effect.DropShadow(20, Color.web("#c0392b")));
                }),
                new KeyFrame(Duration.millis(600))
        );

        FadeTransition fade = new FadeTransition(Duration.millis(300), sp);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        SequentialTransition seq = new SequentialTransition(tl, fade);
        seq.setOnFinished(ev -> onFinish.run());
        seq.play();
    }

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
            dibujarArbol();
            txtResultado.setText("Cargado: " + archivo.getName());

        } catch (java.io.IOException ex) {
            txtResultado.setText("Error al cargar.");
            ex.printStackTrace();
        }
    }

    public void regresar(MouseEvent mouseEvent) {
        System.out.println(Paths.ArchivoView);
        SceneManager.cambiarContenido(contenedor, Paths.ArchivoView);
    }
}