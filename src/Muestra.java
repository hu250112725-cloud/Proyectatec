
public class Muestra {
    private String nombre;
    private double valor;
    private double masa;

    public Muestra(String nombre, double valor, double masa) {
        this.nombre = nombre;
        this.valor = valor;
        this.masa = masa;
    }

    public String obtenerNombre() {

        return this.nombre;
    }

    public double obtenerValor() {
        return this.valor;
    }

    public double obtenerMasa() {

        return this.masa;
    }
}
