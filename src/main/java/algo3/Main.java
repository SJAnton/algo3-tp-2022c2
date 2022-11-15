package algo3;

import java.io.IOException;

public class Main {
    Breakout juego;
    
    private final int ALTO_PANTALLA = 640;
    private final int ANCHO_PANTALLA = 480;
    
    public Main() throws IOException {
        this.juego = new Breakout(ALTO_PANTALLA, ANCHO_PANTALLA);
    }
}