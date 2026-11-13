package estructuras;

// Es parecido a Nodo, pero ademas guarda la prioridad.
public class NodoPrioridad<T> {
    private T dato;
    private int prioridad;
    private NodoPrioridad<T> siguiente;

    public NodoPrioridad(T dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
        this.siguiente = null;
    }

    public T obtenerDato() {
        return this.dato;
    }

    public int obtenerPrioridad() {
        return this.prioridad;
    }

    public NodoPrioridad<T> obtenerSiguiente() {
        return this.siguiente;
    }

    public void establecerSiguiente(NodoPrioridad<T> siguiente) {
        this.siguiente = siguiente;
    }
}
