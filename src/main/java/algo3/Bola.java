package algo3;

public class Bola {
    private double posX;
    private double posY;
    private int radio;
    private double velocidad;
    private double[] direccion;    

    public Bola(double posX, double posY, int radio, double velocidad) {
        this.posX = posX;
        this.posY = posY;
        this.radio = radio;
        this.velocidad = velocidad;
        this.direccion = new double[] {0.0, 0.0};
    }

    public void modificarDireccion(double x, double y) {
        this.direccion[0] = x;
        this.direccion[1] = y; 
    }

    public void modificarVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public void modificarPosicion(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void actualizarMovimiento() {
        this.posX += this.direccion[0] * this.velocidad;
        this.posY += this.direccion[1] * this.velocidad;
    }

    public int radio() {
        return this.radio;
    }

    public double[] verDireccion() {
        return this.direccion;
    }

    public double posX() {
        return this.posX;
    }

    public double posY() {
        return this.posY;
    }

    public double posIzq() {
        return this.posX - this.radio;
    }

    public double posDer() {
        return this.posX + this.radio;
    }

    public double posSup() {
        // Eje Y positivo hacia abajo
        return this.posY - this.radio;
    }

    public double posInf() {
        return this.posY + this.radio;
    }

    public double velocidad() {
        return this.velocidad;
    }
}