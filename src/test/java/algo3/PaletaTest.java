package algo3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaletaTest {
    private final int POS_X = 200;
    private final int POS_Y = 40;

    @Test
    public void pruebaMov() {
        Paleta paleta = new Paleta(POS_X, POS_Y);

        int ancho = paleta.altoAncho()[1];

        while ((paleta.posX() - (ancho / 2)) != 0) {
            // El borde izq no toca la pared izq
            paleta.movIzq();
        }
        assertEquals((paleta.posX() - (ancho / 2)), 0);
        assertEquals(paleta.posX(), ancho / 2);

        int paredDer = ancho * 5;

        while ((paleta.posX() + (ancho / 2)) != paredDer) {
            // El borde der no toca la pared der
            paleta.movDer(paredDer);
        }
        assertEquals((paleta.posX() + (ancho / 2)), paredDer);
        assertEquals(paleta.posX(), paredDer - (ancho / 2));
    }
}