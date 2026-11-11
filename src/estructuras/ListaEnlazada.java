package estructuras;

public class ListaEnlazada<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int cantidad;
    public boolean esVacia(){
        return this.cantidad == 0;
    }
    public int obtenerCantidad(){
        return this.cantidad;
    }
    public void agregarAlFinal(T elemento){
        Nodo<T> nodo = new Nodo<>(elemento);
        if (esVacia()) {
            primero = nodo;
            ultimo = nodo;
        }else {
            ultimo.establecerSiguiente(nodo);
            ultimo = nodo;
        }
        this.cantidad++;
    }
    public T obtenerEn(int indice){
        if (indice < 0 || indice >= this.cantidad) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
    Nodo<T> nodoActual = this.primero;
        for (int i = 0; i < indice; i++) {
            nodoActual = nodoActual.obtenerSiguiente();
        }
        return nodoActual.obtenerDato();
    }
    public T eliminarEn(int indice){
        if (indice < 0 || indice >= this.cantidad) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
        if(indice==0){
            Nodo<T> nodoEliminado = this.primero;
            this.primero = this.primero.obtenerSiguiente();
            this.cantidad--;
            if(this.cantidad==0){
                this.ultimo = null;
            }
            return nodoEliminado.obtenerDato();
        }
        Nodo<T> anterior = this.primero;
        for (int i = 0; i < indice - 1; i++) {
            anterior = anterior.obtenerSiguiente();
        }
        Nodo<T> nodoEliminado = anterior.obtenerSiguiente();
        anterior.establecerSiguiente(nodoEliminado.obtenerSiguiente());
        if (nodoEliminado == this.ultimo) {
            this.ultimo = anterior;
        }
        this.cantidad--;
        return nodoEliminado.obtenerDato();
    }
    public void mostrar(){
        Nodo<T> nodoActual = this.primero;
        while (nodoActual != null) {
            System.out.print(nodoActual.obtenerDato() + " -> ");
            nodoActual = nodoActual.obtenerSiguiente();
        }
        System.out.println("null");
    }
    public void agregar(int indice, T elemento){
        if (indice < 0 || indice > this.cantidad) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");

        }
        if (indice == 0) {
            Nodo<T> nuevo = new Nodo<>(elemento);
            nuevo.establecerSiguiente(this.primero);
            this.primero = nuevo;

            if (this.cantidad == 0) {
                this.ultimo = nuevo;
            }

            this.cantidad++;
            return;
        }
        Nodo<T> anterior = this.primero;

        for (int i = 0; i < indice - 1; i++) {
            anterior = anterior.obtenerSiguiente();
        }
        Nodo<T> nuevo = new Nodo<>(elemento);

        nuevo.establecerSiguiente(anterior.obtenerSiguiente());

        anterior.establecerSiguiente(nuevo);
        if (nuevo.obtenerSiguiente() == null) {
            this.ultimo = nuevo;
        }

        this.cantidad++;
    }

}
