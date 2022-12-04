package algo3;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class LectorFabricaTest {
    public final int NIVEL = 1;

    @Test
    public void pruebaLeerLinea() throws IOException {
        // La función es modificable según la línea e información a leer
        int numLinea = 0;

        String posX = "20";
        String posY = "60";
        String vida = "1";
        String puntuacion = "10";
        String color = "255:255:0";
        String visibilidad = "visible";
        
        LectorCSV lector = new LectorCSV(NIVEL);
        String[] primerLinea = lector.leer().get(numLinea);

        assertEquals(posX, primerLinea[0]);
        assertEquals(posY, primerLinea[1]);
        assertEquals(vida, primerLinea[2]);
        assertEquals(puntuacion, primerLinea[3]);
        assertEquals(color, primerLinea[4]);
        assertEquals(visibilidad, primerLinea[5]);
    }

    @Test
    public void pruebaBloquesArchivo() throws IOException {
        // Prueba que la fábrica genera correctamente los bloques del archivo
        // Pre: el nivel cargado debe contener solamente objetos Bloque (visibles)
        LectorCSV lector = new LectorCSV(NIVEL);
        ArrayList<Object> listaBloques = new FabricaDeBloques(NIVEL).generarNivel();
        ArrayList<String[]> lineas = lector.leer();

        assertEquals(lineas.size(), listaBloques.size());

        for (int i = 0; i < listaBloques.size(); i++) {
            BloqueComun bloque = (BloqueComun)listaBloques.get(i);

            String[] linea = lineas.get(i);

            int posX = Integer.parseInt(linea[0]);
            int posY = Integer.parseInt(linea[1]);
            int puntuacion = Integer.parseInt(linea[3]);

            assertEquals(posX, bloque.posX());
            assertEquals(posY, bloque.posY());
            assertEquals(true, bloque.estado());
            assertEquals(puntuacion, bloque.puntuacion());
        }
    }
}