package algo3;

import java.util.ArrayList;

public class Colision {
    private final double POS_TECHO = 0.0;

    private final int CANT_BLOQ_DEST_1 = 4;
    private final int CANT_BLOQ_DEST_2 = 12;
    private final double MOD_VEL_1 = 1.3;
    private final double MOD_VEL_2 = 1.2; 

    private int cantBloqDestruidos;

    Bola bola;

    public Colision(Bola bola) {
        this.bola = bola;
        this.cantBloqDestruidos = 0;
    }

    public void colisionBolaPaleta(Paleta paleta) {
        // TODO: implementar golpe en la esquina

        int posIzqPal = paleta.posX() - (paleta.ancho() / 2);
        int posDerPal = paleta.posX() + (paleta.ancho() / 2);
        int posSupPal = paleta.posY() - (paleta.alto() / 2);
        int posInfPal = paleta.posY() + (paleta.alto() / 2);

        if (this.bola.posInf() >= posSupPal && this.bola.posInf() <= paleta.posY() &&
            this.bola.posX() >= posIzqPal && this.bola.posX() <= posDerPal) {
            // Choque en la parte superior
            // TODO: implementar mejor rebote
            int parteIzqMitad = paleta.posX() - (paleta.ancho() / 5);
            int parteDerMitad = paleta.posX() + (paleta.ancho() / 5);
            double dirY = this.bola.verDireccion()[1];

            if (this.bola.posX() >= parteIzqMitad && this.bola.posX() <= parteDerMitad) {
                // Impacta cerca de la mitad de la paleta
                this.bola.modificarDireccion(0.0, -dirY); 
            } else if (this.bola.posX() < parteIzqMitad) {
                // Impacta en la parte izq
                this.bola.modificarDireccion(-1.0, -dirY); 
            } else if (this.bola.posX() > parteDerMitad) {
                // Impacta en la parte der
                this.bola.modificarDireccion(1.0, -dirY); 
            }
        } else if ((this.bola.posIzq() <= posDerPal || this.bola.posDer() >= posIzqPal)
            && this.bola.posY() >= posInfPal && this.bola.posY() <= posSupPal) {
            // Choque lateral
            this.bola.modificarDireccion(-this.bola.verDireccion()[0], this.bola.verDireccion()[1]);
        }
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
                switch (this.cantBloqDestruidos) {
                    // Casos en los que debe aumentar la velocidad de la bola
                    case CANT_BLOQ_DEST_1:
                        this.bola.modificarVelocidad(this.bola.velocidad() * MOD_VEL_1);
                        break;
                    case CANT_BLOQ_DEST_2:
                        this.bola.modificarVelocidad(this.bola.velocidad() * MOD_VEL_2);
                        break;
                    default:
                        break;
                }
                breakout.subirPuntuacion(bloque.puntuacion());

            } else if (objBloque.getClass() == BloqueInvisible.class) {
                // El objeto golpeado es un bloque invisible
                BloqueInvisible bloqueInv = (BloqueInvisible)objBloque;

                if (!contactoBolaBloque(bloqueInv)){
                    continue;
                }
                bloqueInv.golpear();
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
        } else if (this.bola.posSup() <= POS_TECHO) {
            this.bola.modificarDireccion(this.bola.verDireccion()[0], -this.bola.verDireccion()[1]);
        }
    }
}