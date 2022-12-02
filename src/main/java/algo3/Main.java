package algo3;

import java.io.IOException;

public class Main {
    private static Breakout juego;
    
    private final static int ALTO_PANTALLA = 640;
    private final static int ANCHO_PANTALLA = 480;
    
    public static void main(String[] args) throws IOException {
        juego = new Breakout(ALTO_PANTALLA, ANCHO_PANTALLA);
    }
}