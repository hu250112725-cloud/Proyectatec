import estructuras.ListaEnlazada;
import estructuras.Nodo;
import estructuras.Pila;
import estructuras.Cola;
import estructuras.ColaPrioridad;

public class PruebasEstructuras {
    private int pruebasCorrectas;

    // main solo crea un objeto y llama al metodo que ejecuta las pruebas.
    public static void main(String[] args) {
        PruebasEstructuras pruebas = new PruebasEstructuras();
        pruebas.ejecutar();
    }

    public void ejecutar() {
        probarEstructuras();
        probarOrdenamientos();
        System.out.println("PRUEBAS CORRECTAS: " + this.pruebasCorrectas);
        System.out.println();
        mostrarTabla();
    }

    private void comprobar(boolean resultado, String nombre) {
        if (resultado) {
            this.pruebasCorrectas++;
        } else {
            throw new IllegalStateException("Fallo la prueba: " + nombre);
        }
    }

    private void probarEstructuras() {
        Nodo<Integer> primero = new Nodo<>(10);
        Nodo<Integer> segundo = new Nodo<>(20);
        comprobar(primero.obtenerSiguiente() == null, "Nodo nuevo");
        primero.establecerSiguiente(segundo);
        comprobar(primero.obtenerSiguiente().obtenerDato() == 20, "Enlace de nodos");

        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        comprobar(lista.esVacia(), "Lista inicialmente vacia");
        lista.agregarAlFinal(20);
        lista.agregar(0, 10);
        lista.agregar(2, 40);
        lista.agregar(2, 30);
        comprobar(lista.obtenerCantidad() == 4, "Cantidad de la lista");
        for (int i = 0; i < 4; i++) {
            comprobar(lista.obtenerEn(i) == (i + 1) * 10, "Insercion en lista");
        }
        comprobar(lista.eliminarEn(2) == 30, "Eliminar medio");
        comprobar(lista.eliminarEn(2) == 40, "Eliminar ultimo");
        lista.agregarAlFinal(50);
        comprobar(lista.obtenerEn(2) == 50, "Agregar despues de eliminar ultimo");
        comprobar(lista.eliminarEn(0) == 10, "Eliminar primero");
        lista.eliminarEn(0);
        lista.eliminarEn(0);
        comprobar(lista.esVacia(), "Eliminar todos");
        lista.agregarAlFinal(7);
        comprobar(lista.eliminarEn(0) == 7, "Reutilizar lista");
        lista.agregarAlFinal(8);
        lista.vaciar();
        comprobar(lista.esVacia(), "Vaciar lista");

        Pila<Integer> pila = new Pila<>();
        comprobar(pila.esVacia(), "Pila nueva");
        pila.apilar(10);
        pila.apilar(20);
        pila.apilar(30);
        comprobar(pila.verTope() == 30 && pila.obtenerCantidad() == 3, "Consultar tope");
        comprobar(pila.desapilar() == 30, "Pila salida 1");
        comprobar(pila.desapilar() == 20, "Pila salida 2");
        comprobar(pila.desapilar() == 10, "Pila salida 3");
        comprobar(pila.esVacia(), "Pila vacia");
        pila.apilar(40);
        pila.vaciar();
        pila.apilar(50);
        comprobar(pila.desapilar() == 50, "Reutilizar pila");

        Cola<Integer> cola = new Cola<>();
        comprobar(cola.esVacia(), "Cola nueva");
        cola.encolar(10);
        cola.encolar(20);
        cola.encolar(30);
        comprobar(cola.verFrente() == 10 && cola.obtenerCantidad() == 3, "Consultar frente");
        comprobar(cola.desencolar() == 10, "Cola salida 1");
        comprobar(cola.desencolar() == 20, "Cola salida 2");
        comprobar(cola.desencolar() == 30, "Cola salida 3");
        comprobar(cola.esVacia(), "Cola vacia");
        cola.encolar(40);
        cola.vaciar();
        cola.encolar(50);
        comprobar(cola.desencolar() == 50, "Reutilizar cola");

        ColaPrioridad<Integer> prioridad = new ColaPrioridad<>();
        comprobar(prioridad.esVacia(), "Prioridad nueva");
        prioridad.encolar(20, 2);
        prioridad.encolar(10, 1);
        prioridad.encolar(30, 2);
        prioridad.encolar(40, 4);
        comprobar(prioridad.verFrente() == 10 && prioridad.obtenerCantidad() == 4, "Consultar prioridad");
        comprobar(prioridad.desencolar() == 10, "Mayor prioridad primero");
        comprobar(prioridad.desencolar() == 20, "Primer empate");
        comprobar(prioridad.desencolar() == 30, "Segundo empate");
        comprobar(prioridad.desencolar() == 40, "Menor prioridad ultimo");
        comprobar(prioridad.esVacia(), "Prioridad vacia");
        prioridad.encolar(50, 2);
        prioridad.vaciar();
        prioridad.encolar(60, 1);
        comprobar(prioridad.desencolar() == 60, "Reutilizar prioridad");
    }

    private Muestra[] crearMuestras() {
        Muestra[] muestras = new Muestra[8];
        muestras[0] = new Muestra("A", 80, 4);
        muestras[1] = new Muestra("B", 30, 2);
        muestras[2] = new Muestra("C", 100, 5);
        muestras[3] = new Muestra("D", 50, 1);
        muestras[4] = new Muestra("E", 70, 3);
        muestras[5] = new Muestra("F", 30, 2);
        muestras[6] = new Muestra("G", 90, 6);
        muestras[7] = new Muestra("H", 60, 4);
        return muestras;
    }

    // Copia el arreglo para que cada algoritmo reciba el mismo orden inicial.
    private Muestra[] copiar(Muestra[] original) {
        Muestra[] copia = new Muestra[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    private void ordenar(Ordenador ordenador, Muestra[] muestras, int algoritmo, int criterio) {
        if (algoritmo == 1) {
            ordenador.burbuja(muestras, criterio);
        } else if (algoritmo == 2) {
            ordenador.insercion(muestras, criterio);
        } else {
            ordenador.quicksort(muestras, criterio);
        }
    }

    private double dato(Muestra muestra, int criterio) {
        if (criterio == 1) {
            return muestra.obtenerValor();
        } else {
            return muestra.obtenerMasa();
        }
    }

    private void verificar(Muestra[] original, Muestra[] ordenado, int criterio) {
        for (int i = 0; i < ordenado.length - 1; i++) {
            comprobar(dato(ordenado[i], criterio) <= dato(ordenado[i + 1], criterio), "Orden ascendente");
        }
        // Verificamos que cada objeto siga apareciendo la misma cantidad de veces.
        for (int i = 0; i < original.length; i++) {
            int antes = 0;
            int despues = 0;
            for (int j = 0; j < original.length; j++) {
                if (original[i] == original[j]) {
                    antes++;
                }
                if (original[i] == ordenado[j]) {
                    despues++;
                }
            }
            comprobar(antes == despues, "Conservar todas las muestras");
        }
    }

    private void probarOrdenamientos() {
        for (int algoritmo = 1; algoritmo <= 3; algoritmo++) {
            Ordenador ordenador = new Ordenador();
            Muestra[] vacio = new Muestra[0];
            ordenar(ordenador, vacio, algoritmo, 1);
            comprobar(ordenador.obtenerComparaciones() == 0 && ordenador.obtenerIntercambios() == 0, "Arreglo vacio");
            Muestra[] una = new Muestra[1];
            una[0] = new Muestra("Unica", 10, 2);
            ordenar(ordenador, una, algoritmo, 2);
            comprobar(ordenador.obtenerComparaciones() == 0 && ordenador.obtenerIntercambios() == 0, "Una muestra");
            Muestra[] tres = new Muestra[3];
            tres[0] = new Muestra("A", 3, 3);
            tres[1] = new Muestra("B", 2, 2);
            tres[2] = new Muestra("C", 1, 1);
            ordenar(ordenador, tres, algoritmo, 1);
            comprobar(ordenador.obtenerComparaciones() == 3, "Conteo conocido");
            if (algoritmo == 3) {
                comprobar(ordenador.obtenerIntercambios() == 1, "Intercambios quicksort");
            } else {
                comprobar(ordenador.obtenerIntercambios() == 3, "Intercambios burbuja o insercion");
            }
            ordenar(ordenador, vacio, algoritmo, 1);
            comprobar(ordenador.obtenerComparaciones() == 0 && ordenador.obtenerIntercambios() == 0, "Reiniciar contadores");
        }
    }

    private void mostrarTabla() {
        System.out.println("Criterio,Escenario,N,Algoritmo,Comparaciones,Intercambios");
        for (int criterio = 1; criterio <= 2; criterio++) {
            String nombreCriterio = "Valor";
            if (criterio == 2) {
                nombreCriterio = "Masa";
            }
            for (int escenario = 1; escenario <= 4; escenario++) {
                Muestra[] entrada = crearMuestras();
                String nombreEscenario = "Original";
                if (escenario == 2 || escenario == 3) {
                    Ordenador preparador = new Ordenador();
                    preparador.insercion(entrada, criterio);
                    nombreEscenario = "Ordenado";
                }
                if (escenario == 3) {
                    Muestra[] invertido = new Muestra[entrada.length];
                    for (int i = 0; i < entrada.length; i++) {
                        invertido[i] = entrada[entrada.length - 1 - i];
                    }
                    entrada = invertido;
                    nombreEscenario = "Inverso";
                }
                if (escenario == 4) {
                    for (int i = 0; i < entrada.length; i++) {
                        entrada[i] = new Muestra("Igual" + i, 50, 2);
                    }
                    nombreEscenario = "Iguales";
                }
                for (int algoritmo = 1; algoritmo <= 3; algoritmo++) {
                    Muestra[] copia = copiar(entrada);
                    Ordenador ordenador = new Ordenador();
                    ordenar(ordenador, copia, algoritmo, criterio);
                    verificar(entrada, copia, criterio);
                    String nombreAlgoritmo = "Burbuja";
                    if (algoritmo == 2) {
                        nombreAlgoritmo = "Insercion";
                    } else if (algoritmo == 3) {
                        nombreAlgoritmo = "Quicksort";
                    }
                    System.out.println(nombreCriterio + "," + nombreEscenario + "," + copia.length
                            + "," + nombreAlgoritmo + "," + ordenador.obtenerComparaciones()
                            + "," + ordenador.obtenerIntercambios());
                }
            }
        }
        System.out.println();
        System.out.println("TOTAL CON TABLA: " + this.pruebasCorrectas + " verificaciones correctas.");
    }
}
