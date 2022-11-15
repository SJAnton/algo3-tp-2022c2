package algo3;

public class Bloque {
    private final int posX;
    private final int posY;

    private final int ALTO = 20;
    private final int ANCHO = 40;
    
    private final int puntuacion;
    private final int[] posicion;
    private final int[] color;

    private int vida;

    public Bloque(int posX, int posY, int vida, int puntuacion, int[] color) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.puntuacion = puntuacion;
        this.posicion = new int[] {this.posX, this.posY};

        this.vida = vida;
    }

    public void golpear() {
        if (this.vida == 0) {
            return;
        }
        this.vida--;
    }

    public int[] posicion() {
        return this.posicion;
    }

    public int[] altoAncho() {
        return new int[] {ALTO, ANCHO}; 
    }

    public int puntuacion() {
        return this.puntuacion;
    }

    public boolean estado() {
        return this.vida != 0;
    }
}