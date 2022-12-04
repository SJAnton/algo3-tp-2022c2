package algo3;

import java.io.IOException;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class BreakoutApp extends Application {            
    private final static int ALTO_PANTALLA = 640;
    private final static int ANCHO_PANTALLA = 480;
    
    private final int POS_X_VIDA = 60;
    private final int POS_X_PUNT = 380;
    private final int POS_Y_TABLERO = 20;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage escenario) throws IOException {
        Group raiz = new Group();
        Canvas canvas = new Canvas(ANCHO_PANTALLA, ALTO_PANTALLA);
        Scene escena = new Scene(raiz, ANCHO_PANTALLA, ALTO_PANTALLA);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        raiz.getChildren().add(canvas);

        escenario.setTitle("Breakout");
        escenario.setResizable(false);
        escenario.setScene(escena);
        escenario.show();

        actualizar(gc, escena);
    }

    public void actualizar(GraphicsContext gc, Scene escena) throws IOException {
        new AnimationTimer() {
            Breakout breakout = new Breakout(ALTO_PANTALLA, ANCHO_PANTALLA);
            @Override
            public void handle(long now) {
                controles(escena, breakout);
                try {
                    breakout.actualizar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dibujarFondo(gc);
                dibujarPuntuacion(gc, breakout);
                
                dibujarPaleta(gc, breakout);  
                dibujarBola(gc, breakout.bola());
                dibujarBloques(gc, breakout.fabrica());
            }
        }.start();
    }

    private void dibujarFondo(GraphicsContext gc) {
        gc.setFill(Color.LIGHTCYAN);
        gc.fillRect(0.0, 0.0, ANCHO_PANTALLA, ALTO_PANTALLA); 
    }

    private void dibujarBola(GraphicsContext gc, Bola bola) {
        double diametro = bola.radio() * 2;

        gc.setFill(Color.GRAY);
        gc.fillOval(bola.posIzq(), bola.posSup(), diametro, diametro);
    }

    private void dibujarPaleta(GraphicsContext gc, Breakout breakout) {
        Paleta paleta = breakout.paleta();
        int extremoIzqY = paleta.posY() - (paleta.alto() / 2);
        
        gc.setFill(Color.AQUA);
        gc.fillRect(paleta.bordeIzq(), extremoIzqY, paleta.ancho(), paleta.alto());
    }

    private void dibujarBloques(GraphicsContext gc, FabricaDeBloques fabrica) {
        for (Object objBloque : fabrica.listaBloques()) {
            if (objBloque.getClass() == BloqueInvisible.class) {
                continue;
            }
            BloqueComun bloque = (BloqueComun)objBloque;

            int posX = bloque.posX() - bloque.ancho() / 2;
            int posY = bloque.posY() - bloque.alto() / 2;

            int r = bloque.color()[0];
            int g = bloque.color()[1];
            int b = bloque.color()[2];

            gc.setFill(Color.rgb(r, g, b));
            gc.fillRect(posX, posY, bloque.ancho(), bloque.alto());
        }
    }

    private void dibujarPuntuacion(GraphicsContext gc, Breakout breakout) {
        String vida = Integer.toString(breakout.vida());
        String puntuacion = Integer.toString(breakout.puntuacion());

        gc.setFill(Color.BLACK);
        gc.fillText("Vidas: %s".formatted(vida), POS_X_VIDA, POS_Y_TABLERO);
        gc.fillText("Puntos: %s".formatted(puntuacion), POS_X_PUNT, POS_Y_TABLERO);
    }

    private void controles(Scene escena, Breakout breakout) {
        //TODO: implementar pausa
        escena.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent tecla) {
                switch (tecla.getCode()) {
                    case LEFT:
                        breakout.paleta().movIzq();
                        break;
                    case RIGHT:
                        breakout.paleta().movDer(ANCHO_PANTALLA);
                        break;
                    case R:
                        breakout.reinciar();
                        break;
                    case SPACE:
                        breakout.iniciarMovimientoBola();
                        break;
                    default:
                        break;
                } 
            } 
        });
    }
}