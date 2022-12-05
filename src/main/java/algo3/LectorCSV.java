package algo3;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/************************************************************************************
* El lector CSV lee los archivos de la carpeta "niveles" separados en               *
* posición en X - posición en Y - vida - puntuación - color (RGB) - visibilidad     *
*************************************************************************************/

public class LectorCSV {
    private ArrayList<String[]> bloques;
    private BufferedReader lector;

    public LectorCSV(int nivel) throws FileNotFoundException {
        String ruta = "niveles/" + Integer.toString(nivel) + ".csv";
        InputStream nombreNivel = getClass().getClassLoader().getResourceAsStream(ruta);

        this.bloques = new ArrayList<String[]>();
        this.lector = new BufferedReader(new InputStreamReader(nombreNivel));
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
}