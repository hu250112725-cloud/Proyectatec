
public class Ordenador {
    private int comparaciones;
    private int intercambios;

    public int obtenerComparaciones() {
        return this.comparaciones;
    }

    public int obtenerIntercambios() {
        return this.intercambios;
    }

    private void reiniciarContadores() {
        this.comparaciones = 0;
        this.intercambios = 0;
    }

    // criterio 1 = valor; criterio 2 = masa.
    private double obtenerDato(Muestra muestra, int criterio) {
        if (criterio == 1) {
            return muestra.obtenerValor();
        } else {
            return muestra.obtenerMasa();
        }
    }

    private void validar(Muestra[] muestras, int criterio) {
        if (muestras == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        if (criterio != 1 && criterio != 2) {
            throw new IllegalArgumentException("Usa 1 para valor o 2 para masa");
        }
        for (int i = 0; i < muestras.length; i++) {
            if (muestras[i] == null) {
                throw new IllegalArgumentException("El arreglo tiene una posicion vacia");
            }

        }
    }

    private void intercambiar(Muestra[] muestras, int a, int b) {
        if (a != b) {
            Muestra temporal = muestras[a];
            muestras[a] = muestras[b];
            muestras[b] = temporal;
            this.intercambios++;
        }
    }

    // BURBUJA: compara vecinos. El mayor va avanzando hacia el final.
    public void burbuja(Muestra[] muestras, int criterio) {
        reiniciarContadores();
        validar(muestras, criterio);
        for (int i = 0; i < muestras.length - 1; i++) {
            for (int j = 0; j < muestras.length - 1 - i; j++) {
                this.comparaciones++;
                if (obtenerDato(muestras[j], criterio) > obtenerDato(muestras[j + 1], criterio)) {
                    intercambiar(muestras, j, j + 1);
                }
            }
        }
    }

    // INSERCION por intercambios: acomoda cada elemento en la parte izquierda.
    public void insercion(Muestra[] muestras, int criterio) {
        reiniciarContadores();
        validar(muestras, criterio);
        for (int i = 1; i < muestras.length; i++) {
            int j = i;
            boolean seguir = true;
            while (j > 0 && seguir) {
                this.comparaciones++;
                if (obtenerDato(muestras[j - 1], criterio) > obtenerDato(muestras[j], criterio)) {
                    intercambiar(muestras, j - 1, j);
                    j--;
                } else {
                    seguir = false;
                }
            }
        }
    }

    // Este metodo inicia quicksort y reinicia los contadores una sola vez.
    public void quicksort(Muestra[] muestras, int criterio) {
        reiniciarContadores();
        validar(muestras, criterio);
        ordenarQuick(muestras, 0, muestras.length - 1, criterio);
    }

    // QUICKSORT: coloca el pivote y ordena por separado los dos lados.
    private void ordenarQuick(Muestra[] muestras, int inicio, int fin, int criterio) {
        if (inicio < fin) {
            int posicionPivote = dividir(muestras, inicio, fin, criterio);
            ordenarQuick(muestras, inicio, posicionPivote - 1, criterio);
            ordenarQuick(muestras, posicionPivote + 1, fin, criterio);
        }
    }

    private int dividir(Muestra[] muestras, int inicio, int fin, int criterio) {
        // Elegimos el ultimo elemento como pivote.
        double pivote = obtenerDato(muestras[fin], criterio);
        int posicion = inicio;
        for (int i = inicio; i < fin; i++) {
            this.comparaciones++;
            if (obtenerDato(muestras[i], criterio) <= pivote) {
                intercambiar(muestras, i, posicion);
                posicion++;
            }
        }
        intercambiar(muestras, posicion, fin);
        return posicion;
    }
}
