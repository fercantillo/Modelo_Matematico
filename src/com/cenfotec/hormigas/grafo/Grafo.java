package com.cenfotec.hormigas.grafo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Grafo {

    private final Map<Nodo, List<Arista>> adyacencia = new LinkedHashMap<>();

    public void agregarNodo(Nodo nodo) {
        adyacencia.putIfAbsent(nodo, new ArrayList<>());
    }

    public void agregarArista(Nodo origen, Nodo destino, int peso) {
        agregarNodo(origen);
        agregarNodo(destino);
        // el grafo es no dirigido, por eso la arista se guarda en las listas de ambos nodos
        adyacencia.get(origen).add(new Arista(origen, destino, peso));
        adyacencia.get(destino).add(new Arista(destino, origen, peso));
    }

    public void agregarArista(String idOrigen, String idDestino, int peso) {
        agregarArista(new Nodo(idOrigen), new Nodo(idDestino), peso);
    }

    public List<Arista> getAristas(Nodo nodo) {
        return Collections.unmodifiableList(adyacencia.getOrDefault(nodo, new ArrayList<>()));
    }

    public List<Nodo> getVecinos(Nodo nodo) {
        List<Nodo> vecinos = new ArrayList<>();
        for (Arista arista : getAristas(nodo)) {
            vecinos.add(arista.getDestino());
        }
        return vecinos;
    }

    public Set<Nodo> getNodos() {
        return Collections.unmodifiableSet(adyacencia.keySet());
    }

    public Nodo getNodo(String id) {
        for (Nodo nodo : adyacencia.keySet()) {
            if (nodo.getId().equals(id)) {
                return nodo;
            }
        }
        return null;
    }

    public int grado(Nodo nodo) {
        return getAristas(nodo).size();
    }

    public boolean esConexo() {
        if (adyacencia.isEmpty()) {
            return true;
        }

        Nodo inicio = adyacencia.keySet().iterator().next();
        Set<Nodo> visitados = new HashSet<>();
        Queue<Nodo> pendientes = new ArrayDeque<>();
        visitados.add(inicio);
        pendientes.add(inicio);

        while (!pendientes.isEmpty()) {
            Nodo actual = pendientes.poll();
            for (Nodo vecino : getVecinos(actual)) {
                if (visitados.add(vecino)) {
                    pendientes.add(vecino);
                }
            }
        }

        // si el BFS desde un solo nodo alcanzó todos, el grafo tiene una única componente
        return visitados.size() == adyacencia.size();
    }

    public static Grafo construirGrafoColonia() {
        Grafo grafo = new Grafo();

        String[] ids = {"H", "I1", "I2", "I3", "T1", "T2", "T3", "F1", "F2", "F3", "F4", "F5"};
        for (String id : ids) {
            grafo.agregarNodo(new Nodo(id));
        }

        grafo.agregarArista("H", "I1", 15);
        grafo.agregarArista("H", "I2", 12);
        grafo.agregarArista("H", "I3", 16);
        grafo.agregarArista("I1", "I2", 10);
        grafo.agregarArista("I1", "T1", 12);
        grafo.agregarArista("I1", "F2", 18);
        grafo.agregarArista("I2", "T2", 11);
        grafo.agregarArista("I2", "F2", 20);
        grafo.agregarArista("I2", "F4", 19);
        grafo.agregarArista("I2", "I3", 11);
        grafo.agregarArista("I3", "F4", 17);
        grafo.agregarArista("I3", "T3", 13);
        grafo.agregarArista("T1", "F1", 10);
        grafo.agregarArista("T2", "F3", 14);
        grafo.agregarArista("T3", "F5", 14);

        return grafo;
    }
}
