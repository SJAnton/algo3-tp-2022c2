package algo3;

public class BloqueInvisible implements Bloque {
    private final int posX;
    private final int posY;

    private final int ALTO = 20;
    private final int ANCHO = 40;

    private final int puntuacion;
    private final int[] color;

    private int vida;
    private boolean invisible;
    
    public BloqueInvisible(int posX, int posY, int vida, int puntuacion, int[] color) {        
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.puntuacion = puntuacion;

        this.vida = vida;
        this.invisible = true;
    }

    public void golpear() {
        // Golpear a un bloque invisible lo hace visible
        this.invisible = false;
    }

    public BloqueComun reemplazarBloque() {
        /* Devuelve un bloque común con las mismas características
        para sustituir en la lista de bloques */
        if (this.esInvisible()) {
            return null;
        }
        return new BloqueComun(this.posX, this.posY, this.vida, this.puntuacion, this.color);
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

    public boolean esInvisible() {
        return this.invisible;
    }

    @Override
    public boolean estado() {
        return false;
    }
}