package algoritmos;

import estructuras.Cola;
import estructuras.ColaPrioridad;
import estructuras.ListaEnlazada;
import estructuras.Pila;

//Busca recorridos dentro de la cuadrícula del Modelo
public final class BuscadorCamino {

    private static final int COSTO_POR_MOVIMIENTO = 5;

    // El rover solo puede desplazarse una celda cardinal por turno.
    private static final int[][] DIRECCIONES = {
        {0, -1}, // Arriba
        {1, 0},  // Derecha
        {0, 1},  // Abajo
        {-1, 0}  // Izquierda
    };

    private BuscadorCamino() {
    }

    /** Una coordenada del mapa actual, expresada como (x, y). */
    public static final class Posicion {
        private final int x;
        private final int y;

        public Posicion(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int obtenerX() {
            return this.x;
        }

        public int obtenerY() {
            return this.y;
        }

        @Override
        public boolean equals(Object objeto) {
            if (this == objeto) {
                return true;
            }
            if (!(objeto instanceof Posicion)) {
                return false;
            }
            Posicion otra = (Posicion) objeto;
            return this.x == otra.x && this.y == otra.y;
        }

        @Override
        public int hashCode() {
            return 31 * this.x + this.y;
        }

        @Override
        public String toString() {
            return "[" + this.x + "," + this.y + "]";
        }
    }

    /** Resultado inmutable: camino ordenado desde el inicio hasta el destino. */
    public static final class Ruta {
        private final ListaEnlazada<Posicion> posiciones;
        private final int energia;

        private Ruta(ListaEnlazada<Posicion> posiciones, int energia) {
            this.posiciones = posiciones;
            this.energia = energia;
        }

        public int obtenerCantidadPasos() {
            return Math.max(0, this.posiciones.obtenerCantidad() - 1);
        }

        public int obtenerEnergiaTotal() {
            return this.energia;
        }

        public Posicion obtenerPosicion(int indice) {
            return this.posiciones.obtenerEn(indice);
        }

        /** Devuelve una copia para que el resultado no pueda modificarse desde fuera. */
        public ListaEnlazada<Posicion> obtenerPosiciones() {
            ListaEnlazada<Posicion> copia = new ListaEnlazada<>();
            for (int i = 0; i < this.posiciones.obtenerCantidad(); i++) {
                copia.agregarAlFinal(this.posiciones.obtenerEn(i));
            }
            return copia;
        }

        @Override
        public String toString() {
            return this.posiciones.toString() + " (pasos: " + obtenerCantidadPasos()
                    + ", energia: " + this.energia + ")";
        }
    }

    /** Dato que se coloca en la cola de prioridad de Dijkstra. */
    private static final class Candidato {
        private final Posicion posicion;
        private final int energia;

        private Candidato(Posicion posicion, int energia) {
            this.posicion = posicion;
            this.energia = energia;
        }
    }

    /**
     * Encuentra la ruta con menos movimientos desde la posicion actual del rover.
     * El destino debe estar dentro de los limites del mapa.
     */
    public static Ruta buscarPorAnchura(Modelo modelo, int destinoX, int destinoY) {
        validarModelo(modelo);
        return buscarPorAnchura(
                modelo, modelo.getRoverX(), modelo.getRoverY(), destinoX, destinoY);
    }

    /** Busca con BFS entre dos coordenadas del mapa. */
    public static Ruta buscarPorAnchura(
            Modelo modelo, int inicioX, int inicioY, int destinoX, int destinoY) {
        validarCoordenadas(modelo, inicioX, inicioY, destinoX, destinoY);

        Cola<Posicion> pendientes = new Cola<>();
        boolean[][] visitada = new boolean[modelo.getTamanoMapa()][modelo.getTamanoMapa()];
        Posicion[][] padre = new Posicion[modelo.getTamanoMapa()][modelo.getTamanoMapa()];
        Posicion inicio = new Posicion(inicioX, inicioY);
        Posicion destino = new Posicion(destinoX, destinoY);

        visitada[inicioY][inicioX] = true;
        pendientes.encolar(inicio);
        while (!pendientes.esVacia()) {
            Posicion actual = pendientes.desencolar();
            if (actual.equals(destino)) {
                return reconstruir(destino, inicio, padre);
            }

            for (int[] direccion : DIRECCIONES) {
                int vecinoX = actual.x + direccion[0];
                int vecinoY = actual.y + direccion[1];
                if (!esValida(modelo, vecinoX, vecinoY) || visitada[vecinoY][vecinoX]) {
                    continue;
                }

                // BFS marca cada casilla al encolarla para no explorarla dos veces.
                visitada[vecinoY][vecinoX] = true;
                Posicion vecino = new Posicion(vecinoX, vecinoY);
                padre[vecinoY][vecinoX] = actual;
                pendientes.encolar(vecino);
            }
        }
        return new Ruta(new ListaEnlazada<Posicion>(), 0);
    }

    /** Alias breve para quienes conocen el algoritmo como BFS. */
    public static Ruta bfs(Modelo modelo, int destinoX, int destinoY) {
        return buscarPorAnchura(modelo, destinoX, destinoY);
    }

    /**
     * Encuentra la ruta de menor consumo desde la posicion actual.
     * En el Modelo actual todos los movimientos cuestan 5, por lo que su ruta
     * tambien tiene el menor numero de pasos.
     */
    public static Ruta buscarPorMenorConsumo(Modelo modelo, int destinoX, int destinoY) {
        validarModelo(modelo);
        return buscarPorMenorConsumo(
                modelo, modelo.getRoverX(), modelo.getRoverY(), destinoX, destinoY);
    }

    /** Busca con Dijkstra entre dos coordenadas del mapa. */
    public static Ruta buscarPorMenorConsumo(
            Modelo modelo, int inicioX, int inicioY, int destinoX, int destinoY) {
        validarCoordenadas(modelo, inicioX, inicioY, destinoX, destinoY);

        int tamaño = modelo.getTamanoMapa();
        int[][] mejorEnergia = new int[tamaño][tamaño];
        Posicion[][] padre = new Posicion[tamaño][tamaño];
        boolean[][] visitada = new boolean[tamaño][tamaño];
        for (int y = 0; y < tamaño; y++) {
            for (int x = 0; x < tamaño; x++) {
                mejorEnergia[y][x] = Integer.MAX_VALUE;
            }
        }

        ColaPrioridad<Candidato> pendientes = new ColaPrioridad<>();
        Posicion inicio = new Posicion(inicioX, inicioY);
        Posicion destino = new Posicion(destinoX, destinoY);
        mejorEnergia[inicioY][inicioX] = 0;
        pendientes.encolar(new Candidato(inicio, 0), 0);

        while (!pendientes.esVacia()) {
            Candidato actual = pendientes.desencolar();
            int x = actual.posicion.x;
            int y = actual.posicion.y;

            // Una coordenada puede encolarse mas de una vez si luego mejora su costo.
            if (visitada[y][x] || actual.energia != mejorEnergia[y][x]) {
                continue;
            }
            if (actual.posicion.equals(destino)) {
                return reconstruir(destino, inicio, padre);
            }
            visitada[y][x] = true;

            for (int[] direccion : DIRECCIONES) {
                int vecinoX = x + direccion[0];
                int vecinoY = y + direccion[1];
                if (!esValida(modelo, vecinoX, vecinoY) || visitada[vecinoY][vecinoX]) {
                    continue;
                }

                int nuevoConsumo = actual.energia + COSTO_POR_MOVIMIENTO;
                if (nuevoConsumo < mejorEnergia[vecinoY][vecinoX]) {
                    mejorEnergia[vecinoY][vecinoX] = nuevoConsumo;
                    Posicion vecino = new Posicion(vecinoX, vecinoY);
                    padre[vecinoY][vecinoX] = actual.posicion;
                    pendientes.encolar(new Candidato(vecino, nuevoConsumo), nuevoConsumo);
                }
            }
        }
        return new Ruta(new ListaEnlazada<Posicion>(), 0);
    }

    // Alias breve para quienes conocen el algoritmo como Dijkstra.
    public static Ruta dijkstra(Modelo modelo, int destinoX, int destinoY) {
        return buscarPorMenorConsumo(modelo, destinoX, destinoY);
    }

    /** Calcula la ruta de menor consumo desde el rover hasta la base [0,0]. */
    public static Ruta calcularRegresoABase(Modelo modelo) {
        validarModelo(modelo);
        return buscarPorMenorConsumo(modelo, modelo.getRoverX(), modelo.getRoverY(), 0, 0);
    }

    private static Ruta reconstruir(Posicion destino, Posicion inicio, Posicion[][] padre) {
        Pila<Posicion> inversa = new Pila<>();
        Posicion actual = destino;
        inversa.apilar(actual);

        // Retrocede por los padres hasta el inicio y luego invierte el recorrido con la pila.
        while (!actual.equals(inicio)) {
            actual = padre[actual.y][actual.x];
            if (actual == null) {
                throw new IllegalStateException("No se pudo reconstruir la ruta encontrada");
            }
            inversa.apilar(actual);
        }

        ListaEnlazada<Posicion> posiciones = new ListaEnlazada<>();
        while (!inversa.esVacia()) {
            posiciones.agregarAlFinal(inversa.desapilar());
        }
        return new Ruta(posiciones, (posiciones.obtenerCantidad() - 1) * COSTO_POR_MOVIMIENTO);
    }

    private static void validarModelo(Modelo modelo) {
        if (modelo == null) {
            throw new IllegalArgumentException("El modelo no puede ser null");
        }
    }

    private static void validarCoordenadas(
            Modelo modelo, int inicioX, int inicioY, int destinoX, int destinoY) {
        validarModelo(modelo);
        if (!esValida(modelo, inicioX, inicioY) || !esValida(modelo, destinoX, destinoY)) {
            throw new IllegalArgumentException("Las coordenadas deben estar dentro del mapa");
        }
    }

    private static boolean esValida(Modelo modelo, int x, int y) {
        return x >= 0 && y >= 0 && x < modelo.getTamanoMapa() && y < modelo.getTamanoMapa();
    }
}
