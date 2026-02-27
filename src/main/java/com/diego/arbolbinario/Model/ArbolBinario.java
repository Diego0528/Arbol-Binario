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

    public void inOrden(Nodo actual){
        if (actual != null){
            inOrden(actual.izquierdo);
            System.out.println(actual.valor);
            inOrden(actual.derecho);
        }
    }

    public void preOrden(Nodo actual){
        if (actual != null){
            System.out.println(actual.valor);
            preOrden(actual.izquierdo);
            preOrden(actual.derecho);
        }
    }
    public void postOrden(Nodo actual){
        if (actual != null){
            postOrden(actual.izquierdo);
            postOrden(actual.derecho);
            System.out.println(actual.valor);
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
