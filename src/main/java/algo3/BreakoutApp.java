package algo3;

import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;

public class BreakoutApp extends Application {        
    private Breakout breakout;
    private Bola bola;
    private Paleta paleta;
    private ArrayList<Object> listaBloques;

    public BreakoutApp(Breakout breakout, Bola bola, Paleta paleta, ArrayList<Object> listaBloques) {
        this.breakout = breakout;
        this.bola = bola;
        this.paleta = paleta;
        this.listaBloques = listaBloques;
    }

    @Override
    public void start(Stage escenario) throws Exception {
        Group raiz = new Group();
        Scene escena = new Scene(raiz, breakout.alto(), breakout.ancho(), Color.AQUA);

        escenario.setTitle("Breakout");
        escenario.setScene(escena);
        escenario.show();
    }

    public void actualizar(Stage escenario) {
        //TODO: implementar
    }

    private Circle dibujarBola() {
        //TODO: debe agregarse a la raíz
        return new Circle(this.bola.posX(), this.bola.posY(), this.bola.radio());
    }

    private Rectangle dibujarPaleta() {
        int[] altoAncho = this.paleta.altoAncho();
        return new Rectangle(this.paleta.posX(), this.paleta.posY(), altoAncho[1], altoAncho[0]);
    }

    private ArrayList<Rectangle> dibujarBloque() {
        ArrayList<Rectangle> dibujosBloques = new ArrayList<Rectangle>();
        for (Object objBloque : this.listaBloques) {
            if (objBloque.getClass() == BloqueInvisible.class) {
                continue;
            }
            Bloque bloque = (Bloque)objBloque;
            int[] posBloque = bloque.posicion();
            int[] altoAnchoBloque = bloque.altoAncho();

            dibujosBloques.add(new Rectangle(posBloque[0], posBloque[1], altoAnchoBloque[1], altoAnchoBloque[0]));
        }
        return dibujosBloques;
    }
}