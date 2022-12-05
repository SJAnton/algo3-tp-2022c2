package algo3;

public class Paleta {
    private int posX;
    private int posY;
    private int velocidad;
    
    private final int ALTO = 10;
    private final int ANCHO = 100;

    public Paleta(int posX, int posY, int velocidad) {
        this.posX = posX;
        this.posY = posY;
        this.velocidad = velocidad;
    }

    public void modificarPosicion(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void movIzq() {
        int paredIzq = 0;
        
        if (bordeIzq() > paredIzq) {
            this.posX -= this.velocidad;
        }
    }

    public void movDer(int paredDer) {
        if (bordeDer() < paredDer) {
            this.posX += this.velocidad;
        }
    }

    public int posX() {
        return this.posX;
    }

    public int posY() {
        return this.posY;
    }

    public int bordeIzq() {
        return posX - (ANCHO / 2);
    }
    
    public int bordeDer() {
        return posX + (ANCHO / 2);
    }

    public int bordeSup() {
        return posY - (ALTO / 2);
    }

    public int alto() {
        return ALTO;
    }

    public int ancho() {
        return ANCHO;
    }
}
