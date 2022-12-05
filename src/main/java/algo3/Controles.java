package algo3;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controles {
    private final Scene escena;
    private final Breakout breakout;

    private final HashMap<KeyCode, Boolean> teclasActivas;

    public Controles(Scene escena, Breakout breakout) {
        this.escena = escena;
        this.breakout = breakout;
        this.teclasActivas = crearDictTeclas();
    }

    public void controles() {
        movimientoPaleta();
        controlesTeclas();
        detectarTeclaLibre();
    }
    
    private HashMap<KeyCode, Boolean> crearDictTeclas() {
        HashMap<KeyCode, Boolean> dict = new HashMap<KeyCode, Boolean>();

        dict.put(KeyCode.LEFT, false);
        dict.put(KeyCode.RIGHT, false);
        return dict;
    }
    
    private void movimientoPaleta() {
        if (teclasActivas.get(KeyCode.LEFT) == true) {
            this.breakout.paleta().movIzq();
        } if (teclasActivas.get(KeyCode.RIGHT) == true) {
            this.breakout.paleta().movDer(this.breakout.ancho());
        }
    }

    private void controlesTeclas() {
        this.escena.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent tecla) {
                switch (tecla.getCode()) {
                    case LEFT:
                        teclasActivas.put(KeyCode.LEFT, true);
                        break;
                    case RIGHT:
                        teclasActivas.put(KeyCode.RIGHT, true);
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

    private void detectarTeclaLibre() {
        this.escena.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent tecla) {
                switch (tecla.getCode()) {
                    case LEFT:
                        teclasActivas.put(KeyCode.LEFT, false);
                        break;
                    case RIGHT:
                        teclasActivas.put(KeyCode.RIGHT, false);
                        break;
                    default:
                        break;
                } 
            } 
        });
    }
}