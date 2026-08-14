package com.cenfotec.hormigas;

// nuevo 
import com.cenfotec.hormigas.grafo.Nodo;

public class Hormiga {
    private final String id;
    private Nodo nodoActual;
    private int energia;
    private boolean disponible;
    private FuenteAlimento alimentoAsignado;

    public Hormiga(String id, Nodo nodoInicio, int energiaInicial) {
        this.id = id;
        this.nodoActual = nodoInicio;
        this.energia = energiaInicial;
        this.disponible = true;
        this.alimentoAsignado = null;
    }

    public String getId() {
        return id;
    }

    public Nodo getNodoActual() {
        return nodoActual;
    }

    public void setNodoActual(Nodo nodoActual) {
        this.nodoActual = nodoActual;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public FuenteAlimento getAlimentoAsignado() {
        return alimentoAsignado;
    }

    public void setAlimentoAsignado(FuenteAlimento alimentoAsignado) {
        this.alimentoAsignado = alimentoAsignado;
        this.disponible = (alimentoAsignado == null);
    }

    public void consumirEnergia(int costo) {
        this.energia = Math.max(0, this.energia - costo);
    }

    @Override
    public String toString() {
        return "Hormiga " + id + " en Nodo (" + nodoActual + ") [Energía: " + energia + ", Disponible: " + disponible + "]";
    }
}
// fin package com.cenfotec.hormigas;

// nuevo 
import com.cenfotec.hormigas.grafo.Nodo;

public class Hormiga {
    private final String id;
    private Nodo nodoActual;
    private int energia;
    private boolean disponible;
    private FuenteAlimento alimentoAsignado;

    public Hormiga(String id, Nodo nodoInicio, int energiaInicial) {
        this.id = id;
        this.nodoActual = nodoInicio;
        this.energia = energiaInicial;
        this.disponible = true;
        this.alimentoAsignado = null;
    }

    public String getId() {
        return id;
    }

    public Nodo getNodoActual() {
        return nodoActual;
    }

    public void setNodoActual(Nodo nodoActual) {
        this.nodoActual = nodoActual;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public FuenteAlimento getAlimentoAsignado() {
        return alimentoAsignado;
    }

    public void setAlimentoAsignado(FuenteAlimento alimentoAsignado) {
        this.alimentoAsignado = alimentoAsignado;
        this.disponible = (alimentoAsignado == null);
    }

    public void consumirEnergia(int costo) {
        this.energia = Math.max(0, this.energia - costo);
    }

    @Override
    public String toString() {
        return "Hormiga " + id + " en Nodo (" + nodoActual + ") [Energía: " + energia + ", Disponible: " + disponible + "]";
    }
}
// fin 
