package algo3;

import java.io.IOException;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class BreakoutApp extends Application {            
    private final int ALTO_PANTALLA = 640;
    private final int ANCHO_PANTALLA = 480;
    
    private final int POS_X_VIDA = 60;
    private final int POS_X_PUNT = 380;
    private final int POS_Y_TABLERO = 20;

    private static final boolean SONIDO_ACTIVO = false;
    private static final String RUTA_AUDIO_LANZ = "/audio/lanzamiento.mp3";
    private static final String RUTA_AUDIO_GOLPE_PAL = "/audio/golpepaleta.mp3";
    private static final String RUTA_AUDIO_GOLPE_BLOQ = "/audio/golpebloque.mp3";
    private static final String RUTA_AUDIO_GOLPE_PAR = "/audio/golpepared.mp3";

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
            Controles controles = new Controles(escena, breakout);
            @Override
            public void handle(long now) {
                controles.controles();
                try {
                    breakout.actualizar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dibujarFondo(gc);
                dibujarPuntuacion(gc, breakout);
                
                dibujarPaleta(gc, breakout);  
                dibujarBola(gc, breakout.bola());
                if (breakout.fabrica().cantidadBloques() != 0) {
                    dibujarBloques(gc, breakout.fabrica());
                }
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

    public static void reproducirSonido(Breakout breakout, String sonido) {
        if (!SONIDO_ACTIVO || breakout == null) {
            // Breakout == null en las pruebas
            return;
        }
        Media archivo;

        switch (sonido) {
            case "lanzamiento":
                archivo = new Media(Main.class.getResource(RUTA_AUDIO_LANZ).toExternalForm());
                break;
            case "golpePaleta":
                archivo = new Media(Main.class.getResource(RUTA_AUDIO_GOLPE_PAL).toExternalForm());
                break;
            case "golpeBloque":
                archivo = new Media(Main.class.getResource(RUTA_AUDIO_GOLPE_BLOQ).toExternalForm());
                break;
            case "golpePared":
                archivo = new Media(Main.class.getResource(RUTA_AUDIO_GOLPE_PAR).toExternalForm());
                break;
            default:
                return;
        }
        MediaPlayer mediaPlayer = new MediaPlayer(archivo);
        mediaPlayer.play();
    }
}