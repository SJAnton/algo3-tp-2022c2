package algo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class ColisionBolaBloqueTest {
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
        BloqueComun bloque = new BloqueComun(POS_X, POS_Y + DIST_BLOQUE_BOLA, VIDA_BLOQUE, PUNTUACION, null);

        double dirX = 0.0;
        double dirY = 1.0;

        bola.modificarDireccion(dirX, dirY);

        assertEquals(bola.verDireccion()[1], dirY, 0.1);

        while (bola.verDireccion()[1] > 0) {
            // Mueve la bola verticalmente hasta colisionar con el bloque
            bola.actualizarMovimiento();
            colision.contactoBolaBloque(bloque.posicion(), bloque.altoAncho());            
        }
        assertEquals(bola.verDireccion()[1], -dirY, 0.1); // cambia la dirección por la colisión

        int posInfBloque = bloque.posicion()[1] - (bloque.altoAncho()[1] / 2);

        assertEquals(bola.posSup(), posInfBloque, 0.1);
    }

    @Test
    public void pruebaColisionVisiblesFabrica() throws IOException {
        // Prueba destruir a un bloque de la fábrica y verifica que se elimine
        // Pre: el nivel cargado debe contener solamente objetos Bloque (visibles)
        int nivel = 1;

        FabricaDeBloques fabrica = new FabricaDeBloques(nivel);
        fabrica.generarNivel();

        ArrayList<Object> listaBloques = fabrica.listaBloques();

        double dirX = 0.0;
        double dirY = 1.0;

        BloqueComun bloque = null;
        int menorDist = ALTO_PANTALLA;

        for (int i = 0; i < listaBloques.size(); i++) {
            // Busca el bloque a destruir más cercano verticalmente
            BloqueComun bloqueActual = (BloqueComun)listaBloques.get(i);

            int[] posBloque = bloqueActual.posicion();
            int anchoBloque = bloqueActual.altoAncho()[1];

            int bordeIzqBloque = posBloque[0] - (anchoBloque / 2);
            int bordeDerBloque = posBloque[0] + (anchoBloque / 2);

            if (POS_X <= bordeDerBloque && POS_X >= bordeIzqBloque && posBloque[1] < menorDist) {
                // Se encontró un bloque más cercano
                menorDist = posBloque[1];
                bloque = bloqueActual;
            }

        }
        assertNotEquals(bloque, null); // Verifica que se encontró el bloque

        Bola bola = new Bola(POS_X, POS_Y, RADIO);
        Colision colision = new Colision(bola);

        bola.modificarDireccion(dirX, dirY);
        assertEquals(bola.verDireccion()[1], dirY, 0.1);

        int posInfBloque = bloque.posicion()[1] - (bloque.altoAncho()[0] / 2);
        int tamInicialLista = listaBloques.size();

        while (bloque.estado()) {
            // Prueba golpear el bloque hasta que se destruya
            bola.actualizarMovimiento();
            colision.colisionBolaBloque(null, fabrica);

            if (bola.posSup() == posInfBloque && bloque.estado()) {
                // Modifica la dirección para volver a golpear al bloque
                bola.actualizarMovimiento();
                bola.modificarDireccion(dirX, -dirY);
            }
        }
        assertEquals(tamInicialLista - 1, listaBloques.size());
    }

    @Test
    public void pruebaColisionBloqueInvisible() throws IOException {
        // Prueba que el bloque invisible es reemplazado al ser golpeado
        // Pre: el nivel cargado debe contener bloques invisibles
        int nivel = 2;

        FabricaDeBloques fabrica = new FabricaDeBloques(nivel);
        fabrica.generarNivel();

        ArrayList<Object> listaBloques = fabrica.listaBloques();

        double dirX = 0.0;
        double dirY = 1.0;

        BloqueInvisible bloqueInv = null;
        int menorDist = ALTO_PANTALLA;
        int indiceMenorDist = 0;

        for (int i = 0; i < listaBloques.size(); i++) {
            // Busca el bloque invisible a destruir más cercano verticalmente
            //Object objBloqueActual = listaBloques.get(i);

            if (listaBloques.get(i).getClass() == BloqueComun.class) {
                continue;
            }
            BloqueInvisible bloqueInvActual = (BloqueInvisible)listaBloques.get(i);

            int[] posBloque = bloqueInvActual.posicion();
            int anchoBloque = bloqueInvActual.altoAncho()[1];

            int bordeIzqBloque = posBloque[0] - (anchoBloque / 2);
            int bordeDerBloque = posBloque[0] + (anchoBloque / 2);

            if (POS_X <= bordeDerBloque && POS_X >= bordeIzqBloque && posBloque[1] < menorDist) {
                indiceMenorDist = i;
                menorDist = posBloque[1];
                bloqueInv = (BloqueInvisible)bloqueInvActual;
            }
        }
        assertNotEquals(bloqueInv, null);

        Bola bola = new Bola(POS_X, POS_Y, RADIO);
        Colision colision = new Colision(bola);

        bola.modificarDireccion(dirX, dirY);

        while (bloqueInv.esInvisible()) {
            bola.actualizarMovimiento();
            colision.colisionBolaBloque(null, fabrica);
        }
        // Verifica que el objeto BloqueInvisible se convirtió en Bloque
        assertEquals(BloqueComun.class, listaBloques.get(indiceMenorDist).getClass());        
    }
}