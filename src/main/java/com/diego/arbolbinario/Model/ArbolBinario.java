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

    void encontrar(Nodo actual){
        if (actual.izquierdo != null){
            encontrar(actual.izquierdo);
            System.out.println(actual.valor);
        }
    }

    void eliminar(Nodo actual){
        if (actual.izquierdo != null){
            eliminar(actual.izquierdo);
            System.out.println(actual.valor);
        }
    }


}