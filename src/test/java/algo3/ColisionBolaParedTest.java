package algo3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColisionBolaParedTest {
    private final int ALTO_PANTALLA = 640;
    private final int ANCHO_PANTALLA = 480;
    private final int POS_X = 60;
    private final int POS_Y = 60;
    private final int RADIO = 5;

    @Test
    public void PruebaColisionHorizontal() {
        Bola bola = new Bola(POS_X, POS_Y, RADIO);
        Colision colision = new Colision(bola);

        double dirX = -1.0;
        double dirY = 0.0;

        bola.modificarDireccion(dirX, dirY);

        assertEquals(bola.verDireccion()[0], dirX, 0.1);

        while (bola.verDireccion()[0] <= 0) {
            // Prueba que la bola llega a la pared izq y rebota
            bola.actualizarMovimiento();
            colision.colisionBolaPared(ALTO_PANTALLA, ANCHO_PANTALLA);
        }
        assertEquals(bola.verDireccion()[0], -dirX, 0.1);
        assertEquals(bola.verDireccion()[1], dirY, 0.1);

        while (bola.verDireccion()[0] > 0) {
            // Prueba que la bola llega a la pared der y rebota
            bola.actualizarMovimiento();
            colision.colisionBolaPared(ALTO_PANTALLA, ANCHO_PANTALLA);
        }
        assertEquals(bola.verDireccion()[0], dirX, 0.1);
        assertEquals(bola.verDireccion()[1], dirY, 0.1);
    }

    @Test
    public void PruebaColisionVertical() {
        Bola bola = new Bola(POS_X, POS_Y, RADIO);
        Colision colision = new Colision(bola);

        double dirX = 0.0;
        double dirY = 1.0;

        bola.modificarDireccion(dirX, dirY);

        assertEquals(bola.verDireccion()[1], dirY, 0.1);

        while (bola.verDireccion()[1] > 0) {
            // Prueba que la bola llega a la pared sup y rebota
            bola.actualizarMovimiento();
            colision.colisionBolaPared(ALTO_PANTALLA, ANCHO_PANTALLA);
        }
        assertEquals(bola.verDireccion()[0], dirX, 0.1);
        assertEquals(bola.verDireccion()[1], -dirY, 0.1);
    }
}