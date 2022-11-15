package algo3;

import java.io.IOException;
import java.util.ArrayList;

public class FabricaDeBloques {
    private ArrayList<Bloque> bloques;
    private ArrayList<String[]> datosBloques;

    public FabricaDeBloques(int nivel) throws IOException {
        this.bloques = new ArrayList<Bloque>();
        this.datosBloques = new LectorCSV(nivel).leer();
    }

    public ArrayList<Bloque> generarNivel() throws IOException {
        for (int i = 0; i < this.datosBloques.size(); i++) {
            int posX = Integer.parseInt(this.datosBloques.get(i)[0]);
            int posY = Integer.parseInt(this.datosBloques.get(i)[1]);
            int vida = Integer.parseInt(this.datosBloques.get(i)[2]);
            int puntuacion = Integer.parseInt(this.datosBloques.get(i)[3]);
            String[] color = this.datosBloques.get(i)[4].split(":");

            this.bloques.add(new Bloque(posX, posY, vida, puntuacion, convertirColorInt(color)));
        }
        return this.bloques;
    }

    private int[] convertirColorInt(String[] color) {
        int valor1 = Integer.parseInt(color[0]);
        int valor2 = Integer.parseInt(color[1]);
        int valor3 = Integer.parseInt(color[2]);

        return new int[] {valor1, valor2, valor3};
    }

    public int cantidadBloques() {
        return this.bloques.size();
    }

    public ArrayList<Bloque> listaBloques() {
        if (cantidadBloques() == 0) {
            return null;
        }
        return this.bloques;
    }
}