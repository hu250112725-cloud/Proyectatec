package estructuras;

public class ColaPrioridad<T> {
    private NodoPrioridad<T> primero;
    private int cantidad;

    public boolean esVacia() {
        return this.cantidad == 0;
    }

    public int obtenerCantidad() {
        return this.cantidad;
    }

    // Un numero menor representa una prioridad mayor: 1 sale antes que 2.
    public void encolar(T elemento, int prioridad) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(elemento, prioridad);
        if (esVacia()) {
            this.primero = nuevo;
        } else if (prioridad < this.primero.obtenerPrioridad()) {
            nuevo.establecerSiguiente(this.primero);
            this.primero = nuevo;
        } else {
            NodoPrioridad<T> actual = this.primero;
            // Avanzamos tambien sobre los empates: se respeta el orden de llegada.
            while (actual.obtenerSiguiente() != null
                    && actual.obtenerSiguiente().obtenerPrioridad() <= prioridad) {
                actual = actual.obtenerSiguiente();
            }
            nuevo.establecerSiguiente(actual.obtenerSiguiente());
            actual.establecerSiguiente(nuevo);
        }
        this.cantidad++;
    }

    public T desencolar() {
        if (esVacia()) {
            throw new IllegalStateException("La cola de prioridad esta vacia");
        }
        T dato = this.primero.obtenerDato();
        this.primero = this.primero.obtenerSiguiente();
        this.cantidad--;
        return dato;
    }

    public T verFrente() {
        if (esVacia()) {
            throw new IllegalStateException("La cola de prioridad esta vacia");
        }
        return this.primero.obtenerDato();
    }

    public void vaciar() {
        this.primero = null;
        this.cantidad = 0;
    }
}
