package com.diego.arbolbinario.Model;

public class Nodo {
    int valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int valor){
        this.valor = valor;
        izquierdo = null;
        derecho = null;
    }
}
