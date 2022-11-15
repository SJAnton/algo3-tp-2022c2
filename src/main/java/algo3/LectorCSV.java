package algo3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/* *********************************************************************
 * El lector CSV lee los archivos de la carpeta "niveles" separados en *
 * posición en X - posición en Y - vida - puntuación - color (RGB)     *
***********************************************************************/

public class LectorCSV {
    private ArrayList<String[]> bloques;
    private BufferedReader lector;

    public LectorCSV(int nivel) throws FileNotFoundException {
        this.bloques = new ArrayList<String[]>();
        this.lector = new BufferedReader(new FileReader(new File(generarRuta(nivel))));
    }

    public ArrayList<String[]> leer() throws IOException {
        String linea;
        
        while ((linea = this.lector.readLine()) != null) {
            String[] datos = linea.split(",");
            this.bloques.add(datos);
        }
        this.lector.close();
        return bloques;
    }

    public String rutaDir() {
        // Devuelve el directorio a la carpeta "niveles"
        String dirActual = System.getProperty("user.dir");
        return dirActual + "\\niveles";
    }

    public String[] listaArchivos(String ruta) {
        // Devuelve el nombre de los archivos en el directorio "niveles"
        File dirNiveles = new File(ruta);
        return dirNiveles.list();
    }

    private String generarRuta(int nivel) {
        // Devuelve el directorio al nivel elegido
        return rutaDir() + "\\" + listaArchivos(rutaDir())[nivel];
    }
}