import estructuras.ListaEnlazada;
import java.util.Random;

public class Modelo {
    private final int TAMANO_MAPA = 8;
    private final int BATERIA_MAXIMA = 150;
    private final int TOTAL_MUESTRAS = 6;
    
    private Muestra[][] mapa;
    private int roverX, roverY;
    private int bateria;
    private ListaEnlazada<Muestra> mochila;
    private int puntaje;
    private int muestrasEntregadas;
    private boolean juegoActivo;
    private String mensajeActual;

    public Modelo() {
        this.mapa = new Muestra[TAMANO_MAPA][TAMANO_MAPA];
        this.mochila = new ListaEnlazada<>();
        iniciarJuego();
    }

    public void iniciarJuego() {
        this.roverX = 0; // Base en 0,0
        this.roverY = 0;
        this.bateria = BATERIA_MAXIMA;
        this.puntaje = 0;
        this.muestrasEntregadas = 0;
        this.mochila.vaciar();
        this.juegoActivo = true;
        this.mensajeActual = "Mision iniciada. Base ubicada en [0,0].";
        
        limpiarMapa();
        generarMuestras();
    }

    private void limpiarMapa() {
        for (int y = 0; y < TAMANO_MAPA; y++) {
            for (int x = 0; x < TAMANO_MAPA; x++) {
                mapa[y][x] = null;
            }
        }
    }

    private void generarMuestras() {
        Random rand = new Random();
        int colocadas = 0;
        while (colocadas < TOTAL_MUESTRAS) {
            int x = rand.nextInt(TAMANO_MAPA);
            int y = rand.nextInt(TAMANO_MAPA);
            // Evitar colocar en la base (0,0) y donde ya haya una muestra
            if ((x != 0 || y != 0) && mapa[y][x] == null) {
                String nombre = "M" + (colocadas + 1);
                double valor = (colocadas + 1) * 15;
                double masa = Math.round((rand.nextDouble() * 4 + 1) * 10.0) / 10.0;
                mapa[y][x] = new Muestra(nombre, valor, masa);
                colocadas++;
            }
        }
    }

    // --- ACCIONES DEL ROVER ---

    public void mover(int dx, int dy) {
        if (!juegoActivo) return;

        int nuevoX = roverX + dx;
        int nuevoY = roverY + dy;

        if (nuevoX >= 0 && nuevoX < TAMANO_MAPA && nuevoY >= 0 && nuevoY < TAMANO_MAPA) {
            roverX = nuevoX;
            roverY = nuevoY;
            bateria -= 5;
            mensajeActual = "Rover desplazado a la coordenada [" + roverX + "," + roverY + "].";
            verificarEstadoJuego();
        } else {
            mensajeActual = "Error: Movimiento bloqueado. Limite de la cueva alcanzado.";
        }
    }

    public void recolectar() {
        if (!juegoActivo) return;
        
        Muestra muestraEnSuelo = mapa[roverY][roverX];
        if (muestraEnSuelo != null) {
            mochila.agregarAlFinal(muestraEnSuelo);
            mapa[roverY][roverX] = null;
            mensajeActual = "Muestra " + muestraEnSuelo.obtenerNombre() + " recolectada exitosamente.";
        } else {
            mensajeActual = "Error: No hay ninguna muestra en esta ubicacion.";
        }
    }

    public void entregarMuestras() {
        if (!juegoActivo) return;

        if (roverX == 0 && roverY == 0) {
            if (mochila.esVacia()) {
                mensajeActual = "La mochila esta vacia. No hay nada que entregar.";
            } else {
                int entregadasAhora = mochila.obtenerCantidad();
                while (!mochila.esVacia()) {
                    Muestra m = mochila.eliminarEn(0);
                    puntaje += m.obtenerValor();
                    muestrasEntregadas++;
                }
                mensajeActual = entregadasAhora + " muestras entregadas en la Base. Puntaje acumulado: " + puntaje;
                verificarEstadoJuego();
            }
        } else {
            mensajeActual = "Error: Debes estar en la Base [0,0] para entregar las muestras.";
        }
    }

    public void recargarBateria() {
        if (!juegoActivo) return;

        if (roverX == 0 && roverY == 0) {
            bateria = BATERIA_MAXIMA;
            mensajeActual = "Bateria recargada al maximo (" + BATERIA_MAXIMA + ").";
        } else {
            mensajeActual = "Error: El punto de recarga solo se encuentra en la Base [0,0].";
        }
    }

    private void verificarEstadoJuego() {
        if (muestrasEntregadas == TOTAL_MUESTRAS) {
            juegoActivo = false;
            mensajeActual = "¡VICTORIA! Has extraido exitosamente las " + TOTAL_MUESTRAS + 
                            " muestras.\n[=== PUNTAJE FINAL: " + puntaje + " ===]";
        } else if (bateria <= 0) {
            juegoActivo = false;
            mensajeActual = "¡DERROTA! El Rover se ha quedado sin bateria en medio de la cueva.";
        }
    }

    // --- GETTERS ---
    public int getRoverX() { return roverX; }
    public int getRoverY() { return roverY; }
    public int getBateria() { return bateria; }
    public int getPuntaje() { return puntaje; }
    public int getTamanoMapa() { return TAMANO_MAPA; }
    public boolean isJuegoActivo() { return juegoActivo; }
    public String getMensajeActual() { return mensajeActual; }
    public Muestra getMuestraEn(int x, int y) { return mapa[y][x]; }
    public int getCantidadEnMochila() { return mochila.obtenerCantidad(); }
}
