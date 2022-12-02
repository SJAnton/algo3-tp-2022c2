package algo3;

public class Paleta {
    private int posX;
    private int posY;
    
    private final int ALTO = 10;
    private final int ANCHO = 60;

    public Paleta(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int[] altoAncho() {
        return new int[] {ALTO, ANCHO};
    }

    public void movIzq() {
        int paredIzq = 0;
        
        if (bordeIzq() >= paredIzq) {
            this.posX--;
        }
    }

    public void movDer(int paredDer) {
        if (bordeDer() <= paredDer) {
            this.posX++;
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

    public int[] posicion()  {
        return new int[] {this.posX, this.posY};
    }

    public void modificarPosicion(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
