package algo3;

import java.io.IOException;
import java.util.Random;

public class Breakout {
    private final int RADIO_BOLA = 5;
    private final int DIST_BOLA_PALETA = 20;
    private final int DIST_PISO_PALETA = 40;

    private final int VIDA_INI = 3;
    private final int NIVEL_INI = 1;
    private final int VEL_PALETA = 20;
    private final int VEL_BOLA_INI = 3;

    private int vida;
    private int nivel;
    private int puntuacion;
    private int posIniX;
    private int posIniY;

    private final int altoPantalla;
    private final int anchoPantalla;
    
    private Bola bola;
    private Paleta paleta;
    private Colision colision;
    private FabricaDeBloques fabricaDeBloques;
    
    public Breakout(int altoPantalla, int anchoPantalla) throws IOException {
        this.puntuacion = 0;
        this.vida = VIDA_INI;
        this.nivel = NIVEL_INI;

        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;

        this.posIniX = this.anchoPantalla / 2;
        this.posIniY = this.altoPantalla - DIST_PISO_PALETA;

        this.bola = new Bola(this.posIniX, this.posIniY - DIST_BOLA_PALETA, RADIO_BOLA, VEL_BOLA_INI);
        this.paleta = new Paleta(this.posIniX, this.posIniY, VEL_PALETA);
        this.colision = new Colision(this.bola);
        this.fabricaDeBloques = new FabricaDeBloques(this.nivel);

        this.inicializar();
    }

    public void inicializar() throws IOException {
        this.fabricaDeBloques.generarNivel();
    }

    public void actualizar() throws IOException {
        if (juegoActivo()) {
            this.bola.actualizarMovimiento();

            this.colision.colisionBolaPaleta(this.paleta);
            this.colision.colisionBolaBloque(this, this.fabricaDeBloques);
            this.colision.colisionBolaPared(this, this.altoPantalla, this.anchoPantalla);
        } else if (this.fabricaDeBloques.cantidadBloques() == 0) {
            // Siguiente nivel
            this.nivel++; // TODO: cargar sig nivel solo si existe
            this.fabricaDeBloques = new FabricaDeBloques(this.nivel);
            this.inicializar();
        }
    }

    private boolean juegoActivo() {
        return this.vida > 0 && this.fabricaDeBloques.cantidadBloques() > 0;
    }

    public void subirPuntuacion(int puntos) {
        this.puntuacion += puntos;
    }

    public void reducirVida() {
        if (this.vida == 0) {
            return;
        }
        this.vida--;
    }

    public void iniciarMovimientoBola() {
        double random = (new Random().nextDouble() * 2) - 1; // - 1 <= random <= 1
        this.bola.modificarDireccion(random, -1.0);
    }

    public void reiniciarPosBola() {
        this.bola.modificarDireccion(0.0, 0.0);
        this.bola.modificarVelocidad(VEL_BOLA_INI);
        this.bola.modificarPosicion(this.posIniX, this.posIniY - DIST_BOLA_PALETA);
    }

    public void reinciar() {
        this.puntuacion = 0;
        this.vida = VIDA_INI;
        this.nivel = NIVEL_INI;
        
        this.reiniciarPosBola();
        try {
            this.fabricaDeBloques = new FabricaDeBloques(nivel);
            this.inicializar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int alto() {
        return this.altoPantalla;
    }

    public int ancho() {
        return this.anchoPantalla;
    }

    public int puntuacion() {
        return this.puntuacion;
    }

    public int vida() {
        return this.vida;
    }

    public Bola bola() {
        return this.bola;
    }

    public Paleta paleta() {
        return this.paleta;
    }

    public FabricaDeBloques fabrica() {
        return this.fabricaDeBloques;
    }

    public int distPisoPaleta() {
        return DIST_PISO_PALETA;
    }
}