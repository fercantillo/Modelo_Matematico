package com.cenfotec.hormigas.grafo;

import java.util.Objects;

public class Nodo {

    private final String id;

    public Nodo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Nodo)) {
            return false;
        }
        Nodo otro = (Nodo) obj;
        return Objects.equals(id, otro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
