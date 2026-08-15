package com.cenfotec.hormigas;

import com.cenfotec.hormigas.grafo.Arista;
import com.cenfotec.hormigas.grafo.Grafo;
import com.cenfotec.hormigas.grafo.Nodo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Simulacion {

    private final Grafo grafo;
    private final Hormiga hormiga;
    private final int pasosTotales;
    private final List<FuenteAlimento> fuentes;
    private final Random random;

    private List<Nodo> rutaActual;
    private int posicionRuta;
    private int contadorFuentes;
    private boolean regresandoHormiguero;
    private boolean obstaculoPresente;
    private boolean sinEnergia;

    private static final int COSTO_MOVIMIENTO = 10;

    // Prepara los elementos necesarios para iniciar la simulación
    public Simulacion(Grafo grafo, int pasosTotales) {
        this.grafo = grafo;
        this.pasosTotales = pasosTotales;

        this.hormiga = new Hormiga("H1", grafo.getNodo("H"), 100);

        this.fuentes = new ArrayList<>();
        this.random = new Random();
        this.rutaActual = new ArrayList<>();
        this.posicionRuta = 0;
        this.contadorFuentes = 0;
        this.regresandoHormiguero = false;
        this.obstaculoPresente = false;
        this.sinEnergia = false;
    }

    // Ejecuta la simulación durante la cantidad de pasos indicada
    public void ejecutar() {
        System.out.println();
        System.out.println("Simulación de recolección");

        for (int paso = 1; paso <= pasosTotales; paso++) {
            System.out.println();
            System.out.println("Paso de tiempo " + paso);

            // Cada 4 pasos se revisa si aparece un obstáculo
            if (paso % 4 == 0) {
                evaluarObstaculo();
            }

            // Cada 3 pasos aparece una nueva fuente de alimento
            if (paso % 3 == 0) {
                generarFuenteAlimento();
            }

            asignarFuentePendiente();
            moverHormiga();
            mostrarEstadoHormiga();
        }

        mostrarResumen();
    }

    // Determina aleatoriamente si la ruta H-I2 queda bloqueada
    private void evaluarObstaculo() {
        obstaculoPresente = random.nextBoolean();

        if (obstaculoPresente) {
            System.out.println("Obstáculo presente en la ruta H - I2.");
        } else {
            System.out.println("La ruta H - I2 está disponible.");
        }
    }

    // Genera una fuente de alimento en uno de los nodos F1 a F5
    private void generarFuenteAlimento() {
        String[] nodosFuente = {"F1", "F2", "F3", "F4", "F5"};

        int posicion = random.nextInt(nodosFuente.length);
        String idNodo = nodosFuente[posicion];

        contadorFuentes++;

        FuenteAlimento fuente = new FuenteAlimento(
                "A" + contadorFuentes,
                grafo.getNodo(idNodo)
        );

        fuentes.add(fuente);

        System.out.println("Nueva fuente de alimento: "
                + fuente.getId() + " en " + idNodo);
    }

    // Asigna una fuente pendiente cuando la hormiga está disponible
    private void asignarFuentePendiente() {

        // Evita asignar una nueva tarea si no queda energía para moverse
        if (hormiga.getEnergia() < COSTO_MOVIMIENTO) {
            sinEnergia = true;
            return;
        }

        if (!hormiga.isDisponible() || sinEnergia) {
            return;
        }

        for (FuenteAlimento fuente : fuentes) {
            if (!fuente.isRecolectado()) {
                hormiga.setAlimentoAsignado(fuente);

                rutaActual = buscarRuta(
                        hormiga.getNodoActual(),
                        fuente.getNodoUbicacion()
                );

                posicionRuta = 0;

                System.out.println("Fuente asignada a "
                        + hormiga.getId() + ": "
                        + fuente.getId() + " en "
                        + fuente.getNodoUbicacion());

                return;
            }
        }
    }

    // Mueve la hormiga un nodo por cada paso de tiempo
    private void moverHormiga() {
        if (hormiga.getAlimentoAsignado() == null || sinEnergia) {
            return;
        }

        if (rutaActual.isEmpty()) {
            return;
        }

        if (posicionRuta >= rutaActual.size() - 1) {
            verificarLlegada();
            return;
        }

        Nodo actual = hormiga.getNodoActual();
        Nodo siguiente = rutaActual.get(posicionRuta + 1);

        // Si aparece un obstáculo, se busca otro camino
        if (esRutaBloqueada(actual, siguiente)) {
            System.out.println("La ruta " + actual + " - "
                    + siguiente + " está bloqueada.");

            Nodo destino;

            if (regresandoHormiguero) {
                destino = grafo.getNodo("H");
            } else {
                destino = hormiga.getAlimentoAsignado().getNodoUbicacion();
            }

            rutaActual = buscarRuta(actual, destino);
            posicionRuta = 0;

            if (rutaActual.size() <= 1) {
                System.out.println("No se encontró una ruta disponible.");
                return;
            }

            siguiente = rutaActual.get(1);

            System.out.println("Se buscó una ruta alternativa.");
        }

        int distancia = obtenerPeso(actual, siguiente);

        // Comprueba si la energía alcanza para realizar otro movimiento
        if (hormiga.getEnergia() < COSTO_MOVIMIENTO) {
            sinEnergia = true;
            System.out.println("La hormiga no tiene energía suficiente para continuar.");
            return;
        }

        hormiga.setNodoActual(siguiente);
        hormiga.consumirEnergia(COSTO_MOVIMIENTO);
        posicionRuta++;

        System.out.println("Movimiento: " + actual + " -> " + siguiente);
        System.out.println("Distancia recorrida: " + distancia + " m");
        System.out.println("Energía consumida: " + COSTO_MOVIMIENTO);

        verificarLlegada();
    }

    // Verifica si la hormiga llegó al alimento o regresó al hormiguero
    private void verificarLlegada() {
        FuenteAlimento fuente = hormiga.getAlimentoAsignado();

        if (fuente == null) {
            return;
        }

        if (!regresandoHormiguero
                && hormiga.getNodoActual().equals(fuente.getNodoUbicacion())) {

            fuente.setRecolectado(true);
            regresandoHormiguero = true;

            System.out.println("Fuente " + fuente.getId() + " recolectada.");

            rutaActual = buscarRuta(
                    hormiga.getNodoActual(),
                    grafo.getNodo("H")
            );

            posicionRuta = 0;

            System.out.println("La hormiga regresa al hormiguero.");
            return;
        }

        if (regresandoHormiguero
                && hormiga.getNodoActual().equals(grafo.getNodo("H"))) {

            System.out.println("La hormiga regresó al hormiguero con el alimento.");

            hormiga.setAlimentoAsignado(null);

            rutaActual = new ArrayList<>();
            posicionRuta = 0;
            regresandoHormiguero = false;

            // Revisa si todavía tiene energía para realizar otra recolección
            if (hormiga.getEnergia() < COSTO_MOVIMIENTO) {
                sinEnergia = true;
            }
        }
    }

    // Indica si una conexión se encuentra bloqueada por el obstáculo
    private boolean esRutaBloqueada(Nodo origen, Nodo destino) {
        if (!obstaculoPresente) {
            return false;
        }

        return (origen.getId().equals("H") && destino.getId().equals("I2"))
                || (origen.getId().equals("I2") && destino.getId().equals("H"));
    }

    // Obtiene la distancia entre dos nodos conectados
    private int obtenerPeso(Nodo origen, Nodo destino) {
        for (Arista arista : grafo.getAristas(origen)) {
            if (arista.getDestino().equals(destino)) {
                return arista.getPeso();
            }
        }

        return 0;
    }

    // Busca una ruta entre dos nodos utilizando BFS
    private List<Nodo> buscarRuta(Nodo inicio, Nodo destino) {
        Queue<Nodo> pendientes = new ArrayDeque<>();
        Set<Nodo> visitados = new HashSet<>();
        Map<Nodo, Nodo> anterior = new HashMap<>();

        pendientes.add(inicio);
        visitados.add(inicio);

        while (!pendientes.isEmpty()) {
            Nodo actual = pendientes.poll();

            if (actual.equals(destino)) {
                break;
            }

            for (Nodo vecino : grafo.getVecinos(actual)) {

                // Las rutas bloqueadas no se consideran durante la búsqueda
                if (esRutaBloqueada(actual, vecino)) {
                    continue;
                }

                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    anterior.put(vecino, actual);
                    pendientes.add(vecino);
                }
            }
        }

        List<Nodo> ruta = new ArrayList<>();

        if (!visitados.contains(destino)) {
            return ruta;
        }

        Nodo actual = destino;

        while (actual != null) {
            ruta.add(actual);
            actual = anterior.get(actual);
        }

        Collections.reverse(ruta);

        return ruta;
    }

    // Muestra la información actual de la hormiga
    private void mostrarEstadoHormiga() {
        System.out.println("Hormiga: " + hormiga.getId());
        System.out.println("Posición actual: " + hormiga.getNodoActual());
        System.out.println("Energía: " + hormiga.getEnergia());
        System.out.println("Disponible: " + hormiga.isDisponible());

        if (sinEnergia) {
            System.out.println("Estado: sin energía suficiente");
        } else if (regresandoHormiguero) {
            System.out.println("Estado: regresando al hormiguero");
        } else if (hormiga.getAlimentoAsignado() != null) {
            System.out.println("Estado: buscando alimento");
        } else {
            System.out.println("Estado: disponible");
        }

        if (hormiga.getAlimentoAsignado() != null) {
            System.out.println("Fuente asignada: "
                    + hormiga.getAlimentoAsignado().getId()
                    + " en "
                    + hormiga.getAlimentoAsignado().getNodoUbicacion());
        } else {
            System.out.println("Fuente asignada: ninguna");
        }
    }

    // Muestra los resultados obtenidos al terminar la simulación
    private void mostrarResumen() {
        int recolectadas = 0;
        int pendientes = 0;

        for (FuenteAlimento fuente : fuentes) {
            if (fuente.isRecolectado()) {
                recolectadas++;
            } else {
                pendientes++;
            }
        }

        System.out.println();
        System.out.println("Resumen de la simulación");
        System.out.println("Fuentes generadas: " + fuentes.size());
        System.out.println("Fuentes recolectadas: " + recolectadas);
        System.out.println("Fuentes pendientes: " + pendientes);
        System.out.println("Energía final de " + hormiga.getId()
                + ": " + hormiga.getEnergia());
        System.out.println("Posición final de " + hormiga.getId()
                + ": " + hormiga.getNodoActual());

        if (sinEnergia) {
            System.out.println("Estado final: sin energía suficiente");
        } else {
            System.out.println("Estado final: operativo");
        }
    }
}
