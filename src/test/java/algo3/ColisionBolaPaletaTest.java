package algo3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColisionBolaPaletaTest {
    private final int POS_X = 50;
    private final int POS_Y = 50;
    private final int RADIO = 5;
    private final int DIST_BOLA_PALETA = 30;

    @Test
    public void colisionMitad() {
        // Prueba colisionar en la parte media de la paleta
        Bola bola = new Bola(POS_X, POS_Y, RADIO, 1);
        Colision colision = new Colision(bola);
        Paleta paleta = new Paleta(POS_X, POS_Y - DIST_BOLA_PALETA, 0);

        double dirX = 0.0;
        double dirY = -1.0;

        bola.modificarDireccion(dirX, dirY);

        while (bola.verDireccion()[1] < 0) {
            // La bola baja hasta colisionar con la paleta
            bola.actualizarMovimiento();
            colision.colisionBolaPaleta(null, paleta);
        }
        assertEquals(dirX, bola.verDireccion()[0], 0.1);
        assertEquals(-dirY, bola.verDireccion()[1], 0.1);
    }

    @Test
    public void colisionIzqDer() {
        // Prueba colisionar en la parte izquierda de la paleta
        int distBolaPaletaX = 25;

        Bola bola = new Bola(POS_X - distBolaPaletaX, POS_Y, RADIO, 1);
        Colision colision = new Colision(bola);
        Paleta paleta = new Paleta(POS_X, POS_Y - DIST_BOLA_PALETA, 0);

        double dirX = 0.0;
        double dirY = -1.0;
        
        bola.modificarDireccion(dirX, dirY);

        while (bola.verDireccion()[1] < 0) {
            // La bola baja hasta colisionar con la paleta
            bola.actualizarMovimiento();
            colision.colisionBolaPaleta(null, paleta);
        }
        double dirXRebote = -0.5;

        assertEquals(dirXRebote, bola.verDireccion()[0], 0.1);
        assertEquals(-dirY, bola.verDireccion()[1], 0.1);

        // Prueba colisionar en la parte derecha de la paleta

        Bola bola2 = new Bola(POS_X + distBolaPaletaX, POS_Y, RADIO, 1);
        Colision colision2 = new Colision(bola2);

        bola2.modificarDireccion(dirX, dirY);

        while (bola2.verDireccion()[1] < 0) {
            bola2.actualizarMovimiento();
            colision2.colisionBolaPaleta(null, paleta);
        }
        dirXRebote = 0.5;

        assertEquals(dirXRebote, bola2.verDireccion()[0], 0.1);
        assertEquals(-dirY, bola2.verDireccion()[1], 0.1);
    }
}