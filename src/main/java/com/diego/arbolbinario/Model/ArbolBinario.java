package com.diego.arbolbinario.Model;

public class ArbolBinario {
    public Nodo raiz;

    public ArbolBinario(){
        raiz = null;
    }

    public void Insertar(int valor){
        raiz = insertarRecursico(raiz, valor);
    }

    private Nodo insertarRecursico(Nodo actual, int valor) {
        if (actual == null) return new Nodo(valor);

        if (valor < actual.valor)
            actual.izquierdo = insertarRecursico(actual.izquierdo, valor);
        else if(valor > actual.valor)
            actual.derecho = insertarRecursico(actual.derecho, valor);

        return actual;
    }

    public String preOrden(Nodo actual){
        StringBuilder sb = new StringBuilder();
        resolverPreOrden(actual, sb);
        return sb.toString().trim();
    }

    private void resolverPreOrden(Nodo actual, StringBuilder sb){
        if (actual != null){
            sb.append(actual.valor).append(",");
            resolverPreOrden(actual.izquierdo, sb);
            resolverPreOrden(actual.derecho, sb);
        }
    }

    public String inOrden(Nodo actual){
        StringBuilder sb = new StringBuilder();
        resolverInOrden(actual, sb);
        return sb.toString().trim();
    }

    private void resolverInOrden(Nodo actual, StringBuilder sb){
        if (actual != null){
            resolverInOrden(actual.izquierdo, sb);
            sb.append(actual.valor).append(",");
            resolverInOrden(actual.derecho, sb);
        }
    }

    public String postOrden(Nodo actual){
        StringBuilder sb = new StringBuilder();
        resolverPostOrden(actual, sb);
        return sb.toString().trim();
    }

    private void resolverPostOrden(Nodo actual, StringBuilder sb){
        if (actual != null){
            resolverPostOrden(actual.izquierdo, sb);
            resolverPostOrden(actual.derecho, sb);
            sb.append(actual.valor).append(",");
        }
    }


    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo actual, int valor) {
        if (actual == null) return false;
        if (valor == actual.valor) return true;
        if (valor < actual.valor)
            return buscarRecursivo(actual.izquierdo, valor);
        else
            return buscarRecursivo(actual.derecho, valor);
    }

    public void eliminar(int valor) {
        raiz = eliminarRecursivo(raiz, valor);
    }

    private Nodo eliminarRecursivo(Nodo actual, int valor) {
        if (actual == null) return null;

        if (valor < actual.valor) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, valor);
        } else if (valor > actual.valor) {
            actual.derecho = eliminarRecursivo(actual.derecho, valor);
        } else {
            // Nodo encontrado — 3 casos:

            // Caso 1: hoja
            if (actual.izquierdo == null && actual.derecho == null)
                return null;

            // Caso 2: un solo hijo
            if (actual.izquierdo == null) return actual.derecho;
            if (actual.derecho == null)   return actual.izquierdo;

            // Caso 3: dos hijos → sucesor in-orden (mínimo del subárbol derecho)
            int sucesor = minimoValor(actual.derecho);
            actual.valor = sucesor;
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor);
        }
        return actual;
    }

    private int minimoValor(Nodo nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo.valor;
    }
    //Exportar e Importar

    public String exportarTXT() {
        StringBuilder sb = new StringBuilder();
        exportarRecursivo(raiz, sb);
        // Quitar última coma
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void exportarRecursivo(Nodo actual, StringBuilder sb) {
        if (actual == null) return;
        sb.append(actual.valor).append(",");
        exportarRecursivo(actual.izquierdo, sb);
        exportarRecursivo(actual.derecho, sb);
    }

    public void importarTXT(String contenido) {
        raiz = null;
        String[] partes = contenido.trim().split(",");
        for (String parte : partes) {
            parte = parte.trim();
            if (!parte.isEmpty()) {
                try {
                    Insertar(Integer.parseInt(parte));
                } catch (NumberFormatException ignored) {}
            }
        }
    }
}