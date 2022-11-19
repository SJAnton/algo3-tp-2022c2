package algo3;

public class BloqueInvisible implements InterfazBloque {
    private final int posX;
    private final int posY;

    private final int ALTO = 20;
    private final int ANCHO = 40;

    private final int puntuacion;
    private final int[] posicion;
    private final int[] color;

    private int vida;
    private boolean invisible;
    
    public BloqueInvisible(int posX, int posY, int vida, int puntuacion, int[] color) {        
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.puntuacion = puntuacion;
        this.posicion = new int[] {this.posX, this.posY};

        this.vida = vida;
        this.invisible = true;
    }

    public void golpear() {
        // Golpear a un bloque invisible lo hace visible
        this.invisible = false;
    }

    public int[] posicion() {
        return this.posicion;
    }

    public int[] altoAncho() {
        return new int[] {ALTO, ANCHO}; 
    }

    public boolean esInvisible() {
        return this.invisible;
    }

    public BloqueComun reemplazarBloque() {
        /* Devuelve un bloque común con las mismas características
        para sustituir en la lista de bloques */
        if (this.esInvisible()) {
            return null;
        }
        return new BloqueComun(this.posX, this.posY, this.vida, this.puntuacion, this.color);
    }

    public int puntuacion() {
        return this.puntuacion;
    }

    @Override
    public boolean estado() {
        return false;
    }
}