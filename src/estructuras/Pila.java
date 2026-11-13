package estructuras;

public class Pila<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();

    public void apilar(T elemento) {
        elementos.agregar(0, elemento);
    }

    public T desapilar() {
        if (elementos.esVacia()) {
            throw new IllegalStateException("La estructura esta vacia");
        }
        return elementos.eliminarEn(0);
    }

    public T verTope() {
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