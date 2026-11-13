public class Main {
    public static void main(String[] args) {
        // Inicializamos la lógica (El Juego/Mapa)
        Modelo modelo = new Modelo();
        
        // Inicializamos la vista por consola, pasándole el modelo
        Vista vista = new VistaConsola(modelo);
        
        // Ejecutamos el flujo de la partida
        vista.iniciar();
    }
}

