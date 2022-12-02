package algo3;

import java.io.IOException;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Breakout {
    private final int RADIO_BOLA = 5;
    private final int DIST_BOLA_PALETA = 20;
    private final int POS_INI_Y = 20;

    private final int DIST_TECHO_TABLERO = 20;

    private int vida;
    private int nivel;
    private int puntuacion;
    private int posIniX;

    private final int altoPantalla;
    private final int anchoPantalla;
    private final int paredSup;
    
    private Bola bola;
    private Paleta paleta;
    private Colision colision;
    private FabricaDeBloques fabricaDeBloques;
    
    public Breakout(int altoPantalla, int anchoPantalla) throws IOException {
        this.vida = 3;
        this.nivel = 1;
        this.puntuacion = 0;

        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.paredSup = anchoPantalla - DIST_TECHO_TABLERO;

        this.posIniX = anchoPantalla / 2;

        this.bola = new Bola(this.posIniX, POS_INI_Y + DIST_BOLA_PALETA, RADIO_BOLA);
        this.paleta = new Paleta(this.posIniX, POS_INI_Y);
        this.colision = new Colision(bola);
        this.fabricaDeBloques = new FabricaDeBloques(this.nivel);

        inicializar();
    }

    public void inicializar() throws IOException {
        this.fabricaDeBloques.generarNivel();
        iniciarMovimientoBola();
        actualizar();
    }

    public void actualizar() throws IOException {
        while (juegoActivo()) {
            this.bola.actualizarMovimiento();

            this.colision.colisionBolaBloque(this, this.fabricaDeBloques);
            this.colision.colisionBolaPaleta(this.paleta);
            this.colision.colisionBolaPared(this, this.altoPantalla, this.paredSup);
        }
        if (this.fabricaDeBloques.cantidadBloques() == 0) {
            // Siguiente nivel
            this.nivel++;
            this.fabricaDeBloques = new FabricaDeBloques(this.nivel);
            inicializar();
        }
    }

    private boolean juegoActivo() {
        return this.vida > 0 && this.fabricaDeBloques.cantidadBloques() > 0;
    }

    public void subirPuntuacion(int puntos) {
        this.puntuacion += puntos;
    }

    public int alto() {
        return this.altoPantalla;
    }

    public int ancho() {
        return this.anchoPantalla;
    }

    public void reducirVida() {
        if (this.vida == 0) {
            return;
        }
        this.vida--;
    }

    public void iniciarMovimientoBola() {
        //TODO: implementar
        
        Bola bola = this.bola;

        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            double random = (new Random().nextDouble() * 2) - 1; // - 1 <= random <= 1

            @Override
            public void handle(KeyEvent event) {
                //Inicia el movimiento de la bola al apretar cualquier tecla
                bola.modificarDireccion(random, 1.0);
            }
        };
    }

    public void reiniciar() {
        this.bola.modificarPosicion(this.posIniX, POS_INI_Y + DIST_BOLA_PALETA);
        iniciarMovimientoBola();
    }
}