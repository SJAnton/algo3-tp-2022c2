package algo3;

import java.util.ArrayList;

public class Colision {
    private final double POS_TECHO = 0.0;

    private final double REBOTE_ESQUINAS_PAL = 1;
    private final double REBOTE_COSTADOS_PAL = 0.5;
    private final double REBOTE_MITAD_PAL = 0.0;

    // Cantidad de bloques que deben ser destruidos para modificar la velocidad
    private final int CANT_BLOQ_DEST_1 = 4;
    private final int CANT_BLOQ_DEST_2 = 12;
    private final int CANT_BLOQ_DEST_3 = 24;
    private final int CANT_BLOQ_DEST_4 = 48;

    // Variación de la velocidad (el total no debe ser mucho mayor a 7 para
    // no generar fallos en la detección de colisiones por la velocidad)
    private final double MOD_VEL_1 = 1.4;
    private final double MOD_VEL_2 = 1.2;
    private final double MOD_VEL_3 = 1.2;
    private final double MOD_VEL_4 = 1.2;

    private int cantBloqDestruidos;

    private Bola bola;

    public Colision(Bola bola) {
        this.bola = bola;
        this.cantBloqDestruidos = 0;
    }

    public void colisionBolaPaleta(Breakout breakout, Paleta paleta) {
        int posIzqPal = paleta.posX() - (paleta.ancho() / 2);
        int posDerPal = paleta.posX() + (paleta.ancho() / 2);
        int posSupPal = paleta.posY() - (paleta.alto() / 2);
        int posInfPal = paleta.posY() + (paleta.alto() / 2);

        double dirX = this.bola.verDireccion()[0];
        double dirY = this.bola.verDireccion()[1];

        if (this.bola.posY() <= posInfPal && this.bola.posY() >= posSupPal && dirX == 0.0) {
            // Choque en los costados de la paleta
            if (condColisionIzq(posDerPal, paleta.posX())) {
                // Caso en el que la bola se mueve verticalmente e impacta a la derecha
                this.bola.modificarDireccion(REBOTE_ESQUINAS_PAL, dirY); 
            } else if (condColisionDer(posIzqPal, paleta.posX())) {
                // Caso en el que la bola se mueve verticalmente e impacta a la izquierda
                this.bola.modificarDireccion(-REBOTE_ESQUINAS_PAL, dirY);
            } 
            
        else if ((condColisionIzq(posDerPal, paleta.posX()) ||
            condColisionDer(posIzqPal, paleta.posX())) &&
            this.bola.posY() <= posInfPal &&this.bola.posY() >= posSupPal) {
                // Caso en el que la bola no se mueve verticalmente
                this.bola.modificarDireccion(-dirX, dirY);
            }
        } else if (condColisionSup(posSupPal, paleta.posY()) &&
            this.bola.posX() >= posIzqPal && this.bola.posX() <= posDerPal) {
            // Choque en la parte superior, la paleta es dividida en cinco partes
        
            choquesParteSupPal(posIzqPal, posDerPal, dirY);
            BreakoutApp.reproducirSonido(breakout, "golpePaleta");   
        }
    }
    
    private void choquesParteSupPal(int posIzqPal, int posDerPal, double dirY) {
        int primerQuintoPal = quintaPartePaleta(1, posIzqPal, posDerPal);
        int segundoQuintoPal = quintaPartePaleta(2, posIzqPal, posDerPal);
        int tercerQuintoPal = quintaPartePaleta(3, posIzqPal, posDerPal);
        int cuartoQuintoPal = quintaPartePaleta(4, posIzqPal, posDerPal);
        int quintoQuintoPal = quintaPartePaleta(5, posIzqPal, posDerPal);

        if (this.bola.posX() <= primerQuintoPal) {
            // Impacta cerca de la parte izquierda de la paleta
            this.bola.modificarDireccion(-REBOTE_ESQUINAS_PAL, -dirY);
        } else if (this.bola.posX() <= segundoQuintoPal && this.bola.posX() > primerQuintoPal) {
            // Impacta entre la mitad y la parte izquierda
            this.bola.modificarDireccion(-REBOTE_COSTADOS_PAL, -dirY);
        } else if (this.bola.posX() <= tercerQuintoPal && this.bola.posX() > segundoQuintoPal) {
            // Impacta cerca de la mitad de la paleta
            this.bola.modificarDireccion(REBOTE_MITAD_PAL, -dirY);
        } else if (this.bola.posX() <= cuartoQuintoPal && this.bola.posX() > tercerQuintoPal) {
            // Impacta entre la mitad y la parte derecha
            this.bola.modificarDireccion(REBOTE_COSTADOS_PAL, -dirY);
        } else if (this.bola.posX() <= quintoQuintoPal && this.bola.posX() > cuartoQuintoPal) {
            // Impacta cerca de la parte derecha
            this.bola.modificarDireccion(REBOTE_ESQUINAS_PAL, -dirY);
        }
    }

    public boolean contactoBolaBloque(Bloque bloque) {
        int posIzqBloque = bloque.posX() - (bloque.ancho() / 2);
        int posDerBloque = bloque.posX() + (bloque.ancho() / 2);
        int posSupBloque = bloque.posY() - (bloque.alto() / 2);
        int posInfBloque = bloque.posY() + (bloque.alto() / 2);

        // Todas las posibles combinaciones de colisión bola-bloque
        if ((condColisionIzq(posDerBloque, bloque.posX()) ||
            condColisionDer(posIzqBloque, bloque.posX())) &&
            this.bola.posY() <= posInfBloque && this.bola.posY() >= posSupBloque) {
            // Choque lateral
            if (this.bola.verDireccion()[0] == 0.0) {
                // Golpe en una esquina del bloque
                this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
            } else {
                this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
            }
            return true;
        } else if ((condColisionSup(posSupBloque, bloque.posY()) ||
            condColisionInf(posInfBloque, bloque.posY())) &&
            this.bola.posX() >= posIzqBloque && this.bola.posX() <= posDerBloque) {
            // Choque en la parte superior / inferior
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
            return true;  
        } 
        return false;
    }

    private void cambiosVelocidad() {
        switch (this.cantBloqDestruidos) {
            // Casos en los que debe aumentar la velocidad de la bola
            case CANT_BLOQ_DEST_1:
                this.bola.modificarVelocidad(this.bola.velocidad() * MOD_VEL_1);
                break;
            case CANT_BLOQ_DEST_2:
                this.bola.modificarVelocidad(this.bola.velocidad() * MOD_VEL_2);
                break;
            case CANT_BLOQ_DEST_3:
                this.bola.modificarVelocidad(this.bola.velocidad() * MOD_VEL_3);
                break;
            case CANT_BLOQ_DEST_4:
                this.bola.modificarVelocidad(this.bola.velocidad() * MOD_VEL_4);
            default:
                break;
        }
    }

    public void colisionBolaBloque(Breakout breakout, FabricaDeBloques fabrica) {
        ArrayList<Bloque> listaBloques = fabrica.listaBloques();

        if (listaBloques == null) {
            // No se creó el nivel o no hay bloques
            return;
        }
        for (int i = 0; i < listaBloques.size(); i++) {
            Bloque objBloque = listaBloques.get(i);

            if (!objBloque.esInvisible()) {
                // El objeto golpeado es un bloque común
                BloqueComun bloque = (BloqueComun)objBloque;

                if (!contactoBolaBloque(bloque)) {
                    continue;
                }    
                bloque.golpear();
                BreakoutApp.reproducirSonido(breakout, "golpeBloque");

                if (bloque.estado()) {
                    // El bloque no fue destruido
                    return;
                }
                listaBloques.remove(i);

                if (breakout == null) {
                    // Breakout es null en las pruebas de colisiones
                    return;
                }
                this.cantBloqDestruidos++;
                this.cambiosVelocidad();
                breakout.subirPuntuacion(bloque.puntuacion());

            } else if (objBloque.esInvisible()) {
                // El objeto golpeado es un bloque invisible
                BloqueInvisible bloqueInv = (BloqueInvisible)objBloque;

                if (!contactoBolaBloque(bloqueInv)){
                    continue;
                }
                bloqueInv.golpear();
                BreakoutApp.reproducirSonido(breakout, "golpeBloque");
                listaBloques.set(i, bloqueInv.reemplazarBloque());
            }
        }
    }

    public void colisionBolaPared(Breakout breakout, int piso, int paredDer) {
        if (this.bola.posInf() >= piso && breakout != null) {
            breakout.reducirVida();
            breakout.reiniciarPosBola();
        } else if (this.bola.posIzq() <= 0.0 || this.bola.posDer() >= paredDer) {
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
            BreakoutApp.reproducirSonido(breakout, "golpePared");
        } else if (this.bola.posSup() <= POS_TECHO) {
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
            BreakoutApp.reproducirSonido(breakout, "golpePared");
        }
    }

    public void reiniciarCantBloqDest() {
        this.cantBloqDestruidos = 0;
    }

    private int quintaPartePaleta(int n, int posIzqPal, int posDerPal) {
        int largoPaleta = posDerPal - posIzqPal;
        return posIzqPal + ((largoPaleta) / 5) * n;
    }

    private boolean condColisionSup(int posSupObj, int posYObj) {
        return this.bola.posInf() >= posSupObj && this.bola.posInf() <= posYObj;
    }

    private boolean condColisionInf(int posInfObj, int posYObj) {
        return this.bola.posSup() <= posInfObj && this.bola.posSup() >= posYObj;
    }

    private boolean condColisionIzq(int posDerObj, int posXObj) {
        return this.bola.posIzq() <= posDerObj && this.bola.posIzq() >= posXObj;
    }

    private boolean condColisionDer(int posIzqObj, int posXObj) {
        return this.bola.posDer() >= posIzqObj && this.bola.posDer() <= posXObj;
    }
}