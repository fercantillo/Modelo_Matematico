package com.cenfotec.hormigas;

import com.cenfotec.hormigas.grafo.Arista;
import com.cenfotec.hormigas.grafo.Grafo;
import com.cenfotec.hormigas.grafo.Nodo;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = Grafo.construirGrafoColonia();

        System.out.println("Nodos del grafo (" + grafo.getNodos().size() + "): " + grafo.getNodos());
        System.out.println();

        mostrarAristas(grafo, "H");
        mostrarAristas(grafo, "I2");
        mostrarAristas(grafo, "F1");

        System.out.println("Grado de H: " + grafo.grado(grafo.getNodo("H")));
        System.out.println("Grado de I2: " + grafo.grado(grafo.getNodo("I2")));
        System.out.println("El grafo es conexo: " + grafo.esConexo());
    }

    private static void mostrarAristas(Grafo grafo, String id) {
        Nodo nodo = grafo.getNodo(id);
        System.out.println("Vecinos de " + nodo + ":");
        for (Arista arista : grafo.getAristas(nodo)) {
            System.out.println("  -> " + arista.getDestino() + " a " + arista.getPeso() + " m");
        }
        System.out.println();
    }
}
