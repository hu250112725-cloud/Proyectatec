package estructuras;

public class Cola<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();

    public void encolar(T elemento) {
        elementos.agregarAlFinal(elemento);
    }

    public T desencolar() {
        if (elementos.esVacia()) {
            throw new IllegalStateException("La estructura esta vacia");
        }
        return elementos.eliminarEn(0);
    }

    public T verFrente() {
        if (elementos.esVacia()) {
            throw new IllegalStateException("La estructura esta vacia");
        }
        return elementos.obtenerEn(0);
    }

    public boolean esVacia() {
        return elementos.esVacia();
    }

    public int obtenerCantidad() {
        return elementos.obtenerCantidad();
    }

    public void vaciar() {
        elementos.vaciar();
    }
}
