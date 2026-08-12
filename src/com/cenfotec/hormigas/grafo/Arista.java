package com.cenfotec.hormigas.grafo;

public class Arista {

    private final Nodo origen;
    private final Nodo destino;
    private final int peso; // distancia en metros

    public Arista(Nodo origen, Nodo destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Nodo getOrigen() {
        return origen;
    }

    public Nodo getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origen + "-" + destino + " (" + peso + "m)";
    }
}
