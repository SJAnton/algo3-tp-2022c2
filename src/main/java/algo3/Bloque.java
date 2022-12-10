package algo3;

public interface Bloque {    
    public void golpear();

    public int posX();

    public int posY();

    public int alto();

    public int ancho();

    public int puntuacion();

    public Color color();

    public boolean estado();

    public boolean esInvisible();

    public Bloque reemplazarBloque();
}