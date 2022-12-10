package algo3;

public class BloqueComun implements Bloque {
    private final int posX;
    private final int posY;

    private final int ALTO = 20;
    private final int ANCHO = 40;
    
    private final int puntuacion;
    private final Color color;

    private int vida;

    public BloqueComun(int posX, int posY, int vida, int puntuacion, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.puntuacion = puntuacion;
        this.vida = vida;
    }

    public void golpear() {
        if (this.vida <= 0) {
            return;
        }
        this.vida--;
    }

    public int posX() {
        return this.posX;
    }

    public int posY() {
        return this.posY;
    }

    public int alto() {
        return ALTO;
    }

    public int ancho() {
        return ANCHO;
    }

    public int puntuacion() {
        return this.puntuacion;
    }

    public Color color() {
        return this.color;
    }

    public boolean estado() {
        return this.vida != 0;
    }

    @Override
    public boolean esInvisible() {
        return false;
    }

    @Override
    public Bloque reemplazarBloque() {
        return null;
    }
}