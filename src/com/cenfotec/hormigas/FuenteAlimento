package com.cenfotec.hormigas;

// nuevo  
import com.cenfotec.hormigas.grafo.Nodo;

public class FuenteAlimento {
    private final String id;
    private final Nodo nodoUbicacion;
    private boolean recolectado;

    public FuenteAlimento(String id, Nodo nodoUbicacion) {
        this.id = id;
        this.nodoUbicacion = nodoUbicacion;
        this.recolectado = false;
    }

    public String getId() {
        return id;
    }

    public Nodo getNodoUbicacion() {
        return nodoUbicacion;
    }

    public boolean isRecolectado() {
        return recolectado;
    }

    public void setRecolectado(boolean recolectado) {
        this.recolectado = recolectado;
    }

    @Override
    public String toString() {
        return "FuenteAlimento " + id + " en Nodo (" + nodoUbicacion + ") [Recolectado: " + recolectado + "]";
    }
}
// fin 
