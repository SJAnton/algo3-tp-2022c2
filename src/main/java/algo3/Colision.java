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
    // Variación de la velocidad
    private final double MOD_VEL_1 = 1.3;
    private final double MOD_VEL_2 = 1.2;
    private final double MOD_VEL_3 = 1.3;

    private int cantBloqDestruidos;

    private Bola bola;

    public Colision(Bola bola) {
        this.bola = bola;
        this.cantBloqDestruidos = 0;
    }

    public void colisionBolaPaleta(Breakout breakout, Paleta paleta) {
        // TODO: implementar golpe en la esquina

        int posIzqPal = paleta.posX() - (paleta.ancho() / 2);
        int posDerPal = paleta.posX() + (paleta.ancho() / 2);
        int posSupPal = paleta.posY() - (paleta.alto() / 2);
        int posInfPal = paleta.posY() + (paleta.alto() / 2);

        if (this.bola.posInf() >= posSupPal && this.bola.posInf() <= paleta.posY() &&
            this.bola.posX() >= posIzqPal && this.bola.posX() <= posDerPal) {
            // Choque en la parte superior, la paleta es divida en cinco partes
            int primerQuintoPal = quintaPartePaleta(1, posIzqPal, posDerPal);
            int segundoQuintoPal = quintaPartePaleta(2, posIzqPal, posDerPal);
            int tercerQuintoPal = quintaPartePaleta(3, posIzqPal, posDerPal);
            int cuartoQuintoPal = quintaPartePaleta(4, posIzqPal, posDerPal);
            int quintoQuintoPal = quintaPartePaleta(5, posIzqPal, posDerPal);
            
            double dirY = this.bola.verDireccion()[1];

            if (this.bola.posX() < primerQuintoPal) {
                this.bola.modificarDireccion(-REBOTE_ESQUINAS_PAL, -dirY);
            } else if (this.bola.posX() < segundoQuintoPal && this.bola.posX() > primerQuintoPal) {
                this.bola.modificarDireccion(-REBOTE_COSTADOS_PAL, -dirY);
            } else if (this.bola.posX() < tercerQuintoPal && this.bola.posX() > segundoQuintoPal) {
                // Impacta cerca de la mitad de la paleta
                this.bola.modificarDireccion(REBOTE_MITAD_PAL, -dirY);
            } else if (this.bola.posX() < cuartoQuintoPal && this.bola.posX() > tercerQuintoPal) {
                this.bola.modificarDireccion(REBOTE_COSTADOS_PAL, -dirY);
            } else if (this.bola.posX() < quintoQuintoPal && this.bola.posX() > cuartoQuintoPal) {
                this.bola.modificarDireccion(REBOTE_ESQUINAS_PAL, -dirY);
            }
            BreakoutApp.reproducirSonido(breakout, "golpePaleta");

        } else if ((this.bola.posIzq() <= posDerPal || this.bola.posDer() >= posIzqPal)
            && this.bola.posY() >= posInfPal && this.bola.posY() <= posSupPal) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
            BreakoutApp.reproducirSonido(breakout, "golpePaleta");
        }
    }

    private int quintaPartePaleta(int n, int posIzqPal, int posDerPal) {
        int largoPaleta = posDerPal - posIzqPal;
        return posIzqPal + ((largoPaleta) / 5) * n;
    }

    private boolean condColisionBloqSup(int posSupBloque, int posYBloque) {
        return this.bola.posInf() >= posSupBloque && this.bola.posInf() <= posYBloque;
    }

    private boolean condColisionBloqInf(int posInfBloque, int posYBloque) {
        return this.bola.posSup() <= posInfBloque && this.bola.posSup() >= posYBloque;
    }

    public boolean contactoBolaBloque(Bloque bloque) {
        //TODO: implementar golpe en la esquina

        int posIzqBloque = bloque.posX() - (bloque.ancho() / 2);
        int posDerBloque = bloque.posX() + (bloque.ancho() / 2);
        int posSupBloque = bloque.posY() - (bloque.alto() / 2);
        int posInfBloque = bloque.posY() + (bloque.alto() / 2);
        
        // Todas las posibles combinaciones de colisión bola-bloque        
        if ((condColisionBloqSup(posSupBloque, bloque.posY()) ||
            condColisionBloqInf(posInfBloque, bloque.posY())) &&
            this.bola.posX() > posIzqBloque && this.bola.posX() < posDerBloque) {
            // Choque en la parte superior / inferior
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
            return true;
        } else if ((this.bola.posIzq() == posDerBloque || this.bola.posDer() == posIzqBloque)
            && this.bola.posY() > posInfBloque && this.bola.posY() < posSupBloque) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
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
            default:
                break;
        }
    }

    public void colisionBolaBloque(Breakout breakout, FabricaDeBloques fabrica) {
        ArrayList<Object> listaBloques = fabrica.listaBloques();
        
        if (listaBloques == null) {
            // No se creó el nivel o no hay bloques
            return;
        }
        for (int i = 0; i < listaBloques.size(); i++) {
            Object objBloque = listaBloques.get(i);

            if (objBloque.getClass() == BloqueComun.class) {
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
                this.cantBloqDestruidos++;

                if (breakout == null) {
                    // Breakout es null en las pruebas de colisiones
                    return;
                }
                this.cambiosVelocidad();
                breakout.subirPuntuacion(bloque.puntuacion());

            } else if (objBloque.getClass() == BloqueInvisible.class) {
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
}