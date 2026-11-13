package Visual;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Panel extends JPanel {
    // --- DATOS DE PRUEBA (Reemplazarán los valores del Modelo más adelante) ---
    private int turno = 1;
    private int puntaje = 250;
    private double bateria = 100.0;     // Nivel actual
    private double bateriaMaxima = 100.0;
    private String estado = "EN CURSO";
    private int roverX = 4;
    private int roverY = 2;

    public Panel() {
        setPreferredSize(new Dimension(300, 600));
        setBackground(new Color(14, 20, 30)); // Fondo oscuro
    }

    // --- MÉTODOS PARA ACTUALIZAR VALORES EXTERNAMENTE ---
    public void setBateria(double actual, double max) {
        this.bateria = actual;
        this.bateriaMaxima = max;
        repaint();
    }

    public void setTurno(int turno) {
        this.turno = turno;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);

        Graphics2D g = (Graphics2D) g0;
        // Suavizado de bordes para textos y figuras limpias
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int margenX = 20;
        int y = 35;

        // 1. TÍTULO
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("MISIÓN ROVER", margenX, y);

        // 2. ESTADO GENERAL
        y += 35;
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.setColor(new Color(170, 190, 215));

        g.drawString("Estado: " + estado, margenX, y);
        g.drawString("Turno: " + turno, margenX, y += 22);
        g.drawString("Puntaje: " + puntaje + " pts", margenX, y += 22);
        g.drawString("Rover en: [" + roverX + ", " + roverY + "]", margenX, y += 22);

        // 3. BARRA DE ENERGÍA
        y += 40;
        double pct = Math.max(0.0, Math.min(1.0, bateria / bateriaMaxima));
        int anchoBarra = 240;
        int altoBarra = 18;

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        g.drawString("BATERÍA (" + (int) (pct * 100) + "%)", margenX, y);

        // Fondo de la barra
        y += 8;
        g.setColor(new Color(35, 45, 60));
        g.fillRoundRect(margenX, y, anchoBarra, altoBarra, 6, 6);

        // Relleno dinámico (Verde si > 30%, Rojo si está baja)
        g.setColor(pct > 0.3 ? new Color(60, 210, 120) : new Color(240, 70, 70));
        g.fillRoundRect(margenX, y, (int) (anchoBarra * pct), altoBarra, 6, 6);

        // Contorno
        g.setColor(new Color(70, 90, 120));
        g.drawRoundRect(margenX, y, anchoBarra, altoBarra, 6, 6);

        // 4. ESPACIO PARA MINIMAPA (Placeholder / Provisorio)
        y += 45;
        int tamanoMapa = 240;

        g.setColor(Color.WHITE);
        g.drawString("MINIMAPA", margenX, y);

        y += 10;
        g.setColor(new Color(8, 12, 18));
        g.fillRect(margenX, y, tamanoMapa, tamanoMapa);
        g.setColor(new Color(50, 70, 95));
        g.drawRect(margenX, y, tamanoMapa, tamanoMapa);

        // Rover de prueba en el minimapa (un punto celeste centrado)
        g.setColor(new Color(90, 200, 255));
        g.fillOval(margenX + (tamanoMapa / 2) - 5, y + (tamanoMapa / 2) - 5, 10, 10);

        // 5. PIE DE AYUDA / CONTROLES
        g.setColor(new Color(110, 130, 150));
        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g.drawString("W/A/S/D para mover · R para reiniciar", margenX, getHeight() - 20);
    }

    
}
