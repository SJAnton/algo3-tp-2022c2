package algo3;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class LectorFabricaTest {
    public final int NIVEL = 0;
    public final String NOMBRE_ARCHIVO = "1.csv";
    public final String DIRECTORIO = "C:\\Users\\usuario\\Desktop\\Algoritmos\\Algoritmos III\\breakout\\niveles";

    @Test
    public void pruebaLocalizarArchivo() throws FileNotFoundException {
        // Prueba que el lector puede localizar la ruta al archivo automáticamente
        LectorCSV lector = new LectorCSV(NIVEL);
        assertEquals(lector.rutaDir(), DIRECTORIO);
        assertEquals(lector.listaArchivos(DIRECTORIO)[0], NOMBRE_ARCHIVO);
    }

    @Test
    public void pruebaLeerLinea() throws IOException {
        // La función es modificable según la línea e información a leer
        int numLinea = 0;

        String posX = "20";
        String posY = "600";
        String vida = "1";
        String puntuacion = "10";
        String color = "255:255:0";
        
        LectorCSV lector = new LectorCSV(NIVEL);        
        String[] primerLinea = lector.leer().get(numLinea);

        assertEquals(posX, primerLinea[0]);
        assertEquals(posY, primerLinea[1]);
        assertEquals(vida, primerLinea[2]);
        assertEquals(puntuacion, primerLinea[3]);
        assertEquals(color, primerLinea[4]);
    }

    @Test
    public void pruebaBloquesArchivo() throws IOException {
        // Prueba que la fábrica genera correctamente los bloques del archivo
        LectorCSV lector = new LectorCSV(NIVEL);
        ArrayList<Bloque> listaBloques = new FabricaDeBloques(NIVEL).generarNivel();
        ArrayList<String[]> lineas = lector.leer();

        assertEquals(listaBloques.size(), lineas.size());

        for (int i = 0; i < listaBloques.size(); i++) {
            Bloque bloque = listaBloques.get(i);

            int[] posBloque = bloque.posicion();
            String[] linea = lineas.get(i);

            int posX = Integer.parseInt(linea[0]);
            int posY = Integer.parseInt(linea[1]);
            int puntuacion = Integer.parseInt(linea[3]);

            assertEquals(posX, posBloque[0]);
            assertEquals(posY, posBloque[1]);
            assertEquals(true, bloque.estado());
            assertEquals(puntuacion, bloque.puntuacion());
        }
    }
}