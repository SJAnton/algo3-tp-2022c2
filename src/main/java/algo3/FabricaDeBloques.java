package algo3;

import java.io.IOException;
import java.util.ArrayList;

public class FabricaDeBloques {
    private ArrayList<Object> bloques;
    private ArrayList<String[]> datosBloques;

    public FabricaDeBloques(int nivel) throws IOException {
        this.bloques = new ArrayList<Object>();
        this.datosBloques = new LectorCSV(nivel).leer();
    }

    public ArrayList<Object> generarNivel() throws IOException {
        for (int i = 0; i < this.datosBloques.size(); i++) {
            int posX = Integer.parseInt(this.datosBloques.get(i)[0]);
            int posY = Integer.parseInt(this.datosBloques.get(i)[1]);
            int vida = Integer.parseInt(this.datosBloques.get(i)[2]);
            int puntuacion = Integer.parseInt(this.datosBloques.get(i)[3]);
            String[] color = this.datosBloques.get(i)[4].split(":");

            switch (this.datosBloques.get(i)[5]) {
                case "visible":
                    BloqueComun bloque = new BloqueComun(posX, posY, vida, puntuacion, convertirColorInt(color));
                    this.bloques.add(bloque);
                    break;
                case "invisible":
                    BloqueInvisible bloqueInvisible = new BloqueInvisible(posX, posY, vida, puntuacion, convertirColorInt(color));
                    this.bloques.add(bloqueInvisible);
                    break;
            }
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

    public ArrayList<Object> listaBloques() {
        if (cantidadBloques() == 0) {
            return null;
        }
        return this.bloques;
    }
}