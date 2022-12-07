package algo3;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.paint.Color;

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
            
            String[] datosColor = this.datosBloques.get(i)[4].split(":");
            
            int r = Integer.parseInt(datosColor[0]);
            int g = Integer.parseInt(datosColor[1]);
            int b = Integer.parseInt(datosColor[2]);

            Color color = Color.rgb(r, g, b);

            switch (this.datosBloques.get(i)[5]) {
                case "visible":
                    BloqueComun bloque = new BloqueComun(posX, posY, vida, puntuacion, color);
                    this.bloques.add(bloque);
                    break;
                case "invisible":
                    BloqueInvisible bloqueInvisible = new BloqueInvisible(posX, posY, vida, puntuacion, color);
                    this.bloques.add(bloqueInvisible);
                    break;
            }
        }
        return this.bloques;
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