package algo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class ColisionBolaBloqueTest {
    private final int NIVEL = 0;
    private final int RADIO = 5;
    private final int POS_X = 100;
    private final int POS_Y = 100;
    private final int VIDA_BLOQUE = 3;
    private final int PUNTUACION = 10;
    private final int ALTO_PANTALLA = 640;
    private final int DIST_BLOQUE_BOLA = 100;

    @Test
    public void pruebaColision() throws IOException {
        // Prueba que la bola rebota al colisionar con el bloque
        Bola bola = new Bola(POS_X, POS_Y, RADIO);
        Colision colision = new Colision(bola);
        Bloque bloque = new Bloque(POS_X, POS_Y + DIST_BLOQUE_BOLA, VIDA_BLOQUE, PUNTUACION, null);

        double dirX = 0.0;
        double dirY = 1.0;

        bola.modificarDireccion(dirX, dirY);

        assertEquals(bola.verDireccion()[1], dirY, 0.1);

        while (bola.verDireccion()[1] > 0) {
            // Mueve la bola verticalmente hasta colisionar con el bloque
            bola.actualizarMovimiento();
            colision.contactoBolaBloque(bloque);            
        }
        assertEquals(bola.verDireccion()[1], -dirY, 0.1); // cambia la dirección por la colisión

        int posInfBloque = bloque.posicion()[1] - (bloque.altoAncho()[1] / 2);

        assertEquals(bola.posSup(), posInfBloque, 0.1);
    }

    @Test
    public void pruebaColisionFabrica() throws IOException {
        // Prueba destruir a un bloque de la fábrica y verifica que se elimine
        Bola bola = new Bola(POS_X, POS_Y, RADIO);
        FabricaDeBloques fabrica = new FabricaDeBloques(NIVEL);
        fabrica.generarNivel();

        ArrayList<Bloque> listaBloques = fabrica.listaBloques();

        double dirX = 0.0;
        double dirY = 1.0;

        Bloque bloque = null;
        int menorDist = ALTO_PANTALLA;

        bola.modificarDireccion(dirX, dirY);

        assertEquals(bola.verDireccion()[1], dirY, 0.1);

        for (int i = 0; i < listaBloques.size(); i++) {
            // Busca el bloque a destruir más cercano verticalmente
            int[] posBloque = listaBloques.get(i).posicion();
            int anchoBloque = listaBloques.get(i).altoAncho()[1];

            int bordeIzqBloque = posBloque[0] - (anchoBloque / 2);
            int bordeDerBloque = posBloque[0] + (anchoBloque / 2);

            if (POS_X <= bordeDerBloque && POS_X >= bordeIzqBloque && posBloque[1] < menorDist) {
                // Se encontró un bloque más cercano
                menorDist = posBloque[1];
                bloque = listaBloques.get(i);
            }

        }
        assertNotEquals(bloque, null);

        Colision colision = new Colision(bola);

        int posInfBloque = bloque.posicion()[1] - (bloque.altoAncho()[0] / 2);
        int tamInicialLista = listaBloques.size();

        while (bloque.estado()) {
            // Prueba golpear el bloque hasta que se destruya
            bola.actualizarMovimiento();
            colision.colisionBolaBloque(fabrica);

            if (bola.posSup() == posInfBloque && bloque.estado()) {
                // Modifica la dirección para volver a golpear al bloque
                bola.actualizarMovimiento();
                bola.modificarDireccion(dirX, -dirY);
            }
        }
        assertEquals(tamInicialLista - 1, listaBloques.size());
    }
}