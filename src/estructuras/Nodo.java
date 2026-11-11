package estructuras;

public class Nodo<T>{
    private T dato;
    private Nodo<T> siguiente;
    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
    }
    public T obtenerDato(){
        return this.dato;
    }
    public Nodo<T> obtenerSiguiente(){
        return this.siguiente;
    }
    public void establecerSiguiente(Nodo<T> siguiente){
        this.siguiente = siguiente;
    }
}
